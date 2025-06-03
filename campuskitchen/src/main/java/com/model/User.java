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

    protected User(String firstName, String lastName, String email, String universityID, String username, String password){
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
        return this.username.equals(username) && this.password.equals(password);

    }

    public void updateProfile(User user){
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.universityID = user.universityID;
        this.username = user.username;
        this.password = user.password;

        // replace entire lists with copies of incoming user's lists
        this.dietaryRestrictions = new ArrayList<>(user.dietaryRestrictions);
        this.mealPlans = new ArrayList<>(user.mealPlans);
    }

    public void deleteAccount(){
        
    }

    public String toString() {
        return firstName + " " + lastName + " " + email + " " + universityID + " " + username + " " + password;
    }
}

