package com.model;

import java.util.ArrayList;

public class RecipeList {
    private ArrayList<Recipe> recipes;
    private RecipeList recipeList;

    private RecipeList(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void getInstance(ArrayList<Recipe> recipes) {

    }

    public void addRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredients> ingredients, ArrayList<String> categories, User author, RecipeStatus status) {

    }

    public void getRecipe(String keyword) {
        
    }

    public void editRecipe(String id, String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredients> ingredients, ArrayList<String> categories, User author, RecipeStatus status) {
        
    }

    public void deleteRecipe(String id) {
        
    }
}
