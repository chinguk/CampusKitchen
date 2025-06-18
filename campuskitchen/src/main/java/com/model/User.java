package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents user and stores personal information, dietary restrictions, and meal plans
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String universityID;
    private String username;
    private String password;
    private ArrayList<Dietary> dietaryRestrictions;
    private ArrayList<MealPlan> mealPlans;
    public static List<Ingredient> groceryList;
    private ArrayList<Recipe> mealPlanRecipes;
    private ArrayList<UUID> myRecipesIds;


    /**
     * Constructor, creates new User with no initial dietary restrictions or meal plans
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param universityID User's ID
     * @param username User's login username
     * @param password User's password
     */
    protected User(String firstName, String lastName, String email, String universityID, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.universityID = universityID;
        this.username = username;
        this.password = password;

        this.dietaryRestrictions = new ArrayList<>();
        this.mealPlans = new ArrayList<>();
    }

    /**
     * Overloaded constructor with existing dietary restictions and mealplans
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param universityID User's ID
     * @param username User's login username
     * @param password User's password
     * @param dietaryRestrictions Initial dietary restrictions
     * @param mealPlans Initial meal plans
     */
    public User(String firstName, String lastName, String email, String universityID, String username, String password, ArrayList<Dietary> dietaryRestrictions, ArrayList<MealPlan> mealPlans, ArrayList<UUID> myRecipeIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.universityID = universityID;
        this.username = username;
        this.password = password;

        this.dietaryRestrictions = (dietaryRestrictions != null) ? new ArrayList<>(dietaryRestrictions) : new ArrayList<>();
        this.mealPlans = (mealPlans != null) ? new ArrayList<>(mealPlans) : new ArrayList<>(); 
        this.myRecipesIds = new ArrayList<>();
    }

    /**
     * Getter for user's first name
     * @return User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for user's first name
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for user's last name
     * @return User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for user's last name
     * @param lastName new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<UUID> getRecipesIds(){
        return myRecipesIds;
    }

    /**
     * Getter for user's email
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for user's email
     * @return new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for university ID
     * @return User's ID
     */
    public String getUniversityID() {
        return universityID;
    }

    /**
     * Setter for university ID
     * @param universityID new university ID
     */
    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    /**
     * Getter for User's username
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for user's username
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for user's password
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for user's password
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for Dietary Restrictions
     * @return List of dietary restrictions
     */
    public List<Dietary> getDietaryRestrictions() {
        return dietaryRestrictions;
    }
    
    /**
     * Setter for Dietary Restrictions
     * @param dietaryRestrictions Dietary restrictions
     */
    public void setDietaryRestrictions(ArrayList<Dietary> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    /**
     * Getter for user's meal plans
     * @return User's meal plans
     */
    public ArrayList<MealPlan> getMealPlans() {
        return mealPlans;
    }

    /**
     * Setter for user's meal plans
     * @param mealPlans Meal plans
     */
    public void setMealPlans(ArrayList<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    /**
     * Updates profile of user
     * @param user User whose data will be updated
     */
    public void updateProfile(User user) {
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

    /**
     * Creates a new MealPlan and adds it to the user's meal plans
     * @param name Name of the meal plan
     * @param recipes Recipes to add to the meal plan
     */
    public void createMealPlan(String name, ArrayList<Recipe> recipes) { 
        mealPlans.add(new MealPlan(name, recipes));
    }

    /**
     * String representation of User, returns user's information
     */
    @Override
    public String toString() {
       return firstName + " " + lastName + " " + email + " " + universityID + " " + username + " " + password
         + " | Dietary: " + dietaryRestrictions
         + " | MealPlans: " + mealPlans;
    }

    private static User instance = null;

    private static ArrayList<User> users = new ArrayList<>();

    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void addFavoriteRecipe(Recipe recipe){
        this.mealPlanRecipes.add(recipe);
    }

    public ArrayList<Recipe> getFavoriteRecipes() {
        if (myRecipesIds == null || myRecipesIds.isEmpty()) {
            return new ArrayList<>();
        }
    
        RecipeList recipeList = RecipeList.getInstance();
        return recipeList.getByIDs(myRecipesIds);
    }

    
}

