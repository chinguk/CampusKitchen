package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents user's meal plan consisting of multiple recipes
 * Provides grocery list of ingredients in recipes
 */
public class MealPlan {
    private ArrayList<Recipe> recipes;
    private static MealPlan instance = null;
    private String id;
    private String name;

    /**
     * Constructs new MealPlan
     * @param name Name of meal plan
     * @param recipes initial list of recipes
     */
    public MealPlan(String name, List<Recipe> recipes){
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    /**
     * Constructs new MealPlan with fixed ID
     * @param name Name of meal plan
     * @param recipes Initial list of recipes
     * @param existingID Existing ID to reuse
     */
    public MealPlan(String name, List<Recipe> recipes, String existingID){
        this.name = name;
        this.id = existingID;
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    /**
     * @return ID of meal plan
     */
    public String getID() {
        return id;
    }

    /**
     * @return name of meal plan
     */
    public String getName() {
        return name;
    }

    /**
     * @return list of recipes in plan
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * @param name Set new name for meal plan
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param recipes new list of recipes
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = (recipes != null) ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    /**
     * Adds recipe to plan
     * @param recipe Recipe to add
     */
    public void addRecipe(Recipe recipe){
        if (recipe != null) {
            recipes.add(recipe);
        }
    }

    /**
     * Removes recipe from plan
     * @param recipe Recipe to be removed
     */
    public void removeRecipe(Recipe recipe){
        if (recipe != null) {
            recipes.remove(recipe);
        }
    }

    /**
     * Generates grocery list by adding ingredients from recipes in plan
     * @return list of ingredients
     */
    public ArrayList<Ingredient> generateGroceryList(){
        Map<String, Ingredient> aggregated = new HashMap<>();

        for (Recipe r : recipes) {
            if (r.getIngredients() == null) continue;
            for (Ingredient ing : r.getIngredients()) {
                String key = ing.getName().toLowerCase() + " " + ing.getUnit().name();
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
    
    /**
     * Returns String representation of meal plan
     */
    @Override
    public String toString() {
        return "MealPlan{id='"  + id + "', recipes=" + recipes + "}";
    }

    public static ArrayList<MealPlan> emptyList() {
        return new ArrayList<>();
    }
}