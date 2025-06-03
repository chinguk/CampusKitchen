package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.util.ArrayList;

public class DataLoader {
    
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/mainjson/Users.json");
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
                ArrayList<Dietary> dietList = new ArrayList<>();
                System.out.println(firstName);
                /*JSONArray dietJson = (JSONArray) j.get(dietaryRestrictions);
                if (dietJson != null) {
                    for (Object d : dietJson) {
                        dietList.add(Dietary.valueOf((String) d));
                    }
                }
                ArrayList<MealPlan> mealPlans = new ArrayList<>();
                JSONArray mpJson = (JSONArray) j.get(mealPlanIDs);
                if (mpJson != null) {
                    for (Object id : mpJson) {
                        String mpId = id.toString();
                        MealPlan mp = MealPlan.getMealPlanId(mpId); 
                        if (mp != null) {
                            mealPlans.add(mp);
                        }
                    }
                } */
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
          }  
    return users;
    }

    public static void main(String[] args) {
        DataLoader.getUsers();
    }

    public static ArrayList<Recipe> getRecipes(){

    }

    public static ArrayList<MealPlan> getMealPlans(){

    }

    public static ArrayList<Ingredients> getIngredients(){

    }

    public static ArrayList<Admin> getAdmin(){

    }

    public static ArrayList<Recipe> getRecipes(){

    }
}
