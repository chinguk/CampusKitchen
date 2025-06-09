package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class DataLoader {

    /**
    * Loads all users from the Users.json file and creates User objects.
    * Each user includes dietary restrictions and meal plans.
    * 
    * @return list of all users
    */
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader reader = new FileReader("campuskitchen/src/main/json/Users.json");
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
                User user = new User(firstName, lastName, email, universityID, username, password, dietList, mealPlans);
                users.add(user);
            }
            return users;
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
        ArrayList<Recipe> allRecipes = getRecipes();
    
        if (mealPlanArray != null) {
            for (Object obj : mealPlanArray) {
                JSONObject planObj = (JSONObject) obj;
                String name = (String) planObj.get("name");
                String mealPlanID = (String) planObj.get("mealPlanIDs");
                JSONArray recipesArray = (JSONArray) planObj.get("recipes");
    
                ArrayList<Recipe> recipes = new ArrayList<>();
                if (recipesArray != null) {
                    for (Object recipeIdObj : recipesArray) {
                        String recipeIdStr = (String) recipeIdObj;
                        UUID recipeId = UUID.fromString(recipeIdStr);
                        Recipe matchedRecipe = findRecipeById(allRecipes, recipeId);
                        if (matchedRecipe != null) {
                            recipes.add(matchedRecipe);
                        }
                    }
                }
    
                MealPlan mealPlan = new MealPlan(name, recipes, mealPlanID);
                mealPlans.add(mealPlan);
            }
        }
        return mealPlans;
    }
    
    /**
    * Finds a recipe in the given list by matching its UUID.
    * 
    * @param recipes list of all recipes
    * @param id the UUID to search for
    * @return the matching Recipe or null if not found
    */
    private static Recipe findRecipeById(ArrayList<Recipe> recipes, UUID id) {
        for (Recipe recipe : recipes) {
            if (recipe.getId().equals(id)) {
                return recipe;
            }
        }
        return null;
    }
    

    /**
    * Loads all recipes from the Recipes.json file and creates Recipe objects.
    * 
    * @return list of all recipes
    */
    public static ArrayList<Recipe> getRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            FileReader reader = new FileReader("campuskitchen/src/main/json/Recipes.json");
            JSONParser parser = new JSONParser();
            JSONArray recipeArray = (JSONArray) parser.parse(reader);
            for (int i=0; i < recipeArray.size(); i++) {
                JSONObject j = (JSONObject)recipeArray.get(i);
                String name = (String) j.get("name");
                String description = (String) j.get("description");
                int duration = ((Long) j.get("duration")).intValue();
                ArrayList<String> stepsList = parseSteps(j);
                ArrayList<Ingredient> ingredientsList = parseIngredients(j);
                ArrayList<String> categoriesList = parseCategories(j);
                User author = (User) j.get("author");
                RecipeStatus recipeStatus = (RecipeStatus) j.get("recipestatus");
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
                Unit unit = (Unit) ing.get("unit");
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
    public static ArrayList<String> parseCategories(JSONObject j){
        ArrayList<String> categories = new ArrayList<>();
        JSONArray catArray = (JSONArray) j.get("categories");
        if (catArray != null) {
            for (Object cat : catArray) {
                categories.add((String) cat);
            }
        }
        return categories;
    }

}

     

