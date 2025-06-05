package com.model;

import java.util.ArrayList;

public class MealPlan {
    private static final User userList = null;
    private String name;
    private ArrayList<String> recipes;
    private String id;


    public MealPlan(String name, ArrayList<Recipe> recipes, String id){
        this.name = name;
        this.recipes = (recipes != null) ? recipes : new ArrayList<>();
        this.id = id;

    public MealPlan(String name, ArrayList<String> recipes){
        this.name = name;
        this.recipes = recipes;
    }

    public MealPlan(String name2, String string) {
        //TODO Auto-generated constructor stub

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

    public static User getInstance() {
        if (userList == null) {
            userList = new User();
        }
        return userList;
    }
}
