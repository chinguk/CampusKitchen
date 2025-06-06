package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MealPlan {
    private ArrayList<Recipe> recipes;
    private  String id;

    public MealPlan(List<Recipe> recipes){
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

    public MealPlan (List<Recipe> recipes, String existingID){
        this.id = existingID;
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    public String getID() {
        return id;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    public List<Recipe> getRecipe(){
        return recipes;

    }

    public void addRecipe(Recipe recipe){

    }

    public void removeRecipe(Recipe recipe){

    }

    public ArrayList<Ingredient> generateGroceryList(){
        return null;

    }

    public static User getInstance() {

    }

    @Override
    public String toString() {
        return "MealPlan{id='"  + id + "', recipes=" + recipes + "}";
    }
}
