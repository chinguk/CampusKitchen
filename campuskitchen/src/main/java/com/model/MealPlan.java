package com.model;

import java.util.ArrayList;

public class MealPlan {
    private String name;
    private ArrayList<Recipe> recipes;
    private String id;

    public MealPlan(String name, ArrayList<Recipe> recipes){
        this.name = name;
        this.recipes = (recipes != null) ? recipes : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.name = newName.trim();
        }
    }

    public String getID() {
        return this.id;
    }

    public String getRecipe(){
        return this.id;

    }

    public void addRecipe(Recipe recipe){

    }

    public void removeRecipe(Recipe recipe){

    }

    public ArrayList<Ingredient> generateGroceryList(){
        return null;

    }
}
