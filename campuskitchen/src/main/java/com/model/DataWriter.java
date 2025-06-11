package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return 
     */
    @SuppressWarnings("unchecked")
    public static boolean saveRecipes() {
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

        return recipeDetails;
    }

    // /**
    // * Generates grocery list for recipes in meal plans
    // *
    // * @param mealPlan Meal plan to generate list for
    // * @return List of ingredients
    // */
    // public List<Ingredient> generateGroceryList(MealPlan mealPlan) {
    // if (mealPlan == null) {
    // return null;
    // }
    // List<Ingredient> groceryList = mealPlan.generateGroceryList();
    // writeGroceryListToFile(mealPlan, groceryList);
    // return groceryList;
    // }
    // // Pass in the name of the meal plan (User.groceryList)

    // /**
    // * Saves grocery list of meal plan to text file
    // *
    // * @param mealPlan Meal plan from which the list is generated
    // * @param groceryList List of ingredients to write
    // */
    // private void writeGroceryListToFile(MealPlan mealPlan, List<Ingredient>
    // groceryList) {
    // String fileName = "grocerylist_" + mealPlan.getID() + ".txt";
    // try (FileWriter writer = new FileWriter(fileName)) {
    // writer.write("Grocery List for MealPlan \"" + mealPlan.getName() + "\" (ID="
    // + mealPlan.getID() + ")\n");
    // for (Ingredient ing : groceryList) {
    // writer.write(ing.getName() + ": " + ing.getAmount() + " " +
    // ing.getUnit().name() + "\n");
    // }
    // writer.flush();
    // System.out.println("Wrote grocery list to " + fileName);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    public static void generateGroceryList(Object plan) {
        if (plan == null || ((MealPlan) plan).getRecipes().isEmpty()) {
            System.out.println("No recipes in meal plan; skipping grocery‐list generation.");
            return;
        }

        // 1) Gather every Ingredient from each Recipe in the plan
        // and sum up quantities for identical ingredient names.
        // We'll key by ingredientName + unit
        Map<String, Double> Quantities = new HashMap<>();
        Map<String, Unit> ingredientUnit = new HashMap<>();

        for (Recipe r : ((MealPlan) plan).getRecipes()) {
            ArrayList<Ingredient> ings = r.generateGroceryList();

            for (Ingredient ing : ings) {
                String name = ing.getName();
                Unit unit = ing.getUnit();
                double amount = ing.getAmount();

                String key = name + "#" + unit.toString();
                double prev = Quantities.getOrDefault(key, 0.0);
                Quantities.put(key, prev + amount);
                ingredientUnit.put(key, unit);
            }
        }

        // 2) Write out to a text file.
        String outFile = "campuskitchen/src/main/resources/grocerylist_" + ((MealPlan) plan).getID() + ".txt";

        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write("Grocery List for MealPlan ID: " + ((MealPlan) plan).getID() + "\n");
            writer.write("––––––––––––––––––––––––––––––––––––––––––––\n");

            for (Map.Entry<String, Double> entry : Quantities.entrySet()) {
                String Key = entry.getKey();
                double totalAmt = entry.getValue();
                Unit unit = ingredientUnit.get(Key);
                String name = Key.split("#")[0];

                // Format: "2.0 pcs Tomato"
                writer.write(String.format("%.2f %s %s%n", totalAmt, unit.toString(), name));
            }

            System.out.println("Wrote grocery list to: " + outFile);
        } catch (IOException e) {
            System.err.println("Error writing grocery‐list file: " + e.getMessage());
        }
    }


    //Test generateGroceryList
    public static void main(String[] args) {
        List<MealPlan> plan = MealPlan.getInstance().getMealPlans();
        generateGroceryList(plan);
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
}