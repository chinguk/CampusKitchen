package com.model;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RecipeFACADETest {
    private RecipeSystemFACADE facade;

    @Before
    public void setUp() {
        facade = RecipeSystemFACADE.getInstance();
    } 

    /**
     * Account creation tests
     */
    @Test
    public void testCreateAccount() {
        boolean created = facade.createAccount("Chi", "Nguyen", "chi@example.com", "UNI123",
            "chin", "pw123"
        );
        assertTrue("Should return true for a new username", created);
    }

    @Test
    public void testCreateAccountEmptyUsername() {
        boolean created = facade.createAccount("Empty", "User", "empty.user@example.com", "UNI12345", 
            "", "somePassword");
        assertFalse("createAccount() should return false when username is empty", created);
    }

    @Test
    public void testCreateAccountNullUsername() {
        boolean created = facade.createAccount("Null", "User", "null.user@example.com", "UNI67890", 
            null, "password123");
        assertFalse("createAccount() should return false when username is null", created);
    }

    @Test
    public void testCreateAccountFailsDuplicate() {
        assertTrue(facade.createAccount("Chi","Nguyen","chi@example.com","UNI123","chin","pw123"));
        boolean second = facade.createAccount("Chi","Nguyen","chi2@example.com","UNI124","chin","pw456");
        assertFalse("Should return false when username already exists", second);
    }

    @Test
    public void testCreateAccountNullPassword() {
            boolean created = facade.createAccount(
        "Null", 
        "Password", 
        "null.password@example.com", 
        "UNI24680", 
        "nullPassUser", 
        null            // null password
    );
    assertFalse(
        "createAccount() should return false when password is null",
        created
    );
    }

    @Test
    public void testCreateAccountEmptyPassword() {
        boolean created = facade.createAccount(
            "Empty", 
            "Password", 
            "empty.password@example.com", 
            "UNI13579", 
            "emptyPassUser", 
            "");
        assertFalse(
        "createAccount() should return false when password is empty",
        created);
    }

    @Test
    public void testCreateAccountEmptyFirst() {
        boolean created = facade.createAccount(
        "", 
        "Password", 
        "empty.password@example.com", 
        "UNI13579", 
        "emptyPassUser", 
        "123"
        );
        assertFalse(
        "createAccount() should return false when name is empty",
        created
        );
    }

    /**
     * Login tests
     */
    @Test
    public void testLogin() {
        String user = "chi";
        String pass = "chi";
        String email = "chi2@email.com";
        User logged = facade.login(user, pass);
        assertNotNull("login should return a User when username exists", logged);
        assertEquals(user, logged.getUsername());
        assertEquals(email, logged.getEmail());
    }

    @Test
    public void testLoginNotFound() {
        User missing = facade.login("feas", "wef");
        assertNull("login should return null when username is not found", missing);
    }

    @Test
    public void testLoginEmptyUser() {
        User missing = facade.login("", "wef");
        assertNull("login should return null when username is empty", missing);
    }

    @Test
    public void testLoginEmptyPassword() {
        User missing = facade.login("chi", "");
        assertNull("login should return null when password is empty", missing);
    }

        @Test
    public void testLoginNullUser() {
        User missing = facade.login(null, "wef");
        assertNull("login should return null when username is null", missing);
    }

    @Test
    public void testLoginNullPassword() {
        User missing = facade.login("chi", null);
        assertNull("login should return null when password is null", missing);
    }

    @Test
    public void testLoginAfterCreate() {
        // create the account
        String username = "alex";
        String password = "secret";
        String email    = "alex@foo.com";
        assertTrue(facade.createAccount("Alex","Foo", email, "UNI999", username, password));

        // login
        User u = facade.login(username, password);
        assertNotNull("login should return a User for valid credentials", u);
        assertEquals("username should match", username, u.getUsername());
        assertEquals("email should match",    email,    u.getEmail());
    }
    
    /**
     * Generate grocery list tests
     */
    @Test
    public void testGenerateGroceryListNullInput() {
        assertNull("Passing null mealPlan should return null",
                   facade.generateGroceryList(null));
    }

    /**
     * Update profile tests
     */
    @Test
    public void testUpdateProfile() {

    }

    /**
     * Get recipe by keyword tests
     */
    @Test
    public void testGetRecipeByKeyword() {

    }

    /**
     * Get all recipes tests
     */
    @Test
    public void testGetAllRecipe() {

    }

    /**
     * Submit recipe tests
     */
    @Test
    public void testSubmitRecipe() {

    }

    /**
     * Approve recipes test
     */
    @Test
    public void testApproveRecipe() {

    }

    /**
     * Delete recipe tests
     */
    @Test
    public void testDeleteRecipe() {

    }

    /**
     * Rate recipe tests
     */
    @Test
    public void testRateRecipe() {

    }

    /**
     * Average rating tests
     */
    @Test
    public void testGetAverageRating() {

    }

    /**
     * Create meal plan tests
     */
    @Test
    public void testCreateMealPlan() {

    }

    /**
     * Logout tests
     */
    @Test
    public void testLogout() {

    }
}
