package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.scene.chart.PieChart.Data;

/**
 * Facade for recipe system that handles account management, recipe operations,
 * meal plan creation, and grocery list generation. Implements singleton pattern
 */
public class RecipeSystemFACADE {
    private static final User NEW_USER = null;
    private User user;
    private static RecipeSystemFACADE recipeSystemFACADE;
    private RecipeStatus recipeStatus;
    private ArrayList<MealPlan> mealPlans = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();

    /**
     * Private constructor to enforce singleton pattern
     */
    private RecipeSystemFACADE() {

    }

    /**
     * Returns singleton instance of facade and creates it if necessary
     * @return single RecipeSystemFACADE instance
     */
    public static RecipeSystemFACADE getInstance() {
        if(recipeSystemFACADE == null) {
            recipeSystemFACADE = new RecipeSystemFACADE();
        }
        return recipeSystemFACADE;
    }

    /**
     * Create new user account in the system
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param universityID user's ID
     * @param username user's username
     * @param password user's password
     * @return True if creates account, false otherwise
     */
    public boolean createAccount(String firstName, String lastName, String email, String universityID, String username, String password) {
        return UserList.getInstance().addUser(firstName, lastName, email, universityID, username, password);
    }

    /**
     * Updates profile information of currently logged in user
     * @param user Updated User
     */
    public void updateProfile(User updatedUser) {
        if (this.user != null && updatedUser != null) {
            this.user.updateProfile(updatedUser);
        }
    }

    /**
     * Logs user into system
     * @param username Username to authenticate
     * @param password Password to authenticate
     * @return User if login succeeds, null otherwise
     */
    public User login(String username, String password) {
        User foundUser = UserList.getInstance().getUser(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            this.user = foundUser;
            return foundUser;
        }
        return null;
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        UserList.getInstance().save();
        RecipeList.getInstance().save();
        this.user = null;
        recipeSystemFACADE = null;
        }

    /**
     * Retrieves recipe matching keyword
     * @param word Search word
     * @return matching recipe or null if not found
     */
    public Recipe getRecipeByKeyWord(String word) {
        ArrayList<Recipe> results = RecipeList.getInstance().getRecipe(word);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Returns all recipes in the system
     * @return All recipes
     */
    public ArrayList<Recipe> getAllRecipes() {
        return RecipeList.getInstance().getRecipes();
    }
    /**
     * Submits a new recipe to the system.
     * @param name Name of the recipe
     * @param description Description of the recipe
     * @param duration Preparation time in minutes
     * @param steps List of steps for preparation
     * @param ingredients List of ingredients
     * @param course Course categories for the recipe
     * @param culture Cultural tags for the recipe
     * @param dietary Dietary restrictions
     * @param author Author of the recipe
     * @param status Approval status of the recipe
     */
    public void submitRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients,
                                ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, User author,
                                RecipeStatus status) {
        RecipeList.getInstance().addRecipe(name, description, duration, steps, ingredients, culture, dietary, course, author, status);
    }
    /**
     * Approves a recipe and updates its status.
     * @param recipe Recipe to approve
     * @return The approved status
     */
    @SuppressWarnings("static-access")
    public RecipeStatus approveRecipe(Recipe recipe) {
        return recipeStatus.APPROVED;
    }

    /**
     * Edits an existing recipe by updating its fields.
     * @param id ID of the recipe to edit
     * @param name Updated name
     * @param description Updated description
     * @param duration Updated duration
     * @param steps Updated preparation steps
     * @param ingredients Updated ingredients list
     * @param course Updated course categories
     * @param culture Updated cultural tags
     * @param dietary Updated dietary restrictions
     * @param status Updated approval status
     */       
    public void editRecipe(UUID id, String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, 
                            ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, RecipeStatus status) {
        Recipe recipe = RecipeList.getInstance().getByID(id);
        if (recipe != null) {
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setDuration(duration);
            recipe.setSteps(steps);
            recipe.setIngredients(ingredients);
            recipe.setCourse(course);
            recipe.setCulture(culture);
            recipe.setDietary(dietary);
            recipe.setStatus(status);
        }
    }

    /**
     * Deletes a recipe from the system.
     * @param recipe Recipe to delete
     */
    public void deleteRecipe(Recipe recipe) {
        if (recipe != null) {
            RecipeList.getInstance().deleteRecipe(recipe.getId().toString());
        }
    }
    /**
     * Adds a rating to a given recipe.
     * @param recipe Recipe to rate
     * @param rating Rating to be added
     */
    public void rateRecipe(Recipe recipe, Rating rating) {
        if (recipe != null && rating != null) {
            recipe.getRatings().add(rating);
        }
    }

    // can just call from recipes
    public double getAverageRating(Recipe recipe) {
        return (recipe != null) ? recipe.getAverageRating() : 0.0;
    }

    
/**
 * Creates a new meal plan for the currently logged-in user.
 * 
 * @param name The name of the meal plan to be created.
 * @param recipes A list of recipes to be included in the meal plan.
 * 
 * If no user is logged in, the method will print an error message and not create a meal plan.
 */

    public void createMealPlan(String name, ArrayList<Recipe> recipes) {   
        if (user == null) {
            System.out.println("No user is currently logged in. Cannot create meal plan.");
            return;
        }
        user.createMealPlan(name, recipes);
    }
     
    
    /**
     * Retrieves the meal plans of the currently logged-in user.
     * 
     * @return A list of MealPlan objects for the logged-in user, or an empty list if no user is logged in.
     */

    public List<MealPlan> getUserMealPlans() {
        return (user != null) ? user.getMealPlans() : new ArrayList<>();
    }

    
    /**
     * Generates a grocery list for a given meal plan.
     * 
     * @param plan The meal plan for which to generate the grocery list.
     * @return A list of ingredients needed for the meal plan.
     */

    public List<Ingredient> generateGroceryList(MealPlan plan) {
        return DataWriter.getInstance().generateGroceryList(plan);
    }

    public void addMealPlan(MealPlan plan) {
        mealPlans.add(plan);
    }

    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

}
    
