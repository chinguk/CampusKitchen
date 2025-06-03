package com.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import junit.framework.Test;

public class DataWriter extends DataConstants {
    
    public static void saveUsers(ArrayList<User> users) {
        JSONArray userArray = new JSONArray();

        for (User user : users) {
            JSONObject j = new JSONObject();
            j.put("firstName", user.getFirstName());
            j.put("lastName", user.getLastName());
            j.put("email", user.getEmail());
            j.put("universityID", user.getUniversityID());
            j.put("username", user.getUsername());
            j.put("password", user.getPassword());

            JSONArray dietJson = new JSONArray();
            if (user.getDietaryRestrictions() != null) {
                for (Dietary d : user.getDietaryRestrictions()) {
                    dietJson.add(d.toString());
                }
            }
            j.put("dietaryRestrictions", dietJson);

            JSONArray mpJson = new JSONArray();
            if (user.getMealPlans() != null) {
                for (MealPlan mp : user.getMealPlans()) {
                    mpJson.add(mp.getId());
                }
            }
            j.put("mealPlanIDs", mpJson);

            userArray.add(j);
        }

        try (FileWriter file = new FileWriter(new File("json/Users.json"))) {
            file.write(userArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
