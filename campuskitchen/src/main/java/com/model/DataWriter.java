package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

    /**
     * Saves the current list of users to the files
     * Also adds two test users to the list
     */
    
    @SuppressWarnings("unchecked")
    public static void saveUsers() {
        ArrayList<User> userList = UserList.getInstance().getUsers();
        
        userList.add(new User("pplante", "Portia", "Plante", "1234", "pplante", "2309553344"));

        ArrayList<Dietary> kimDietary = new ArrayList<>();
        kimDietary.add(Dietary.VEGETARIAN);
        kimDietary.add(Dietary.GLUTEN_FREE);
        ArrayList<MealPlan> kimMealPlans = new ArrayList<>();
        MealPlan kimPlan = new MealPlan("MP1001", "Kim’s Weekly Plan");
        kimMealPlans.add(kimPlan);
        userList.add(new User("Kim", "Brown", "KBrown", "2345", "kbro", "10062003", kimDietary, kimMealPlans));

        JSONArray userArray = new JSONArray();

        for (User u : userList) {
            userArray.add(getUserJSON(u));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/testWriter.json")) {
            file.write(userArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @SuppressWarnings("unchecked")
    public static void saveMealPlans() {
        ArrayList<MealPlan> mealPlans = MealPlan.getInstance().getMealPlans();
        JSONArray mpJson = new JSONArray();
        for (MealPlan mp : mealPlans) {
            mpJson.add(getMealPlanJSON(mp));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/testWriter.json")) {
            file.write(mpJson.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getMealPlanJSON(MealPlan mealPlan) {
        JSONObject mealPlanDetails = new JSONObject();
        mealPlanDetails.put("id", mealPlan.getID());
        mealPlanDetails.put("name", mealPlan.getName());
        mealPlanDetails.put("recipes", mealPlan.getRecipe());

        return mealPlanDetails;
    }

    @SuppressWarnings("unchecked")
    public static void saveRecipes() {
        ArrayList<Recipe> recipes = RecipeList.getInstance().getRecipes();
        JSONArray recipeJson = new JSONArray();
        for (Recipe r : recipes) {
            recipeJson.add(getRecipeJSON(r));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/testWriter.json")) {
            file.write(recipeJson.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getRecipeJSON(Recipe recipe) {
        JSONObject recipeDetails = new JSONObject();
        recipeDetails.put("id", recipe.getId());
        recipeDetails.put("name", recipe.getName());
        recipeDetails.put("steps", recipe.getSteps());
        return recipeDetails;
    }

    public static void main(String[] args) {
        DataWriter.saveUsers();
        DataWriter.saveRecipes();
        DataWriter.saveMealPlans();
    }
}