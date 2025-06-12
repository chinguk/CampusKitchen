package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

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
        try (FileWriter file = new FileWriter(USERS_FILE)) {
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
                mpJson.add(getMealPlanJSON(mp));
            }
        }
        userDetails.put("mealPlans", mpJson);

        return userDetails;
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
        mealPlanDetails.put("mealPlanID", mealPlan.getID());
        mealPlanDetails.put("name", mealPlan.getName());

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
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean saveRecipes() {
        ArrayList<Recipe> allRecipes = new ArrayList<>(RecipeList.getInstance().getRecipes());
        JSONArray recipesArray = new JSONArray();
        for (Recipe r : allRecipes) {
            recipesArray.add(getRecipeJSON(r));
        }
        try (FileWriter file = new FileWriter(RECIPES_FILE)) {
            file.write(recipesArray.toJSONString());
            file.flush();
            System.out.println("Successfully saved " + allRecipes.size() + " recipes.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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
            // “recipe” field points back to this recipe’s ID
            ratingObj.put("recipe", recipe.getId().toString());
            ratingsArray.add(ratingObj);
        }
        recipeDetails.put("ratings", ratingsArray);

        JSONArray stepsArray = new JSONArray();
        for (String step : recipe.getSteps()) {
            stepsArray.add(step);
        }
        recipeDetails.put("steps", stepsArray);

        return recipeDetails;
    }

    /**
     * The main method of the DataWriter class is used to write the users, recipes,
     * and meal plans to their respective JSON files.
     * This method is called when the DataWriter class is run as a Java application.
     */
    public static void main(String[] args) {
        DataWriter.saveUsers();
        DataWriter.saveRecipes();
    }






 

    
/**
 * Generates a grocery list from the provided meal plan and writes it to a file.
 * If the meal plan is null, returns an empty list.
 * Updates the static User.groceryList with the generated list.
 *
 * @param mealPlan the meal plan from which to generate the grocery list
 * @return a list of ingredients representing the grocery list
 */

    public List<Ingredient> generateGroceryList(MealPlan mealPlan) {
        if (mealPlan == null) {
            return Collections.emptyList();
        }
        List<Ingredient> groceryList = mealPlan.generateGroceryList();
        writeGroceryListToFile(mealPlan, groceryList);
        User.groceryList = groceryList;
        
        return User.groceryList;
    }

    
/**
 * Writes the grocery list of a given meal plan to a text file.
 * The file is named using the meal plan's ID and includes each ingredient's
 * name, amount, and unit. If an error occurs during file writing, the
 * stack trace is printed.
 *
 * @param mealPlan the meal plan whose grocery list is to be written
 * @param groceryList the list of ingredients to write to the file
 */

    private void writeGroceryListToFile(MealPlan mealPlan, List<Ingredient> groceryList) {
        String fileName = "grocerylist_" + mealPlan.getID() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Grocery List for MealPlan \"" + mealPlan.getName() + "\" (ID="
                    + mealPlan.getID() + ")\n");
            for (Ingredient ing : groceryList) {
                writer.write(ing.getName() + ": " + ing.getAmount() + " " +
                        ing.getUnit().name() + "\n");
            }
            writer.flush();
            System.out.println("Wrote grocery list to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}