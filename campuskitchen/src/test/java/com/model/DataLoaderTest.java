package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;


public class DataLoaderTest {

    private UserList users = UserList.getInstance();

    /**
     * Setup initial user list with one user before each test.
     */
    @Before
    public void setup() throws ParseException, org.json.simple.parser.ParseException  {
        users.getUsers().clear();
        users.getUsers().add(new User("Emily", "Miller", "emill@gmail.com", "F193742", "emill", "321", new ArrayList<>(), new ArrayList<>()));
        DataWriter.saveUsers(); 
    }

    /**
     * Test that the loaded users list size matches expected count.
     */
    @Test
    public void testGetUsersSize() {
        ArrayList<User> loadedUsers = DataLoader.getUsers();
        assertEquals(1, loadedUsers.size());
    }

    /**
     * Test that the first user's first name is correctly loaded.
     */
    @Test
    public void testGetUsersFirstName() {
        ArrayList<User> loadedUsers = DataLoader.getUsers();
        assertEquals("Emily", loadedUsers.get(0).getFirstName());
    }

    /**
     * Test that the first user's username is correctly loaded.
     */
    @Test
    public void testGetUsersUsername() {
        ArrayList<User> loadedUsers = DataLoader.getUsers();
        assertEquals("emill", loadedUsers.get(0).getUsername());
    }

    /**
     * Test that the users list is empty after clearing and saving.
     */
    @Test
    public void testGetUsersEmpty() throws ParseException, org.json.simple.parser.ParseException {
        users.getUsers().clear();
        DataWriter.saveUsers();
        ArrayList<User> loadedUsers = DataLoader.getUsers();
        assertTrue(loadedUsers.isEmpty());
    }

    /**
     * Test that parsing dietary restrictions returns empty list if none present.
     */
    @Test
    public void testParseDietaryRestrictionsEmpty() {
        JSONObject fakeUser = new JSONObject();
        ArrayList<Dietary> restrictions = DataLoader.parseDietaryRestrictions(fakeUser);
        assertTrue(restrictions.isEmpty());
    }

    /**
     * Test that parsing meal plans returns empty list if none present.
     */
    @Test
    public void testParseMealPlansEmpty() {
        JSONObject fakeUser = new JSONObject(); 
        ArrayList<MealPlan> plans = DataLoader.parseMealPlans(fakeUser);
        assertTrue(plans.isEmpty());
    }

    /**
     * Test that the list of recipes is never null.
     */
    @Test
    public void testGetRecipesNotNull() {
        ArrayList<Recipe> recipes = DataLoader.getRecipes();
        assertNotNull(recipes);
    }

    /**
     * Test that parsing recipe steps returns empty list if none present.
     */
    @Test
    public void testParseStepsEmpty() {
        JSONObject recipe = new JSONObject(); 
        ArrayList<String> steps = DataLoader.parseSteps(recipe);
        assertTrue(steps.isEmpty());
    }

    /**
     * Test that parsing ingredients returns empty list if none present.
     */
    @Test
    public void testParseIngredientsEmpty() {
        JSONObject recipe = new JSONObject(); 
        ArrayList<Ingredient> ingredients = DataLoader.parseIngredients(recipe);
        assertTrue(ingredients.isEmpty());
    }

    /**
     * Test that parsing categories returns empty list if none present.
     */
    @Test
    public void testParseCategoriesEmpty() {
        JSONObject recipe = new JSONObject(); 
        ArrayList<Category> categories = DataLoader.parseCategories(recipe);
        assertTrue(categories.isEmpty());
    }

    /**
     * Test that searching for a recipe by a non-existent ID returns null.
     */
    @Test
    public void testFindRecipeByIdReturnsNullForNonexistent() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Recipe result = DataLoader.findRecipeById(recipes, UUID.randomUUID());
        assertNull(result);
    }

    /**
     * Test that adding a new user increases the users list size by one.
     */
    @Test
    public void testAddUserIncreasesSize() throws ParseException, org.json.simple.parser.ParseException {
        int initialSize = users.getUsers().size();
        users.addUser("John", "Snow", "john@gmail.com", "G123456", "jsnow", "234");
        DataWriter.saveUsers();
        ArrayList<User> updatedUsers = DataLoader.getUsers();
        assertEquals(initialSize + 1, updatedUsers.size());
        assertEquals("jsnow", updatedUsers.get(updatedUsers.size() - 1).getUsername());
    }

    /**
     * Test that removing a user decreases the users list size by one and user is no longer found.
     */
    @Test
    public void testRemoveUserDecreasesSize() throws ParseException, org.json.simple.parser.ParseException {
        users.addUser("Mark", "Smith", "mark@gmail.com", "T654321", "msmith", "mypassword");
        DataWriter.saveUsers();
        int sizeBeforeRemoval = users.getUsers().size();
        users.removeUser("msmith");
        DataWriter.saveUsers();
        ArrayList<User> updatedUsers = DataLoader.getUsers();
        assertEquals(sizeBeforeRemoval - 1, updatedUsers.size());   
        assertNull(users.getUser("msmith"));
    }

    /**
     * Test that editing a user's email correctly updates the stored user information.
     */
    @Test
    public void testEditUserUpdatesEmail() throws ParseException, org.json.simple.parser.ParseException {
        users.addUser("Lisa", "Brown", "lisa@gmail.com", "F987654", "lbrown", "pass");
        DataWriter.saveUsers();
        String newEmail = "lisa.new@example.com";
        users.editUser("Lisa", "Brown", newEmail, "U987654", "lbrown", "pass");
        DataWriter.saveUsers();
        User editedUser = users.getUser("lbrown");
        assertEquals(newEmail, editedUser.getEmail());
    }

    /**
     * Test that getUser returns null for a username not in the system.
     */
    @Test
    public void testGetNonexistentUserReturnsNull() {
        User user = users.getUser("nonexistent123");
        assertNull(user);
    }

    /**
     * Test that removing a null username does not affect the user list size.
     */
    @Test
    public void testRemoveNullUserDoesNothing() {
        int sizeBefore = users.getUsers().size();
        users.removeUser(null);
        int sizeAfter = users.getUsers().size();
        assertEquals(sizeBefore, sizeAfter);
    }

    /**
     * Test that adding a user with an existing username still increases size 
     */
    @Test
    public void testAddDuplicateUsernameStillAddsUser() throws ParseException, org.json.simple.parser.ParseException {
        users.addUser("Emily", "Miller", "another@email.com", "X123456", "emill", "diffpass");
        DataWriter.saveUsers();
        ArrayList<User> updatedUsers = DataLoader.getUsers();
        long count = updatedUsers.stream().filter(u -> u.getUsername().equals("emill")).count();
        assertTrue(count > 1); // Shows duplicates were allowed
    }

    /**
     * Test that a newly created user does not have dietary restrictions by default.
     */
    @Test
    public void testNewUserHasNoDietaryRestrictions() {
        User emily = users.getUser("emill");
        assertTrue(emily.getDietaryRestrictions().isEmpty());
    }

    /**
     * Test that parsing recipe steps returns false when list is not populated.
     */
    @Test
    public void testParseStepsEmptyReturnsFalseForHasSteps() {
        JSONObject recipe = new JSONObject();
        ArrayList<String> steps = DataLoader.parseSteps(recipe);
        assertFalse("Parsed steps should be empty and thus 'not present'", steps.size() > 0);
    }
}


