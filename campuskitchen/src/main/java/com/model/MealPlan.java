package com.model;

import java.util.ArrayList;

public class MealPlan {
    private String name;
    private ArrayList<Recipe> recipes;
    private ArrayList<MealPlan> mealPlanIDs;

    public MealPlan(String name, ArrayList<Recipe> recipes){
        this.name = name;
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe){

    }

    public void removeRecipe(Recipe recipe){

    }

    public ArrayList<Ingredients> generateGroceryList(){
        return null;

    }

    public void rename(String name){

    }
}
