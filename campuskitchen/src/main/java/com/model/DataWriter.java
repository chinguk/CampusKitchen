package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

    /**
     * Saves the current list of users to a JSON file.
     * Adds two test users, "pplante" and "Kim", to the list before saving.
     * Each user is converted to a JSON object and added to a JSON array.
     */

    @SuppressWarnings("unchecked")
    public static boolean saveUsers() {
        ArrayList<User> userList = UserList.getInstance().getUsers();

        JSONArray userArray = new JSONArray();

        for (User u : userList) {
            userArray.add(getUserJSON(u));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/Users.json")) {
            file.write(userArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * The JSON object contains the user's first name, last name, email, university
     * ID, username, password, dietary restrictions, and meal plan IDs.
     * The dietary restrictions and meal plan IDs are stored in JSON arrays within
     * the user JSON object.
     */
    @SuppressWarnings("unchecked")
    private static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put("firstName", user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("email", user.getEmail());
        userDetails.put("universityID", user.getUniversityID());
        userDetails.put("username", user.getUsername());
        userDetails.put("password", user.getPassword());

        JSONArray dietJson = new JSONArray();
        if (user.getDietaryRestrictions() != null) {
            for (Dietary d : user.getDietaryRestrictions()) {
                dietJson.add(d.toString());
            }
        }
        userDetails.put("dietaryRestrictions", dietJson);

        JSONArray mpJson = new JSONArray();
        if (user.getMealPlans() != null) {
            for (MealPlan mp : user.getMealPlans()) {
                mpJson.add(mp.getID());
            }
        }
        userDetails.put("mealPlanIDs", mpJson);

        return userDetails;
    }

    /**
     * Saves all meal plans to a JSON file. Each meal plan is converted to a JSON
     * object and added to a JSON array.
     * The JSON object contains the meal plan's name, ID, and list of recipes.
     */
    @SuppressWarnings("unchecked")
    public static void saveMealPlans() {
        ArrayList<MealPlan> allPlans = new ArrayList<>(MealPlan.getInstance().getMealPlans());
        JSONArray mealPlanArray = new JSONArray();
        for (MealPlan mp : allPlans) {
            mealPlanArray.add(getMealPlanJSON(mp));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/Users.json")) {
            file.write(mealPlanArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a MealPlan object to a JSON object with the following fields: id,
     * name, recipes.
     * The id field is the MealPlan's ID, the name field is the MealPlan's name, and
     * the recipes field is the MealPlan's list of recipes.
     */

    @SuppressWarnings("unchecked")
    private static JSONObject getMealPlanJSON(MealPlan mealPlan) {
        JSONObject mealPlanDetails = new JSONObject();
        mealPlanDetails.put("id", mealPlan.getID());

        JSONArray recipesArray = new JSONArray();
        for (Recipe r : mealPlan.getRecipes()) {
            recipesArray.add(r.getId().toString());
        }
        mealPlanDetails.put("recipes", recipesArray);
        
        return mealPlanDetails;
    }

    /**
     * Saves all recipes to a JSON file. Each recipe is converted to a JSON object
     * and added to a JSON array.
     * The JSON object contains the recipe's name, description, duration, steps,
     * ingredients, categories, author, and status.
     */
    @SuppressWarnings("unchecked")
    public static void saveRecipes() {
        ArrayList<Recipe> allRecipes = new ArrayList<>(RecipeList.getInstance().getRecipes());
        JSONArray recipesArray = new JSONArray();
        for (Recipe r : allRecipes) {
            recipesArray.add(getRecipeJSON(r));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/Recipes.json")) {
            file.write(recipesArray.toJSONString());
            file.flush();
            System.out.println("Successfully saved " + allRecipes.size() + " recipes.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a Recipe object to a JSON object with the following fields: id,
     * name, steps.
     * The id field is the Recipe's ID, the name field is the Recipe's name, and the
     * steps field is the Recipe's list of steps.
     */

    @SuppressWarnings("unchecked")
    private static JSONObject getRecipeJSON(Recipe recipe) {
        JSONObject recipeDetails = new JSONObject();
        recipeDetails.put("id", recipe.getId().toString());
        recipeDetails.put("name", recipe.getName());
        recipeDetails.put("user", recipe.getAuthor());
        recipeDetails.put("description", recipe.getDescription());
        recipeDetails.put("duration", recipe.getDuration());
        recipeDetails.put("status", recipe.getStatus());
        recipeDetails.put("recipeStatus", recipe.getStatus());
        recipeDetails.put("author", recipe.getAuthor());
        
        JSONArray ingredientsArray = new JSONArray();
        for (Ingredient ing : recipe.getIngredients()) {
            JSONObject ingObj = new JSONObject();
            ingObj.put("name",   ing.getName());
            ingObj.put("amount", ing.getAmount());
            ingObj.put("unit",   ing.getUnit().toString());
            ingredientsArray.add(ingObj);
        }
        recipeDetails.put("ingredients", ingredientsArray);

        JSONArray categoriesArray = new JSONArray();
        for (String cat : recipe.getCategories()) {
            categoriesArray.add(cat);
        }
        recipeDetails.put("categories", categoriesArray);

        JSONArray ratingsArray = new JSONArray();
        for (Rating rating : recipe.getRatings()) {
            JSONObject ratingObj = new JSONObject();
            ratingObj.put("user",    rating.getUser());
            ratingObj.put("comment", rating.getComment());
            ratingObj.put("date",    rating.getDate());
            ratingObj.put("score",   rating.getScore());
            // “recipe” field points back to this recipe’s ID
            ratingObj.put("recipe",  recipe.getId().toString());
            ratingsArray.add(ratingObj);
        }
        recipeDetails.put("ratings", ratingsArray);

        return recipeDetails;
    }

    /**
     * The main method of the DataWriter class is used to write the users, recipes,
     * and meal plans to their respective JSON files.
     * This method is called when the DataWriter class is run as a Java application.
     */
    // public static void main(String[] args) {
    //     DataWriter.saveUsers();
    //     DataWriter.saveRecipes();
    //     DataWriter.saveMealPlans();
    // }

    private static void testSaveRecipes() {
        System.out.println("=== Running testSaveRecipes() ===");

        // 1) Load existing recipes from disk (via DataLoader)
        ArrayList<Recipe> loaded = DataLoader.getRecipes();
        System.out.println("DataLoader.getRecipes() returned " + loaded.size() + " recipes.");

        // 2) Seed the singleton
        RecipeList.getInstance(loaded);

        // 3) Print each recipe’s basic info
        for (Recipe r : RecipeList.getInstance().getRecipes()) {
            System.out.println("  • Recipe: \"" 
                + r.getName() 
                + "\" (ID=" 
                + r.getId().toString() 
                + ")");
        }

        // 4) Invoke saveRecipes() to write them back out
        saveRecipes();

        // 5) Final confirmation
        System.out.println("testSaveRecipes(): saveRecipes() completed. " +
                           "Open campuskitchen/src/main/json/Recipes.json to verify.");
        System.out.println("=== testSaveRecipes() finished ===");
    }

    public static void main(String[] args) {
        // If you just run DataWriter with no arguments, do the normal saves:
        //   saveUsers(), saveRecipes(), saveMealPlans().
        //
        // But if you pass "test" as the first argument, run our quick test instead.

        if (args.length > 0 && args[0].equalsIgnoreCase("test")) {
            // Only test saving recipes:
            testSaveRecipes();
            return;
        }

        // Normal behavior:
        saveUsers();
        saveRecipes();
        saveMealPlans();
    }
}


