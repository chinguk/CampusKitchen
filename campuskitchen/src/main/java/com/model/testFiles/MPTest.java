package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A simple “main” that:
 *   1) Loads two known recipes from Recipes.json
 *   2) Creates a MealPlan containing exactly those two recipes
 *   3) Saves the MealPlan to MealPlans.json
 *   4) Generates a grocery‐list .txt for that MealPlan
 *
 * After running this, inspect:
 *   • campuskitchen/src/main/json/MealPlans.json
 *   • campuskitchen/src/main/resources/grocerylist_<planID>.txt
 */
public class MPTest {

    public static void main(String[] args) {
        ArrayList<Recipe> loadedRecipes = DataLoader.getRecipes();
        RecipeList.getInstance(loadedRecipes); // seed the singleton

        if (RecipeList.getInstance().getRecipes().isEmpty()) {
            System.err.println("ERROR: No recipes loaded. Aborting test.");
            return;
        } else {
            System.out.println("Loaded " + RecipeList.getInstance().getRecipes().size() + " recipes from disk.");
        }

        UUID quinoaId    = UUID.fromString("4b1e2d53-5f4e-4b1a-9df4-a6aef40f6c7d");
        UUID saladId     = UUID.fromString("dd772f9e-0f18-4bd7-90a4-13ae71d9f531");

        Recipe quinoaRecipe = RecipeList.getInstance().getByID(quinoaId);
        Recipe saladRecipe  = RecipeList.getInstance().getByID(saladId);

        if (quinoaRecipe == null || saladRecipe == null) {
            System.err.println("ERROR: Could not find one or both of the test‐recipes in RecipeList.");
            return;
        }

        System.out.println("→ Found Recipe #1: " + quinoaRecipe.getName());
        System.out.println("→ Found Recipe #2: " + saladRecipe.getName());

        List<Recipe> chosen = Arrays.asList(quinoaRecipe, saladRecipe);

        MealPlan testPlan = new MealPlan(chosen);
        MealPlanList.getInstance(testPlan.getID() != null 
                                ? MealPlanList.getInstance(new ArrayList<>()).getMealPlans() 
                                : null); 
        
        MealPlanList.getInstance();  
        MealPlanList.getInstance().addMealPlan(testPlan);

        System.out.println("Created new MealPlan with ID: " + testPlan.getID());

        DataWriter.saveMealPlans();
        System.out.println("✓ MealPlans.json should now contain exactly one plan with two recipe‐IDs.");

        GroceryListGenerator.generateGroceryList(testPlan);
        System.out.println("✓ A grocery‐list_<planID>.txt file should now exist under src/main/resources/");

        System.out.println("=== TEST COMPLETE ===");
    }
}
