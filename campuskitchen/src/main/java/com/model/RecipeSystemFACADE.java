package com.model;

import java.util.ArrayList;
import java.util.List;

public class RecipeSystemFACADE {
    private User user;

    public User createAccount(String firstName, String lastName, String email, String universityID, String username, String password) {

    }

    public void updateProfile(User user) {

    }

    public void deleteAccount(String UniversityID) {

    }

    public void logout() {

    }

    public User login(String username, String password) {

    }

    public Recipe getRecipeByKeyWord(String word) {

    }

    public ArrayList<Recipe> getAllRecipe() {

    }

    public void submitRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredients> ingredients, ArrayList<Category> categories, User author, RecipeStatus status) {

    }

    public void editRecipe(UUID id, String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, ArrayList<Category> categories, RecipeStatus status) {

    }

    public void approveRecipe(Recipe) {

    }

    public void deleteRecipe(Recipe) {

    }

    public void rateRecipe(Recipe, Rating rating) {

    }

    public double getAverageRating(Recipe) {

    }

    public void createMealPlan(String name, ArrayList<Recipe> recipes) {

    }

    public List<MealPlan> getUserMealPlans(User user) {

    }

    public List<Ingredient> generateGroceryList(MealPlan) {
        
    }
}
