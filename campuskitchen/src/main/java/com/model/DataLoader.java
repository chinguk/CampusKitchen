package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 * Questions:
 * -Do we need addRecipe()?
 * -should recipes in json be a String value of IDs(because that is what it is)
 */


import java.util.ArrayList;

public class DataLoader {
    
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

    public static ArrayList<MealPlan> parseMealPlans(JSONObject j) {
        ArrayList<MealPlan> mealPlans = new ArrayList<>();
        JSONArray mealPlanArray = (JSONArray) j.get("mealPlans");
        if (mealPlanArray != null) {
            for (Object obj : mealPlanArray) {
                JSONObject planObj = (JSONObject) obj;
                String name = (String) planObj.get("name");
                String mealPlanID = (String) planObj.get("mealPlanIDs");
                JSONArray recipesArray = (JSONArray) planObj.get("recipes");
                ArrayList<Recipe> recipes = new ArrayList<>();
                if (recipesArray != null) {
                    for (Object recipeIdObj : recipesArray) {
                        //String recipeId = (String) recipeIdObj;
                        //recipes.add(new Recipe(recipeId));
                    }
                }
                MealPlan mealPlan = new MealPlan(name, recipes, mealPlanID);
                mealPlans.add(mealPlan);
            }
        }
        return mealPlans;
    }

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
                recipe.add(recipe);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return recipes;
    }

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

    
    
    
    


    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
       ArrayList<User> users = DataLoader.getUsers();
       for(User user : users){
        System.out.println(user);
       }
       /*ArrayList<Recipe> recipes = DataLoader.getRecipes();
       for(Recipe recipe : recipes){
        System.out.println(recipe);
       }*/
    }

}

     

