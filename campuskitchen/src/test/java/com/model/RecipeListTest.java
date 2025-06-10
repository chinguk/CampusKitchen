package com.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.model.Recipe;
import com.model.RecipeList;
import com.model.User;

public class RecipeListTest {
    private RecipeList list;

/**
 * Set up method to reset the singleton instances of RecipeList before each test.
 * Uses reflection to set the private static fields 'instance' and 'recipeList' to null.
 * Initializes the 'list' variable with a new RecipeList instance.
 */

    @Before
    public void setUp() {
        // Reset singleton instances via reflection
        try {
            java.lang.reflect.Field f1 = RecipeList.class.getDeclaredField("instance");
            f1.setAccessible(true);
            f1.set(null, null);
            java.lang.reflect.Field f2 = RecipeList.class.getDeclaredField("recipeList");
            f2.setAccessible(true);
            f2.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        list = RecipeList.getInstance();
    }

    /**
     * Test the addRecipe method to ensure that it correctly adds a recipe to the list of all recipes.
     * A recipe with all required fields is arranged and added to the RecipeList. The test verifies that
     * the method successfully adds the recipe by asserting that the recipe is contained in the list
     * of all recipes retrieved from the RecipeList.
     */
    @Test
    public void addRecipe() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        r.setId(UUID.randomUUID());
        r.setName("Cake");
        r.setDescription("Sweet and delicious");
        User author = new User(null, null, null, null, null, null, null, null);
        author.setUsername("baker");
        r.setAuthor(author);

        list.addRecipe(r);
        assertTrue("Recipe should be added when all info is present", list.getRecipes().contains(r));
    }

    /**
     * Test the addRecipe method to ensure that it correctly does not add a duplicate
     * recipe to the list of all recipes by name. Two recipes with the same name but
     * different IDs and descriptions are arranged and added to the RecipeList. The test
     * verifies that the method does not add the second recipe by asserting that the size
     * of the list of all recipes retrieved from the RecipeList is 1.
     */
    @Test
    public void duplicateRecipeByName() {
        Recipe r1 = new Recipe(null, null, 0, null, null, null, null, null);
        r1.setId(UUID.randomUUID());
        r1.setName("Pie");
        r1.setDescription("Apple pie");
        User u1 = new User(null, null, null, null, null, null, null, null); u1.setUsername("chef1"); r1.setAuthor(u1);
        list.addRecipe(r1);

        Recipe r2 = new Recipe(null, null, 0, null, null, null, u1, null);
        r2.setId(UUID.randomUUID());
        r2.setName("Pie");
        r2.setDescription("Cherry pie");
        User u2 = new User(null, null, null, null, null, null, null, null); u2.setUsername("chef2"); r2.setAuthor(u2);
        list.addRecipe(r2);

        assertEquals("Should not add duplicate by name", 1, list.getRecipes().size());
    }

    /**
     * Test the addRecipe method to ensure that it correctly does not add a duplicate
     * recipe to the list of all recipes by ID. Two recipes with the same ID but
     * different names and descriptions are arranged and added to the RecipeList. The
     * test verifies that the method does not add the second recipe by asserting that
     * the size of the list of all recipes retrieved from the RecipeList is 1.
     */
    @Test
    public void dubliplicateRecipeByID() {
        Recipe r1 = new Recipe(null, null, 0, null, null, null, null, null);
        UUID id = UUID.randomUUID();
        r1.setId(id);
        r1.setName("Cake");
        r1.setDescription("Vanilla cake");
        User u = new User(null, null, null, null, null, null, null, null); u.setUsername("chef"); r1.setAuthor(u);
        list.addRecipe(r1);

        Recipe r2 = new Recipe(null, null, 0, null, null, null, u, null);
        r2.setId(id);
        r2.setName("Cancake");
        r2.setDescription("Chocolate");
        User u2 = new User(null, null, null, null, null, null, null, null); u2.setUsername("chef2"); r2.setAuthor(u2);
        list.addRecipe(r2);

        assertEquals("Should not add duplicate by ID", 1, list.getRecipes().size());
    }

    /**
     * Test that a recipe with missing mandatory fields (ID, name, description, author)
     * is not added to the list of all recipes. The test verifies that attempting to add
     * such a recipe results in no change to the list, asserting that it remains empty.
     */

    @Test
    public void missingRecipeInfo() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        // missing id, name, description, author
        list.addRecipe(r);
        assertTrue("Should not add recipe with missing fields", list.getRecipes().isEmpty());
    }

    /**
     * Test that a recipe without an author is not added to the list of all recipes.
     * The test verifies that attempting to add such a recipe results in no change to
     * the list, asserting that it remains empty.
     */
    @Test
    public void nonUserCreatesRecipe() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        r.setId(UUID.randomUUID());
        r.setName("Bread");
        r.setDescription("Fresh");
        // author is null
        list.addRecipe(r);
        assertTrue("Should not add recipe without an author", list.getRecipes().isEmpty());
    }

