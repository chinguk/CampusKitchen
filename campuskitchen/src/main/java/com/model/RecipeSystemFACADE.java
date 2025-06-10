package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Facade for recipe system that handles account management, recipe operations,
 * meal plan creation, and grocery list generation. Implements singleton pattern
 */
public class RecipeSystemFACADE {
    private static final User NEW_USER = null;
    private User user;
    private static RecipeSystemFACADE recipeSystemFACADE;

    /**
     * Private constructor to enforce singleton pattern
     */
    private RecipeSystemFACADE() {}

    /**
     * Returns singleton instance of facade and creates it if necessary
     * @return single RecipeSystemFACADE instance
     */
    public static RecipeSystemFACADE getInstance() {
        if(recipeSystemFACADE == null) {
            recipeSystemFACADE = new RecipeSystemFACADE();
        }
        return recipeSystemFACADE;
    }

    /**
     * Create new user account in the system
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param universityID user's ID
     * @param username user's username
     * @param password user's password
     * @return True if creates account, false otherwise
     */
    public boolean createAccount(String firstName, String lastName, String email, String universityID, String username, String password) {
        return UserList.getInstance().addUser(firstName, lastName, email, universityID, username, password);
    }

    /**
     * Updates profile information of currently logged in user
     * @param user Updated User
     */
    public void updateProfile(User user) {

    }

    /**
     * Logs user into system
     * @param username Username to authenticate
     * @param password Password to authenticate
     * @return User if login succeeds, null otherwise
     */
    public User login(String username, String password) {
        return UserList.getInstance().getUser(username);
    }

    /**
     * Retrieves recipe matching keyword
     * @param word Search word
     * @return matching recipe or null if not found
     */
    public Recipe getRecipeByKeyWord(String word) {
        return null;
    }

    /**
     * Returns all recipes in the system
     * @return All recipes
     */
    public ArrayList<Recipe> getAllRecipe() {
        return null;
    }

    public void submitRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients,
                                ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, User author,
                                RecipeStatus status) {

    }

    public void approveRecipe(Recipe recipe) {

    }

    public void deleteRecipe(Recipe recipe) {

    }

    public void rateRecipe(Recipe recipe, Rating rating) {

    }

    // can just call from recipes
    public double getAverageRating(Recipe recipe) {
        return 0;
    }

    /**
     * Creates new meal plan for logged in user and generates grocery list file
     * @param name Name of meal plan
     * @param recipes list of recipes to include
     * NOTE: NO INTERACTION WITH CONSOLE, NO LOGIC ONLY ONE LINE, CALL FROM APPRIATE CLASSES
     */
    public void createMealPlan(String name, ArrayList<Recipe> recipes) {
    User currentUser = this.user;

    if (currentUser == null) {
        System.out.println("No user is currently logged in. Cannot create meal plan.");
        return;
    }

    MealPlan newPlan = new MealPlan(name, recipes);
    currentUser.getMealPlans().add(newPlan);
    List<Ingredient> grocery = generateGroceryList(newPlan);
    System.out.println("Created meal plan '" + name + "'. Grocery list has been written.");
    }

    public List<MealPlan> getUserMealPlans(User user) {
        return null;
    }

    public List<Ingredient> generateGroceryList(MealPlan plan) {
        return UserList.getInstance().generateGroceryList(plan);
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        UserList.getInstance().save();
        RecipeList.getInstance().save();

       // this.user = null;
       // recipeSystemFACADE = null;
    }
}