package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MealPlan {
    private ArrayList<Recipe> recipes;
    private  String id;
    private String name;

    public MealPlan(String name, List<Recipe> recipes){
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public MealPlan (String name, List<Recipe> recipes, String existingID){
        this.id = existingID;
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "MealPlan{id='"  + id + "', recipes=" + recipes + "}";
    }
}
