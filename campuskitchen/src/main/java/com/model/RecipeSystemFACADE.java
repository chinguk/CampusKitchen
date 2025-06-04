package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeSystemFACADE {
    private User user;

    public User createAccount(String firstName, String lastName, String email, String universityID, String username, String password) {
        return user;
    }

    public void updateProfile(User user) {

    }


    public void logout() {
        
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        UserList stored = UserList.getInstance();
        User found = stored.getUser(username);

        if (found != null && found.getPassword().equals(password)) {
            this.user = found;
            return found;
        }
        return null;
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
}