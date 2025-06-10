package com.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataWriter {

    /**
     * Saves the current list of users to a JSON file.
     * Each user is converted to a JSON object and added to a JSON array.
     * @throws org.json.simple.parser.ParseException
     */

    @SuppressWarnings("unchecked")
    public static boolean saveUsers() throws ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        File file = new File("campuskitchen/src/main/json/Users.json");
        JSONArray merged = new JSONArray();

        // 1) load existing
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                merged = (JSONArray) parser.parse(fr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Object ob : merged) {
            JSONObject jo = (JSONObject) ob;
            if (jo.containsKey("mealPlanIDs") && jo.get("mealPlanIDs") instanceof JSONObject) {
                // pull out the old object
                JSONObject oldPlan = (JSONObject) jo.remove("mealPlanIDs");
                // wrap it in a new array
                JSONArray fixed = new JSONArray();
                fixed.add(oldPlan);
                // replace or set the correct key
                jo.put("mealPlans", fixed);
            }
        }

        // 2) index by username
        Map<String, JSONObject> byUsername = new LinkedHashMap<>();
        for (Object ob : merged) {
            JSONObject jo = (JSONObject) ob;
            byUsername.put((String) jo.get("username"), jo);
        }

        // 3) overlay in-memory users
        for (User u : UserList.getInstance().getUsers()) {
            JSONObject jo = getUserJSON(u);
            byUsername.put(u.getUsername(), jo);
        }

        // 4) write back
        JSONArray out = new JSONArray();
        out.addAll(byUsername.values());
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(out.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

        JSONArray mealPlansJson = new JSONArray();
        if (user.getMealPlans() != null) {
            for (MealPlan mp : user.getMealPlans()) {
                JSONObject mpObj = new JSONObject();
                mpObj.put("name", mp.getName());
                mpObj.put("mealPlanIDs", mp.getID().toString());
                mpObj.put("recipes", mp.getRecipes());
                mealPlansJson.add(mpObj);

                JSONArray recipesArr = new JSONArray();
                for (Recipe r : mp.getRecipes()) {
                    recipesArr.add(r.getId().toString());
                }
                mpObj.put("recipes", recipesArr);

                mealPlansJson.add(mpObj);
            }
        }
        userDetails.put("mealPlans", mealPlansJson);

        return userDetails;
    }

    /**
     * Saves all recipes to a JSON file. Each recipe is converted to a JSON object
     * and added to a JSON array. 
     * The JSON object contains the recipe's name, description, duration, steps,
     * ingredients, categories, author, and status.
     * 
     * @throws org.json.simple.parser.ParseException
     */
    @SuppressWarnings("unchecked")
    public static boolean saveRecipes() throws ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        File file = new File("campuskitchen/src/main/json/Recipes.json");
        JSONArray merged = new JSONArray();

        // 1) load existing
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                merged = (JSONArray) parser.parse(fr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 2) index by recipe ID
        Map<String, JSONObject> byId = new LinkedHashMap<>();
        for (Object o : merged) {
            JSONObject jo = (JSONObject) o;
            byId.put((String) jo.get("id"), jo);
        }

        // 3) overlay in-memory recipes
        for (Recipe r : RecipeList.getInstance().getRecipes()) {
            JSONObject jo = getRecipeJSON(r);
            byId.put(r.getId().toString(), jo);
        }

        // 4) write back
        JSONArray out = new JSONArray();
        out.addAll(byId.values());
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(out.toJSONString());
            fw.flush();
            System.out.println("Successfully saved " + byId.size() + " recipes.");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
        recipeDetails.put("user", recipe.getAuthor().getUsername());
        recipeDetails.put("description", recipe.getDescription());
        recipeDetails.put("duration", recipe.getDuration());
        recipeDetails.put("status", recipe.getStatus().name());
        recipeDetails.put("recipeStatus", recipe.getStatus().name());
        recipeDetails.put("author", recipe.getAuthor().getUsername());

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

        return recipeDetails;
    }

    /**
     * The main method of the DataWriter class is used to write the users, recipes,
     * and meal plans to their respective JSON files.
     * This method is called when the DataWriter class is run as a Java application.
     * 
     * @throws org.json.simple.parser.ParseException
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException, org.json.simple.parser.ParseException {
        DataWriter.saveUsers();
        DataWriter.saveRecipes();
    }
}