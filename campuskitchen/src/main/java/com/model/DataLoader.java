package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 * Remove getRecipeById
 * getMealplans
 * call RecipesRecipeList.getInstance().getRecipeById()
 * remove recipes from getUsers
 * getUsers:
 * Hashmap<user, ArrayList<Recipe UUIDs>
 * After got all Users,
 * fill in Recipes per User
 */






public class DataLoader extends DataConstants{

    /**
    * Loads all users from the Users.json file and creates User objects.
    * Each user includes dietary restrictions and meal plans.
    * 
    * @return list of all users
    */
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        HashMap<User, ArrayList<MealPlan>> userMealPlansMap = new HashMap<>();
        HashMap<User, ArrayList<UUID>> userRecipes = new HashMap<>();
        try {
            FileReader reader = new FileReader(USERS_FILE);
            JSONParser parser = new JSONParser();
            JSONArray userArray = (JSONArray) parser.parse(reader);
            for (int i=0; i < userArray.size(); i++) {
                JSONObject j = (JSONObject)userArray.get(i);
                String firstName = (String) j.get("firstName");
                String lastName = (String) j.get("lastName");
                String email = (String) j.get("email");
                String universityID = (String) j.get("universityID");
                String username = (String) j.get("username");
                String password = (String) j.get("password");
                ArrayList<Dietary> dietList = parseDietaryRestrictions(j);
                ArrayList<MealPlan> mealPlans = parseMealPlans(j);
                JSONArray recipeArray = (JSONArray) j.get("myrecipes");;
                ArrayList<UUID> recipeIDs = new ArrayList();
                for(int k=0; k < recipeArray.size(); k++){
                    recipeIDs.add(UUID.fromString((String)recipeArray.get(k)));
                }
                
                User user = new User(firstName, lastName, email, universityID, username, password, dietList, mealPlans);
                users.add(user);
                userMealPlansMap.put(user, mealPlans);
                userRecipes.put(user, recipeIDs);
            }
            
            RecipeList recipeList = RecipeList.getInstance();
            recipeList.getRecipes();
    
            for (Map.Entry<User, ArrayList<MealPlan>> entry : userMealPlansMap.entrySet()) {
                for (MealPlan mp : entry.getValue()) {
                    ArrayList<Recipe> recipes = new ArrayList<>();
                    for (UUID id : mp.getRecipeIds()) {
                        Recipe r = recipeList.getByID(id);
                        if (r != null) {
                            recipes.add(r);
                        }
                    }
                    mp.setRecipes(recipes);  
                }
            }

            Iterator<User> userRecipeIterator = userRecipes.keySet().iterator();
                while(userRecipeIterator.hasNext()){
                    User user = userRecipeIterator.next();
                    ArrayList<UUID> ids = userRecipes.get(user);
                    for(UUID id : ids) {                       
                       Recipe recipe = RecipeList.getInstance().getByID(id);
                       recipe.setAuthor(user);
                       user.addFavoriteRecipe(recipe);
                    }
                }


        } catch (Exception e) {
            e.printStackTrace();
          }  
    return users;
    }

    /**
    * Parses dietary restrictions from a user JSONObject.
    * 
    * @param j JSONObject representing a user
    * @return list of dietary restrictions for the user
    */
    public static ArrayList<Dietary> parseDietaryRestrictions(JSONObject j) {
        ArrayList<Dietary> dietList = new ArrayList<>();
        JSONArray dietJson = (JSONArray) j.get("dietaryRestrictions");
        if (dietJson != null) {
            for (Object d : dietJson) {
                    dietList.add(Dietary.valueOf(d.toString().toUpperCase()));
            }
        }
        return dietList;
    }

    /**
    * Parses meal plans from a user JSONObject.
    * It also links recipes to the meal plans by matching IDs.
    * 
    * @param j JSONObject representing a user
    * @return list of meal plans for the user
    */
    public static ArrayList<MealPlan> parseMealPlans(JSONObject j) {
        ArrayList<MealPlan> mealPlans = new ArrayList<>();
        JSONArray mealPlanArray = (JSONArray) j.get("mealPlans");
    
        if (mealPlanArray != null) {
            for (Object obj : mealPlanArray) {
                JSONObject planObj = (JSONObject) obj;
                String name = (String) planObj.get("name");
                String mealPlanID = (String) planObj.get("mealPlanID");
                
                ArrayList<UUID> recipesIds = new ArrayList<>();
                Object recipesObj = planObj.get("recipes");

                if (recipesObj instanceof JSONArray) {
                    JSONArray recipesArray = (JSONArray) recipesObj;
                    for (Object recipeIdObj : recipesArray) {
                        try {
                           UUID recipeId = UUID.fromString((String) recipeIdObj);
                           recipesIds.add(recipeId);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Invalid UUID in mealPlan: " + recipeIdObj);
                        }
                    }
                } else {
                    System.err.println("Missing or invalid 'recipes' array in meal plan: " + name);
                }
                MealPlan mealPlan = new MealPlan(name, recipesIds, mealPlanID);
                mealPlans.add(mealPlan);
            }
        }
        return mealPlans;
    }
    

    /**
    * Loads all recipes from the Recipes.json file and creates Recipe objects.
    * 
    * @return list of all recipes
    */
    public static ArrayList<Recipe> getRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            FileReader reader = new FileReader(RECIPES_FILE);
            JSONParser parser = new JSONParser();
            JSONArray recipeArray = (JSONArray) parser.parse(reader);
            for (int i=0; i < recipeArray.size(); i++) {
                JSONObject j = (JSONObject)recipeArray.get(i);
                String name = (String) j.get("name");
                String description = (String) j.get("description");
                int duration = ((Long) j.get("duration")).intValue();
                ArrayList<String> stepsList = parseSteps(j);
                ArrayList<Ingredient> ingredientsList = parseIngredients(j);
                ArrayList<Category> categoriesList = parseCategories(j);             
                String authorUsername = (String) j.get("author");
                User author = null;
                String statusStr = (String) j.get("recipeStatus");
                RecipeStatus recipeStatus = RecipeStatus.NULL;  
                if (statusStr != null) {
                     try {
                        recipeStatus = RecipeStatus.valueOf(statusStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid status: " + statusStr + " - defaulting to NULL");
    }
                } else {
                    System.err.println("Missing statusStr for recipe: " + name);
                }
                Recipe recipe = new Recipe(name, description, duration, stepsList, ingredientsList, categoriesList, author, recipeStatus);
                recipes.add(recipe);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return recipes;
    }
    /**
    * Parses the list of steps for a recipe from its JSONObject.
    * 
    * @param j JSONObject representing a recipe
    * @return list of steps for the recipe
    */
    public static ArrayList<String> parseSteps(JSONObject j){
        ArrayList<String> steps = new ArrayList<>();
        JSONArray stepsArray = (JSONArray) j.get("steps");
        if (stepsArray != null) {
            for (Object step : stepsArray) {
            steps.add((String) step);
            }
        }
        return steps;
    }
    
    /**
    * Parses the list of ingredients for a recipe from its JSONObject.
    * 
    * @param j JSONObject representing a recipe
     * @return list of ingredients for the recipe
    */
    public static ArrayList<Ingredient> parseIngredients(JSONObject j){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        JSONArray ingArray = (JSONArray) j.get("ingredients");
        if (ingArray != null) {
            for (Object ingObj : ingArray) {
                JSONObject ing = (JSONObject) ingObj;
                String ingName = (String) ing.get("name");
                double amount = ((Number) ing.get("amount")).doubleValue();
                Unit unit = Unit.valueOf(((String) ing.get("unit")).toUpperCase());
                ingredients.add(new Ingredient(ingName, amount, unit));
            }
        }
        return ingredients;
    }
    

    /**
    * Parses the list of categories for a recipe from its JSONObject.
    * 
    * @param j JSONObject representing a recipe
    * @return list of categories for the recipe
    */
    public static ArrayList<Category> parseCategories(JSONObject j){
        ArrayList<Category> categories = new ArrayList<>();
        JSONArray catArray = (JSONArray) j.get("categories");
        if (catArray != null) {
            for (Object cat : catArray) {
                try {
                    categories.add(Category.valueOf(cat.toString().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.err.println("Unknown category: " + cat);
                }
            }
        }
        return categories;
    }
    /*
     * Test for getUsers and getRecikpes
     */
    public static void main(String[] args) {
        ArrayList<User> users = DataLoader.getUsers();
        for(User user : users){
         System.out.println(user);
        }
        ArrayList<Recipe> recipes = DataLoader.getRecipes();
        for(Recipe recipe : recipes){
         System.out.println(recipe);
        }
     }
}
    

     

