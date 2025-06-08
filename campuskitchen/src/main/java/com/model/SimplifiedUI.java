package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * UI used to demonstrate scenerios
 */
public class SimplifiedUI {
    public SimplifiedUI() {}

    public void run(){
        //scenario1();
        scenario3();
    }

    /**
     * Login scenerio
     */
    public void scenario1() {
        User user = RecipeSystemFACADE.getInstance().login("emill23", "321");

        if(user == null) {
            System.out.println("We didn't successfully login");
            return;
        }

        System.out.println("Successfully logged in");
        System.out.println(user);
    }

    /**
     * Account creation scenerio
     */
    public void scenario2() {
        System.out.println("Scenario 2");

        boolean adding = RecipeSystemFACADE.getInstance().createAccount("Amy", "Smith", "asmith@asmith.com", "1234", "asmith", "2309553344");

        if(!adding) {
            System.out.println("Couldn't successfully create account");
            return;
        }

        System.out.println("Successfully created account");
        RecipeSystemFACADE.getInstance().logout();

        User user = RecipeSystemFACADE.getInstance().login("asmith", "2309553344");

        if(user == null) {
            System.out.println("We didn't successfully login");
            return;
        }

    }

    /**
     * Login and generate grocerylist scenerio
     */
    public void scenario3() {
        System.out.println("Scenario 3");

        User loggedIn = RecipeSystemFACADE.getInstance().login("emill23", "321");
        if (loggedIn == null) {
            System.out.println("Login failed");
            return;
        }
        System.out.println("Logged in as: " + loggedIn.getUsername());

        // create two simple fake recipes with ingredients:
        Ingredient tom1 = new Ingredient("Tomato", 1.0, Unit.PIECE);
        Ingredient on1  = new Ingredient("Onion", 2.0, Unit.PIECE);
        Recipe salad = new Recipe(
            "Tomato Salad",
            "Chop tomatoes and onions, mix.",
            10,
            new ArrayList<>(),
            new ArrayList<>(List.of(tom1, on1)),
            new ArrayList<>(),
            loggedIn,
            RecipeStatus.APPROVED
        );

        Ingredient tom2 = new Ingredient("Tomato", 0.5, Unit.PIECE);
        Ingredient let1 = new Ingredient("Lettuce", 1.0, Unit.PIECE);
        Recipe sandwich = new Recipe(
            "Sandwich",
            "Layer tomato and lettuce between bread.",
            15,
            new ArrayList<>(),
            new ArrayList<>(List.of(tom2, let1)),
            new ArrayList<>(),
            loggedIn,
            RecipeStatus.APPROVED
        );

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(salad);
        recipes.add(sandwich);

        MealPlan plan = new MealPlan("Weekday lunches", recipes);
        loggedIn.getMealPlans().add(plan);

        List<Ingredient> grocery = RecipeSystemFACADE.getInstance().generateGroceryList(plan);

        System.out.println("Generated grocery list for MealPlan ID = " + plan.getID());
        System.out.println("Items:");
        for (Ingredient ing : grocery) {
            System.out.println("  • " + ing.getName() + ": " + ing.getAmount() + " " + ing.getUnit());
        }
        System.out.println("Look for file named “grocerylist_" + plan.getID() + ".txt");
    }
    public static void main(String[] args) {
        (new SimplifiedUI()).run();
    }
}
