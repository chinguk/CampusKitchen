package com.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {
    
    /**
     * Saves a list of users to a JSON file.
     * 
     * @param users The list of users to be saved.
     * @return 
     */
    public static void saveUsers() {
        // User users = User.getInstance();
		//ArrayList<User> userList = users.getUsers();

        ArrayList<User> userList = new ArrayList();
		userList.add(new User("pplante", "Portia", "Plante", "1234", "pplante", "2309553344"));
        JSONArray userArray = new JSONArray();

        for(int i=0; i< userList.size(); i++) {
			userArray.add(getUserJSON(userList.get(i)));
		}
		
        try (FileWriter file = new FileWriter("campuskitchen/src/main/json/testWriter.json")) {
 
            file.write(userArray.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        public static JSONObject getUserJSON(User user) {
                JSONObject userDetails = new JSONObject();
                userDetails.put("firstName", user.getFirstName());
                userDetails.put("lastName", user.getLastName());
                userDetails.put("email", user.getEmail());
                userDetails.put("universityID", user.getUniversityID());
                userDetails.put("username", user.getUsername());
                userDetails.put("password", user.getPassword());

                return userDetails;
        }    

        /*
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

        try (FileWriter file = new FileWriter(new File("json/Test.json"))) {
            file.write(userArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
            */ 
        public static void main(String[] args){
            DataWriter.saveUsers();
        }
}


