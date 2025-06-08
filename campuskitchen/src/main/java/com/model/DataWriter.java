package com.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

        JSONArray mealPlansArray = new JSONArray();
        if (user.getMealPlans() != null && !user.getMealPlans().isEmpty()) {
            for (MealPlan mp : user.getMealPlans()) {
                JSONObject planObj = new JSONObject();
                planObj.put("name", mp.getName());
                planObj.put("mealPlanIDs", mp.getID());
                // build the recipes array
                JSONArray recipesArray = new JSONArray();
                for (Recipe r : mp.getRecipes()) {
                    recipesArray.add(r.getId().toString());
                }
                planObj.put("recipes", recipesArray);
                mealPlansArray.add(planObj);
            }
        }
        userDetails.put("mealPlans", mealPlansArray);

        return userDetails;
    }

    /**
     * Saves all meal plans to a JSON file. Each meal plan is converted to a JSON
     * object and added to a JSON array.
     * The JSON object contains the meal plan's name, ID, and list of recipes.
     */
    public static void saveMealPlans() {
        saveUsers();
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
        mealPlanDetails.put("mealPlanIDs", mealPlan.getID());
        mealPlanDetails.put("name", mealPlan.getName());

        JSONArray recipesArray = new JSONArray();
        if (mealPlan.getRecipes() != null) {
            for (Recipe r : mealPlan.getRecipes()) {
                recipesArray.add(r.getId().toString());
            }
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
        String path = "campuskitchen/src/main/json/Recipes.json";

        // 1) Parse existing JSON array (if file exists)
        JSONArray existingArray;
        try (FileReader reader = new FileReader(path)) {
            existingArray = (JSONArray) new JSONParser().parse(reader);
        } catch (Exception e) {
            // If file not found or parse error, start fresh
            existingArray = new JSONArray();
        }

        // 2) Build a map of IDs to JSONObject for quick lookups
        Map<String, JSONObject> byId = new HashMap<>();
        for (Object o : existingArray) {
            JSONObject obj = (JSONObject) o;
            byId.put((String) obj.get("id"), obj);
        }

        // 3) Merge or add each in‐memory Recipe
        for (Recipe r : RecipeList.getInstance().getRecipes()) {
            JSONObject newJson = getRecipeJSON(r);
            byId.put(r.getId().toString(), newJson);
        }

        // 4) Reconstruct the JSONArray
        JSONArray merged = new JSONArray();
        merged.addAll(byId.values());

        // 5) Write merged array back to disk
        try (FileWriter file = new FileWriter(path)) {
            file.write(merged.toJSONString());
            file.flush();
            System.out.println("Merged and saved " + merged.size() + " recipes.");
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
            ingObj.put("name", ing.getName());
            ingObj.put("amount", ing.getAmount());
            ingObj.put("unit", ing.getUnit().toString());
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
            ratingObj.put("user", rating.getUser());
            ratingObj.put("comment", rating.getComment());
            ratingObj.put("date", rating.getDate());
            ratingObj.put("score", rating.getScore());

            ratingObj.put("recipe", recipe.getId().toString());
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
    // DataWriter.saveUsers();
    // DataWriter.saveRecipes();
    // DataWriter.saveMealPlans();
    // }






// private static void testPrintUsersJSON() {
//     System.out.println("=== testPrintUsersJSON ===");

//     // 1) Load in-memory users
//     ArrayList<User> users = DataLoader.getUsers();
//     UserList.getInstance();

//     // 2) Build a JSONArray of JSONObjects
//     JSONArray arr = new JSONArray();
//     for (User u : UserList.getInstance().getUsers()) {
//         arr.add(getUserJSON(u));
//     }

//     // 3) Print it raw (compact) – for pretty, you can manually indent
//     System.out.println(arr.toJSONString());
// }


    private static void testSaveRecipes() {
        System.out.println("=== testSaveRecipes ===");

        // 1) Load & seed
        ArrayList<Recipe> recipes = DataLoader.getRecipes();
        System.out.println("Loaded " + recipes.size() + " recipes.");
        RecipeList.getInstance(recipes);

        // 2) Print each recipe
        for (Recipe r : RecipeList.getInstance().getRecipes()) {
            System.out.println("  - " + r.getName() + " (ID=" + r.getId() + ")");
        }

        // 3) Save
        saveRecipes();
        System.out.println("saveRecipes() completed. Check Recipes.json.\n");
    }

    // private static void testSaveUserMealPlan() {
    //     System.out.println("=== testSaveUserMealPlan ===");

    //     // 1) Load users (this also parses existing mealPlans for each user)
    //     ArrayList<User> users = DataLoader.getUsers();
    //     System.out.println("Loaded " + users.size() + " users.");
    //     UserList.getInstance();

    //     // 2) Load recipes (so we can build a new plan)
    //     ArrayList<Recipe> recipes = DataLoader.getRecipes();
    //     RecipeList.getInstance(recipes);
    //     System.out.println("Loaded " + recipes.size() + " recipes for plan creation.");

    //     // 3) Pick first user & show how many plans they have now
    //     User u = users.get(0);
    //     System.out.println("User '" + u.getUsername() + "' has "
    //             + u.getMealPlans().size() + " mealPlans before.");

    //     // 4) Create a dummy plan (with the first recipe, if any) and attach
    //     ArrayList<Recipe> oneRecipe = new ArrayList<Recipe>();
    //     if (!recipes.isEmpty()) {
    //         oneRecipe.add(recipes.get(0));
    //     }
    //     MealPlan dummy = new MealPlan("Dummy Plan", oneRecipe);
    //     u.getMealPlans().add(dummy);
    //     System.out.println("Added Dummy Plan (ID=" + dummy.getID() + ").");

    //     // 5) Persist users (which now includes the new plan under "mealPlans")
    //     saveUsers();
    //     System.out.println("saveUsers() completed. Check Users.json for nested mealPlans.\n");
    // }

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("testRecipes")) {
            testSaveRecipes();
        } else {
            // Normal behavior
            saveUsers();
            saveRecipes();
            saveMealPlans();
        }
    }
}