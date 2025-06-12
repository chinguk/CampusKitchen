package com.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.*;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

public class DataWriterTest {
    private JSONParser parser = new JSONParser();

    /**
     * Before each test, clear the in-memory collections and remove any existing
     * JSON files.
     * This ensures that each test starts from a clean slate.
     */
    @Before
    public void setUp() {
        // Clear in-memory collections
        UserList.getInstance().getUsers().clear();
        RecipeList.getInstance().getRecipes().clear();
        // Remove any existing JSON files
        new File("campuskitchen/src/main/json/Users.json").delete();
        new File("campuskitchen/src/main/json/Recipes.json").delete();
    }

    /**
     * Test the saveUsers method to ensure it correctly writes user data to a JSON
     * file.
     * The test sets up a User object with specific details, adds it to the
     * UserList,
     * and invokes the saveUsers method. It then verifies the method's success by
     * checking the existence of "Users.json", ensuring it contains the expected
     * number of users, and confirming the correct user details are stored.
     */

    @Test
    public void saveUsers() throws Exception {
        // Arrange: one user with no diets or meal plans
        User u = new User(null, null, null, null, null, null, null, null);
        u.setFirstName("Alice");
        u.setLastName("Wonder");
        u.setEmail("alice@example.com");
        u.setUniversityID("U01");
        u.setUsername("alice");
        u.setPassword("secret");
        u.setDietaryRestrictions(Dietary.emptyList());
        u.setMealPlans(MealPlan.emptyList());
        UserList.getInstance().getUsers().add(u);

        // Act
        boolean result = DataWriter.saveUsers();

        // Assert
        assertTrue("saveUsers should succeed", result);
        File file = new File("campuskitchen/src/main/json/Users.json");
        assertTrue("Users.json should exist", file.exists());
        JSONArray arr = (JSONArray) parser.parse(new FileReader(file));
        assertEquals("One user should be written", 1, arr.size());
        JSONObject jo = (JSONObject) arr.get(0);
        assertEquals("alice", jo.get("username"));
        assertTrue(((JSONArray) jo.get("dietaryRestrictions")).isEmpty());
        assertTrue(((JSONArray) jo.get("mealPlans")).isEmpty());
    }

    /**
     * Test the private getUserJSON method via reflection, which takes a User and
     * returns a JSON object representation of it.
     * The JSON object is verified to contain the expected fields and values from
     * the User object.
     */
    @Test
    public void getUserJSON() throws Exception {
        // Arrange: user for direct JSON conversion
        User u = new User(null, null, null, null, null, null, null, null);
        u.setFirstName("Bob");
        u.setLastName("Builder");
        u.setEmail("bob@example.com");
        u.setUniversityID("U02");
        u.setUsername("bob");
        u.setPassword("build");
        u.setDietaryRestrictions(Dietary.emptyList());
        u.setMealPlans(MealPlan.emptyList());

        // Act: invoke private getUserJSON via reflection
        Method m = DataWriter.class.getDeclaredMethod("getUserJSON", User.class);
        m.setAccessible(true);
        JSONObject jo = (JSONObject) m.invoke(null, u);

        // Assert
        assertEquals("Bob", jo.get("firstName"));
        assertEquals("Builder", jo.get("lastName"));
        assertEquals("bob", jo.get("username"));
        assertTrue(((JSONArray) jo.get("dietaryRestrictions")).isEmpty());
        assertTrue(((JSONArray) jo.get("mealPlans")).isEmpty());
    }

