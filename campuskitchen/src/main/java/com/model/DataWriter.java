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
            JSONObject userObject = new JSONObject();
            userObject.put("firstName", user.getFirstName());
            userObject.put("lastName", user.getLastName());
            userObject.put("email", user.getEmail());
            userObject.put("universityID", user.getUniversityID());
            userObject.put("username", user.getUsername());
            userObject.put("password", user.getPassword());
            userObject.put("dietaryRestrictions", user.getDietaryRestrictions());
            userObject.put("mealPlans", user.getMealPlans());
            userArray.add(userObject);
        }

        File TestFile = new java.io.File("json/Test.json");

        try (FileWriter file = new FileWriter(TestFile)) {
            file.write(userArray.toJSONString());
            file.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }   
}
