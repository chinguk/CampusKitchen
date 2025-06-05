package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeSystemFACADE {
    private static final User newUser = null;
    private User user;
    private static RecipeSystemFACADE recipeSystemFACADE;

    private RecipeSystemFACADE() {}

    public static RecipeSystemFACADE getInstance() {
        if(recipeSystemFACADE == null) {
            recipeSystemFACADE = new RecipeSystemFACADE();
        }
        return recipeSystemFACADE;
    }
    public boolean createAccount(String firstName, String lastName, String email, String universityID, String username, String password) {
        return UserList.getInstance().addUser(firstName, lastName, email, universityID, username, password);
    }

    public void updateProfile(User user) {

    }

    public User login(String username, String password) {
        return UserList.getInstance().getUser(username);
    }

    public Recipe getRecipeByKeyWord(String word) {
        return null;

    }

    public ArrayList<Recipe> getAllRecipe() {
        return null;

    }

    public void submitRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, User author, RecipeStatus status) {

    }

    public void editRecipe(UUID id, String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, RecipeStatus status) {

    }

    public void approveRecipe(Recipe recipe) {

    }

    public void deleteRecipe(Recipe recipe) {

    }

    public void rateRecipe(Recipe recipe, Rating rating) {

    }

    public double getAverageRating(Recipe recipe) {
        return 0;

    }

    public void createMealPlan(String name, ArrayList<Recipe> recipes) {

    }

    public List<MealPlan> getUserMealPlans(User user) {
        return null;

    }

    public List<Ingredient> generateGroceryList(MealPlan mealPlan) {
        return null;
        
    }

    public void logout() {
        UserList.getInstance().save();
    }
}