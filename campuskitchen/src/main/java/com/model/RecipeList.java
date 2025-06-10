package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton manager for recipes in the system
 */
public class RecipeList {
    private static RecipeList instance = null;
    private ArrayList<Recipe> recipes;
    private static RecipeList recipeList;

    private RecipeList(ArrayList<Recipe> recipes) {
        this.recipes = new ArrayList<>();
    }

    /**
     * Returns the single instance of the RecipeList class, which contains
     * a list of all the recipes in the system. If the instance does not
     * exist yet, it is created.
     */
    public static RecipeList getInstance() {
        if (recipeList == null) {
            recipeList = new RecipeList(new ArrayList<>());
        }
        return recipeList;
    }

    /**
     * Adds a recipe to the list of all recipes in the system.
     * If the recipe is null, nothing is done.
     */
    public void addRecipe(Recipe recipe) {
        if (recipe != null) {
            recipes.add(recipe);
        }
    }

    /**
     * Searches for recipes containing the given keyword in either the name or the description.
     * Case is ignored. If the keyword is null or empty, an empty list is returned.
     */
    public ArrayList<Recipe> getRecipe(String keyword) {
        ArrayList<Recipe> matches = new ArrayList<>();
        if (keyword == null || keyword.isEmpty()) {
            return matches;
        }
        String lowerKey = keyword.toLowerCase();
        for (Recipe recipe : recipes) {
            if ((recipe.getName() != null && recipe.getName().toLowerCase().contains(lowerKey)) 
            || (recipe.getDescription() != null && recipe.getDescription().toLowerCase().contains(lowerKey))) {
                matches.add(recipe);
            }
        }
        return matches;
    }

    /**
     * Retreives list of recipes
     * @return List of all Recipes
     */
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Deletes recipe from system
     * @param id UUID of recipe to remove
     */
    public void deleteRecipe(String id) {
        if (id != null) {
            recipes.removeIf(recipe -> recipe.getId().toString().equals(id));
        }
    }

    /**
     * Retrieves recipe by UUID
     * @param id UUID to search for
     * @return Matching Recipe
     */
    public Recipe getByID(UUID id) {
        for (Recipe recipe : recipes) {
            if (recipe.getId().equals(id)) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Returns singleton instance of RecipeList 
     * @param loadedRecipes initial list of recipes to seed
     * @return RecipeList singleton
     */
    public static RecipeList getInstance(ArrayList<Recipe> loadedRecipes) {
        if (instance == null) {
            instance = new RecipeList(loadedRecipes);
        }
        return instance;
    }
}