    /**
     * Test the saveRecipes method to ensure that it correctly saves a recipe to a
     * JSON file.
     * A single recipe is arranged and added to the RecipeList. The test verifies
     * that the
     * method successfully writes the recipe to "Recipes.json" by asserting the
     * file's
     * existence and checking that the JSON content matches the expected values of
     * the
     * arranged recipe, including the recipe's ID, name, user, and description.
     */
    @Test
    public void saveRecipes() throws Exception {
        // Arrange: one recipe
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        UUID id = UUID.randomUUID();
        r.setId(id);
        r.setName("TestTreat");
        User author = new User(null, null, null, null, null, null, null, null);
        author.setUsername("chef");
        r.setAuthor(author);
        r.setDescription("Tasty");
        r.setDuration(15);
        r.setStatus(RecipeStatus.APPROVED);
        r.setIngredients(Ingredient.emptyList());
        r.setRatings(Rating.emptyList());
        RecipeList.getInstance().getRecipes().add(r);

        // Act
        boolean result = DataWriter.saveRecipes();

        // Assert
        assertTrue("saveRecipes should succeed", result);
        File file = new File("campuskitchen/src/main/json/Recipes.json");
        assertTrue("Recipes.json should exist", file.exists());
        JSONArray arr = (JSONArray) parser.parse(new FileReader(file));
        assertEquals("One recipe should be written", 1, arr.size());
        JSONObject jo = (JSONObject) arr.get(0);
        assertEquals(id.toString(), jo.get("id"));
        assertEquals("TestTreat", jo.get("name"));
        assertEquals("chef", jo.get("user"));
        assertEquals("Tasty", jo.get("description"));
    }

    /**
     * Test the private getRecipeJSON method via reflection, which
     * takes a Recipe and returns a JSON object representation of it.
     * The JSON object is verified to contain the expected fields and
     * values from the Recipe object.
     */
    @Test
    public void getRecipeJSON() throws Exception {
        // Arrange: recipe for direct JSON conversion
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        UUID id = UUID.randomUUID();
        r.setId(id);
        r.setName("MyRecipe");
        User u = new User(null, null, null, null, null, null, null, null);
        u.setUsername("author");
        r.setAuthor(u);
        r.setDescription("Desc");
        r.setDuration(20);
        r.setStatus(RecipeStatus.REJECTED);
        r.setIngredients(Ingredient.emptyList());
        r.setRatings(Rating.emptyList());

        // Act: invoke private getRecipeJSON via reflection
        Method m = DataWriter.class.getDeclaredMethod("getRecipeJSON", Recipe.class);
        m.setAccessible(true);
        JSONObject jo = (JSONObject) m.invoke(null, r);

        // Assert
        assertEquals(id.toString(), jo.get("id"));
        assertEquals("MyRecipe", jo.get("name"));
        assertEquals("author", jo.get("user"));
        assertEquals("Desc", jo.get("description"));
        assertEquals(20L, jo.get("duration"));
        assertEquals("REJECTED", jo.get("status"));
        assertEquals("REJECTED", jo.get("recipeStatus"));
        assertTrue(((JSONArray) jo.get("ingredients")).isEmpty());
        assertTrue(((JSONArray) jo.get("categories")).isEmpty());
        assertTrue(((JSONArray) jo.get("ratings")).isEmpty());
    }

    @Test
    void testGenerateGroceryListPopulatesStaticAndReturnsList() {
        // Create test ingredients
        Ingredient ing1 = new Ingredient("Apple", 2, Unit.PIECE);
        Ingredient ing2 = new Ingredient("Flour", 1.5, Unit.CUP);

        // Create a test recipe with these ingredients
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Step1");

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ing1);
        ingredients.add(ing2);

        ArrayList<Category> categories = new ArrayList<>();

        Recipe recipe = new Recipe(
            "Test Recipe",                // name
            "A simple test recipe",      // description
            5,                             // duration
            steps,                         // steps
            ingredients,                   // ingredients
            categories,                    // categories
            null,                          // author
            RecipeStatus.APPROVED          // status
        );

        // Set up a meal plan containing this recipe
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        MealPlan mealPlan = new MealPlan("Test Plan", recipes, "plan-001");

        // Generate the grocery list
        DataWriter writer = new DataWriter();
        List<Ingredient> result = writer.generateGroceryList(mealPlan);

        // Verify the returned list is not null and has the expected ingredients
        assertNotNull(result, "Resulting grocery list should not be null");
        assertEquals(2, result.size(), "Grocery list should contain 2 ingredients");
        assertTrue(result.contains(ing1), "Grocery list should contain Apple");
        assertTrue(result.contains(ing2), "Grocery list should contain Flour");

        // Verify the static User.groceryList was populated
        assertSame(User.groceryList, result, "Should return the static User.groceryList");
    }
}
