package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Singleton manager for recipes in the system
 */
public class RecipeList {
    private static RecipeList instance = null;
    private ArrayList<Recipe> recipes;

    private RecipeList() {
        this.recipes = DataLoader.getRecipes();
    }
    

    /**
     * Returns the single instance of the RecipeList class, which contains
     * a list of all the recipes in the system. If the instance does not
     * exist yet, it is created.
     */
    public static RecipeList getInstance() {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }
    
    /**
     * Adds recipe
     * @param name
     * @param description
     * @param duration
     * @param steps
     * @param ingredient
     * @param culture
     * @param dietary
     * @param course
     * @param author
     * @param status
     * @return
     */
    public boolean addRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredient,
                    ArrayList<Culture> culture, ArrayList<Dietary> dietary, ArrayList<Course> course, User author, RecipeStatus status) {
        Recipe newRecipe = new Recipe(name, description, duration, steps, ingredient, culture, dietary, course, author,status);
        recipes.add(newRecipe);
        return true;
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

    public ArrayList<Recipe> getByIDs(ArrayList<UUID> ids) {
        ArrayList<Recipe> matchedRecipes = new ArrayList<>();
        for (UUID id : ids) {
            for (Recipe recipe : recipes) {
                if (recipe.getId().equals(id)) {
                    matchedRecipes.add(recipe);
                    break; // Stop inner loop once match is found
                }
            }
        }
        return matchedRecipes;
    }
    /**
    * Finds a recipe in the given list by matching its UUID.
    * 
    * @param recipes list of all recipes
    * @param id the UUID to search for
    * @return the matching Recipe or null if not found
    */
    public UUID findIdByRecipe(ArrayList<Recipe> recipes, UUID id) {
        for (Recipe recipe : recipes) {
            return recipe.getId();
        }
        return null;
    }

    public ArrayList<UUID> getRecipeIds() {
        ArrayList<UUID> idList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            idList.add(recipe.getId());
        }
        return idList;
    }

    public boolean save() {
        return DataWriter.saveRecipes();
    }    
}
