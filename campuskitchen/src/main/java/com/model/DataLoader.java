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
                System.out.println(firstName);
                ArrayList<Dietary> dietList = parseDietaryRestrictions(j);
                ArrayList<String> mealPlans = parseMealPlanIDs(j);
                System.out.println(dietList);
                System.out.println(mealPlans);
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

    public static ArrayList<String> parseMealPlanIDs(JSONObject j) {
        ArrayList<String> mealPlanIds = new ArrayList<>();
        JSONArray idArray = (JSONArray) j.get("mealPlanIDs");
        if (idArray != null) {
            for (Object id : idArray) {
                mealPlanIds.add(id.toString());
            }
        }
        return mealPlanIds;
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
