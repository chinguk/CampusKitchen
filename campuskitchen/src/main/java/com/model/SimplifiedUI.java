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
        //scenario2();
        scenario3();
        //scenario4();
    }

    /**
     * Login scenerio
     */
    public void scenario1() {
        User user = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");

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

        boolean adding = RecipeSystemFACADE.getInstance().createAccount("Tonya", "Hamilton", "TonHamilton@gmail.com", "5000", "TonyaHam", "2345");

        if(!adding) {
            System.out.println("Couldn't successfully create account");
            return;
        }

        System.out.println("Successfully created account");

        try {
            DataWriter.saveUsers();
            System.out.println("Users updated with new account.");
        } catch (Exception e) {
            System.err.println("Error saving Users.json:");
            e.printStackTrace();
        }

        RecipeSystemFACADE.getInstance().logout();

        User user = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");

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

        User loggedIn = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");
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

    public void scenario4() {
        System.out.println("Scenario 4: Create and save a new recipe");

        // 1) Log in as an existing user (must already be in Users.json)
        User author = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");
        if (author == null) {
            System.out.println("Login failed–cannot create recipe.");
            return;
        }
        System.out.println("Logged in as: " + author.getUsername());

        // 2) Build some ingredients
        Ingredient flour = new Ingredient("Flour", 2.0, Unit.CUP);
        Ingredient sugar = new Ingredient("Sugar", 1.0, Unit.CUP);
        Ingredient butter = new Ingredient("Butter", 0.5, Unit.CUP);

        // 3) Construct a new Recipe
        Recipe cookieRecipe = new Recipe(
            "Simple Sugar Cookies",
            "Mix ingredients, roll into balls, and bake at 350°F for 12 minutes.",
            15,
            new ArrayList<>(List.of(
                "Preheat oven to 350°F.",
                "Cream together ingredients.",
                "Shape dough and bake."
            )),
            new ArrayList<>(List.of(flour, sugar, butter)),
            new ArrayList<>(List.of("DESSERT","BAKED")),
            author,
            RecipeStatus.APPROVED
        );

        // 4) Add to in-memory list and persist
        RecipeList.getInstance().getRecipes().add(cookieRecipe);
        try {
            DataWriter.saveRecipes();
            System.out.println("Successfully added recipe and updated Recipes");
        } catch (Exception e) {
            System.err.println("Error saving Recipes");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        (new SimplifiedUI()).run();
    }
}
