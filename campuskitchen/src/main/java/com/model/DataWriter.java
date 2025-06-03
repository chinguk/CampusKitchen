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
     */
    public static void saveUsers() {
       // User users = User.getInstance();
		//ArrayList<User> userList = users.getUsers();

        ArrayList<User> userList = new ArrayList();
		userList.add(new User("pplante", "Portia", "Plante", "1234", "pplante", "2309553344"));
        JSONArray userArray = new JSONArray();

        for(int i=0; i< userList.size(); i++) {
			userArray.add(userList.get(i));
		}
		
        try (FileWriter file = new FileWriter("json/user_temp.json")) {
 
            file.write(userArray.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    

        for (User user : users) {
            JSONObject j = new JSONObject();
            j.put("firstName", user.getFirstName());
            j.put("lastName", user.getLastName());
            j.put("email", user.getEmail());
            j.put("universityID", user.getUniversityID());
            j.put("username", user.getUsername());
            j.put("password", user.getPassword());
        }

           /*  JSONArray dietJson = new JSONArray();
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
    }   */
        public static void main(String[] args){
            DataWriter.saveUsers();
        }
    }
}