/**
 * Test the getRecipe method to ensure that it correctly searches for recipes
 * containing the given keyword in either the name or the description.
 * Two recipes are arranged and added to the RecipeList. The test verifies that
 * the method returns the correct recipe(s) by asserting the size of the result
 * list and confirming the presence of the expected recipe.
 */

    @Test
    public void getRecipe() {
        Recipe r1 = new Recipe(null, null, 0, null, null, null, null, null);
        r1.setId(UUID.randomUUID());
        r1.setName("Brownie");
        r1.setDescription("Chocolate treat");
        User u = new User(null, null, null, null, null, null, null, null); u.setUsername("chef"); r1.setAuthor(u);
        list.addRecipe(r1);

        Recipe r2 = new Recipe(null, null, 0, null, null, null, u, null);
        r2.setId(UUID.randomUUID());
        r2.setName("Cookie");
        r2.setDescription("Sugar cookie");
        User u2 = new User(null, null, null, null, null, null, null, null); u2.setUsername("chef"); r2.setAuthor(u2);
        list.addRecipe(r2);

        ArrayList<Recipe> results = list.getRecipe("chocolate");
        assertEquals(1, results.size());
        assertTrue(results.contains(r1));

        results = list.getRecipe("cookie");
        assertEquals(1, results.size());
        assertTrue(results.contains(r2));
    }

    /**
     * Test that searching for a recipe with a non-existent keyword returns an empty list.
     * The test verifies that the getRecipe method correctly handles the case where no
     * recipes match the provided keyword by asserting that the result list is empty.
     */

    @Test
    public void notExistingRecipe() {
        ArrayList<Recipe> results = list.getRecipe("nonexistent");
        assertTrue("Search for nonexistent should return empty", results.isEmpty());
    }

    /**
     * Test that searching for a recipe with a misspelled keyword returns an empty list.
     * The test verifies that the getRecipe method correctly handles the case where the
     * keyword is misspelled by asserting that the result list is empty.
     */
    @Test
    public void missplelledRecipe() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        r.setId(UUID.randomUUID());
        r.setName("Strudel");
        r.setDescription("Apple strudel");
        User u = new User(null, null, null, null, null, null, null, null); u.setUsername("chef"); r.setAuthor(u);
        list.addRecipe(r);

        ArrayList<Recipe> results = list.getRecipe("sturdel");
        assertTrue("Misspelled keyword should not match", results.isEmpty());
    }

    /**
     * Test that searching for a recipe by author name returns an empty list when
     * author filtering is not implemented. The test verifies that the getRecipe
     * method correctly handles the case where the keyword is a username by
     * asserting that the result list is empty.
     */
    @Test
    public void noAuthoredRecipe() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        r.setId(UUID.randomUUID());
        r.setName("Tart");
        r.setDescription("Fruit tart");
        User u = new User(null, null, null, null, null, null, null, null); u.setUsername("baker"); r.setAuthor(u);
        list.addRecipe(r);

        ArrayList<Recipe> results = list.getRecipe("baker");
        assertTrue("Searching by author name should not match if author filtering not implemented", results.isEmpty());
    }

    /**
     * Tests the deleteRecipe method by adding a recipe to the list and then
     * deleting it by ID. The test verifies that the method successfully deletes
     * the recipe by asserting that the list of recipes is empty after deletion.
     */
    @Test
    public void deleteRecipe() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        UUID id = UUID.randomUUID();
        r.setId(id);
        r.setName("Muffin");
        r.setDescription("Blueberry muffin");
        User u = new User(null, null, null, null, null, null, null, null); u.setUsername("chef"); r.setAuthor(u);
        list.addRecipe(r);

        list.deleteRecipe(id.toString());
        assertTrue("Recipe should be deleted by ID", list.getRecipes().isEmpty());
    }

    /**
     * Retrieves a recipe by its UUID
     * @param id the UUID to search for
     * @return the matching Recipe or null if not found
     */
    @Test
    public void getByID() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        UUID id = UUID.randomUUID();
        r.setId(id);
        r.setName("Cupcake");
        r.setDescription("Vanilla cupcake");
        User u = new User(null, null, null, null, null, null, null, null); u.setUsername("chef"); r.setAuthor(u);
        list.addRecipe(r);

        Recipe found = list.getByID(id);
        assertEquals("Should return matching recipe", r, found);
        assertNull("Should return null for non-existing ID", list.getByID(UUID.randomUUID()));
    } 

}