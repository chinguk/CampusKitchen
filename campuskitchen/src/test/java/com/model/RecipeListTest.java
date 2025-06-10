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

    @Test
    public void missingRecipeInfo() {
        Recipe r = new Recipe(null, null, 0, null, null, null, null, null);
        // missing id, name, description, author
        list.addRecipe(r);
        assertTrue("Should not add recipe with missing fields", list.getRecipes().isEmpty());
    }

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

    @Test
    public void notExistingRecipe() {
        ArrayList<Recipe> results = list.getRecipe("nonexistent");
        assertTrue("Search for nonexistent should return empty", results.isEmpty());
    }

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