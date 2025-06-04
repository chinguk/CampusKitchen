package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

    @SuppressWarnings("unchecked")
    public static void saveUsers() {
        ArrayList<User> userList = UserList.getInstance().getUsers();
        userList.add(new User("pplante", "Portia", "Plante", "1234", "pplante", "2309553344"));
        userList.add(new User("Kim", "Brown", "KBrown", "2345", "kbro", "10062003", new ArrayList<MealPlan>(), new ArrayList<Dietary>()));

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
        userDetails.put("mealPlans", user.getMealPlans());
        userDetails.put("dietaryRestrictions", user.getDietaryRestrictions());

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

    /*public static void saveMealPlans() {
        ArrayList<MealPlan> mealPlans = MealPlan.getInstance().getMealPlans();
        JSONArray mpJson = new JSONArray();
        for (MealPlan mp : mealPlans) {
            mpJson.add(getMealPlanJSON(mp));
        }
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/MealPlans.json")) {
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
    }*/

    public static void main(String[] args) {
        DataWriter.saveUsers();
    }
}