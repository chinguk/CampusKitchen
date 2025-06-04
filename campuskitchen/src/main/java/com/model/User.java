package com.model;

import java.util.ArrayList;
import java.util.List;

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
        this.dietaryRestrictions  = new ArrayList<>();
        this.mealPlans = mealPlans = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversityID() {
        return universityID;
    }

    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Dietary> getDietaryRestrictions() {
        return dietaryRestrictions;
    }
    
    public void setDietaryRestrictions(ArrayList<Dietary> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public ArrayList<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(ArrayList<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public void createAccount(){
        UserList list = UserList.getInstance();

        list.addUser(
            this.firstName,
            this.lastName,
            this.email,
            this.universityID,
            this.username,
            this.password
        );
    }

    public boolean login(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    public void updateProfile(User user){
        if (user == null) return;
        this.firstName = user.firstName;
        this.lastName  = user.lastName;
        this.email = user.email;
        this.universityID = user.universityID;
        this.username = user.username;
        this.password = user.password;

        this.dietaryRestrictions = new ArrayList<>(user.dietaryRestrictions);
        this.mealPlans = new ArrayList<>(user.mealPlans);
    }

    public void deleteAccount(){
        UserList list = UserList.getInstance();
        list.removeUser(this.username);

    }

    public String toString() {
       return firstName + " " + lastName + " " + email + " " + universityID + " " + username + " " + password
         + " | Dietary: " + dietaryRestrictions
         + " | MealPlans: " + mealPlans;
    }
    
}

