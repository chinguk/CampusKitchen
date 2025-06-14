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
        //scenario3();
        //scenario4();
        // scenario5();
       // scenario6();
       scenario7();

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

    // create and save new recipe
    public void scenario4() {
        System.out.println("Scenario 4: Create and save a new recipe");

        // Log in as an existing user
        User author = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");
        if (author == null) {
            System.out.println("Login failed–cannot create recipe.");
            return;
        }
        System.out.println("Logged in as: " + author.getUsername());

        // Build some ingredients
        Ingredient flour = new Ingredient("Flour", 2.0, Unit.CUP);
        Ingredient sugar = new Ingredient("Sugar", 1.0, Unit.CUP);
        Ingredient butter = new Ingredient("Butter", 0.5, Unit.CUP);

        // Construct a new Recipe
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
            new ArrayList<>(List.of(Category.DESSERT)),
            author,
            RecipeStatus.APPROVED
        );

        // Add to in-memory list and persist
        RecipeList.getInstance().getRecipes().add(cookieRecipe);
        try {
            DataWriter.saveRecipes();
            System.out.println("Successfully added recipe and updated Recipes");
        } catch (Exception e) {
            System.err.println("Error saving Recipes");
            e.printStackTrace();
        }
    }

    // scenerio 5 add recipes to meal plan
    public void scenario5() {
        System.out.println("Scenario 5: Create a MealPlan via facade and add recipes");

        // Log in
        User user = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");
        if (user == null) {
            System.out.println("Login failed cannot create meal plan.");
            return;
        }
        System.out.println("Logged in as: " + user.getUsername());

        // Build example recipes
        Ingredient egg = new Ingredient("Egg", 2.0, Unit.PIECE);
        Ingredient milk = new Ingredient("Milk", 1.0, Unit.CUP);
        Recipe omelette = new Recipe(
            "Cheese Omelette",
            "cheesy eggs",
            8,
            new ArrayList<>(List.of("Beat eggs and milk", "Pour into pan", "Add cheese", "Fold and serve")),
            new ArrayList<>(List.of(egg, milk)),
            new ArrayList<>(List.of(Culture.AMERICAN)),
            new ArrayList<>(List.of(Dietary.VEGETARIAN)),
            new ArrayList<>(List.of(Course.BREAKFAST)),
            user,
            RecipeStatus.APPROVED
            );

        Ingredient tom2 = new Ingredient("Tomato", 0.5, Unit.PIECE);
        Ingredient let1 = new Ingredient("Lettuce", 1.0, Unit.PIECE);
        Recipe sandwich = new Recipe(
            "Sandwich",
            "Tomato Sandwich",
            15,
            new ArrayList<>(List.of("Layer tomato and lettuce in between two pieces of bread", "Serve")),
            new ArrayList<>(List.of(tom2, let1)),
            new ArrayList<>(List.of(Culture.AMERICAN)),
            new ArrayList<>(List.of(Dietary.VEGETARIAN)),
            new ArrayList<>(List.of(Course.LUNCH)),
            user,
            RecipeStatus.APPROVED);

        Ingredient pasta = new Ingredient("Pasta", 200.0, Unit.GRAM);
        Ingredient sauce = new Ingredient("Tomato Sauce", 150.0, Unit.GRAM);
        Recipe pastaDish = new Recipe(
        "Pasta Marinara",
        "Boil pasta, heat sauce, combine.",
        20,
        new ArrayList<>(),
        new ArrayList<>(List.of(pasta, sauce)),
        new ArrayList<>(List.of(Culture.AMERICAN)),
        new ArrayList<>(List.of(Dietary.VEGETARIAN)),
        new ArrayList<>(List.of(Course.LUNCH)),
        user,
        RecipeStatus.APPROVED
        );

        // Create meal plan with only the omelette
        ArrayList<Recipe> initialRecipes = new ArrayList<>();
        initialRecipes.add(omelette);
        initialRecipes.add(sandwich);
        initialRecipes.add(pastaDish);
        RecipeSystemFACADE.getInstance().createMealPlan("My Weekend Plan", initialRecipes);

        // find the plan by name
        List<MealPlan> plans = RecipeSystemFACADE.getInstance().getUserMealPlans();
        MealPlan myPlan = null;
        for (MealPlan p : plans) {
            if ("My Weekend Plan".equals(p.getName())) {
                myPlan = p;
                break;
            }
        }

        if (myPlan == null) {
            System.out.println("Failed to find the created meal plan.");
            return;
        }

        // Add second recipe
        myPlan.addRecipe(pastaDish);
        System.out.println("Added " + pastaDish.getName() + " to " + myPlan.getName() + ".");

        // Print out all meal plans and their grocery lists
        for (MealPlan plan : plans) {
            System.out.println("\nMealPlan: " + plan.getName() + " (ID=" + plan.getID() + ")");
            System.out.println("Recipes:");
            for (Recipe r : plan.getRecipes()) {
                System.out.println(r.getName());
            }
            List<Ingredient> grocery = RecipeSystemFACADE.getInstance().generateGroceryList(plan);
            System.out.println("Grocery list:");
            for (Ingredient ing : grocery) {
                System.out.print(ing.getName() + ing.getAmount() + ing.getUnit());
            }
        }
    }

    // scenerio where user logs in and looks at all recipes
    public void scenario6() {

        ArrayList<Recipe> all = RecipeSystemFACADE.getInstance().getAllRecipes();
        if (all.isEmpty()) {
            System.out.println("no recipes found");
            return;
        }
        for (Recipe r : all) {
            System.out.println(r.getName());
        }
    }

    public void scenario7() {
        System.out.println("Scenario 7: View user's meal plans and their recipes");

        // Log in
        User user = RecipeSystemFACADE.getInstance().login("TonyaHam", "2345");
        if (user == null) {
            System.out.println("Login failed. Cannot view meal plans.");
            return;
        }
        System.out.println("Logged in as: " + user.getUsername());

        // Get meal plans
        List<MealPlan> mealPlans = RecipeSystemFACADE.getInstance().getUserMealPlans();

        if (mealPlans.isEmpty()) {
            System.out.println("No meal plans found for this user.");
            return;
        }

        // Print meal plans and their recipes
        for (MealPlan plan : mealPlans) {
            System.out.println("\nMeal Plan: " + plan.getName() + " (ID: " + plan.getID() + ")");
            List<Recipe> recipes = plan.getRecipes();
            if (recipes.isEmpty()) {
                System.out.println("No recipes in this meal plan.");
            } else {
                System.out.println("Recipes:");
                for (Recipe r : recipes) {
                    System.out.println(r.getName() + " (" + r.getDuration() + " mins)");
                }
            }
        }
    }
    public static void main(String[] args) {
        (new SimplifiedUI()).run();
    }
}
