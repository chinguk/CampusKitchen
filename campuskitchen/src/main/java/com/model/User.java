package com.model;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String universityID;
    private String username;
    private String password;
    private ArrayList<Dietary> dietaryRestrictions;
    private ArrayList<MealPlan> mealPlans;

    private User(String firstName, String lastName, String email, String universityID, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.universityID = universityID;
        this.username = username;
        this.password = password;
    }

    public void createAccount(){

    }

    public boolean login(String username, String password){
        
    }

    public void updateProfile(User user){

    }

    public void deleteAccount(){



        
    }
}

