package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeSystemFACADE {
    private static final User newUser = null;
    private User user;
    private static RecipeSystemFACADE recipeSystemFACADE;

    private RecipeSystemFACADE() {}

    public static RecipeSystemFACADE getInstance() {
        if(recipeSystemFACADE == null) {
            recipeSystemFACADE = new RecipeSystemFACADE();
        }
        return recipeSystemFACADE;
    }
    public boolean createAccount(String firstName, String lastName, String email, String universityID, String username, String password) {
        return UserList.getInstance().addUser(firstName, lastName, email, universityID, username, password);
    }

    public void updateProfile(User user) {

    }

    public User login(String username, String password) {
        return UserList.getInstance().getUser(username);
    }

    public Recipe getRecipeByKeyWord(String word) {
        return null;

    }

    public ArrayList<Recipe> getAllRecipe() {
        return null;

    }

    public void submitRecipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, User author, RecipeStatus status) {

    }

    public void editRecipe(UUID id, String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, ArrayList<Course> course, ArrayList<Culture> culture, ArrayList<Dietary> dietary, RecipeStatus status) {

    }

    public void approveRecipe(Recipe recipe) {

    }

    public void deleteRecipe(Recipe recipe) {

    }

    public void rateRecipe(Recipe recipe, Rating rating) {

    }

    public double getAverageRating(Recipe recipe) {
        return 0;

    }

    public void createMealPlan(String name, ArrayList<Recipe> recipes) {
    User currentUser = this.user;

    if (currentUser == null) {
        System.out.println("No user is currently logged in. Cannot create meal plan.");
        return;
    }

    MealPlan newPlan = new MealPlan(name, recipes);
    currentUser.getMealPlans().add(newPlan);
    List<Ingredient> grocery = generateGroceryList(newPlan);
    System.out.println("Created meal plan '" + name + "'. Grocery list has been written.");
    }

    public List<MealPlan> getUserMealPlans(User user) {
        return null;
    }

    public List<Ingredient> generateGroceryList(MealPlan mealPlan) {
        if (mealPlan == null) {
            return null;
        }
        List<Ingredient> groceryList = mealPlan.generateGroceryList();
        writeGroceryListToFile(mealPlan, groceryList);
        return groceryList;        
    }

    private void writeGroceryListToFile(MealPlan mealPlan, List<Ingredient> groceryList) {
        String fileName = "grocerylist_" + mealPlan.getID() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Grocery List for MealPlan \"" + mealPlan.getName() + "\" (ID=" + mealPlan.getID() + ")\n");
            for (Ingredient ing : groceryList) {
                writer.write(ing.getName() + ": " + ing.getAmount() + " " + ing.getUnit().name() + "\n");
            }
            writer.flush();
            System.out.println("Wrote grocery list to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        UserList.getInstance().save();
    }
}