package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        this.name = name;
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

    public void addRecipe(Recipe recipe){
        if (recipe != null) {
            recipes.add(recipe);
        }
    }

    public void removeRecipe(Recipe recipe){
        if (recipe != null) {
            recipes.remove(recipe);
        }
    }

    public ArrayList<Ingredient> generateGroceryList(){
        Map<String, Ingredient> aggregated = new HashMap<>();

        for (Recipe r : recipes) {
            if (r.getIngredients() == null) continue;
            for (Ingredient ing : r.getIngredients()) {
                String key = ing.getName().toLowerCase() + "|" + ing.getUnit().name();
                if (aggregated.containsKey(key)) {
                    Ingredient existing = aggregated.get(key);
                    existing.setAmount(existing.getAmount() + ing.getAmount());
                } else {
                    Ingredient copy = new Ingredient(ing.getName(), ing.getAmount(), ing.getUnit());
                    aggregated.put(key, copy);
                }
            }
        }        
        return new ArrayList<>(aggregated.values());
    }
    
    @Override
    public String toString() {
        return "MealPlan{id='"  + id + "', recipes=" + recipes + "}";
    }
}
