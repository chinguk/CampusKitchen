package com.model;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RecipeFACADETest {
    private RecipeSystemFACADE facade;
    private Recipe sampleRecipe;
    private User author;
    private ArrayList<String> steps;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Course> courses;
    private ArrayList<Culture> cultures;
    private ArrayList<Dietary> dietaryList;
    private ArrayList<Course> categories;


    @Before
    public void setUp() {
    facade = RecipeSystemFACADE.getInstance();
            for (Recipe r : new ArrayList<>(facade.getAllRecipe())) {
            facade.deleteRecipe(r);
        }

        // common stub data
        steps = new ArrayList<>();
        steps.add("Step 1");

        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Sugar", 1, Unit.CUP));

        courses = new ArrayList<>();
        cultures = new ArrayList<>();
        dietaryList = new ArrayList<>();

        author = new User("Test", "User", "test@domain.com", "ID100", "testuser", "pw");
    } 

    /**
     * Account creation tests
     */
    @Test
    public void testCreateAccount() {
        boolean created = facade.createAccount("Chi", "Nguyen", "chi@example.com", "UNI123",
            "chin", "pw123"
        );
        assertTrue(created);
    }

    @Test
    public void testCreateAccountEmptyUsername() {
        boolean created = facade.createAccount("Empty", "User", "empty.user@example.com", "UNI12345", 
            "", "somePassword");
        assertFalse(created);
    }

    @Test
    public void testCreateAccountNullUsername() {
        boolean created = facade.createAccount("Null", "User", "null.user@example.com", "UNI67890", 
            null, "password123");
        assertFalse(created);
    }

    @Test
    public void testCreateAccountFailsDuplicate() {
        assertTrue(facade.createAccount("Chi","Nguyen","chi@example.com","UNI123","chin","pw123"));
        boolean second = facade.createAccount("Chi","Nguyen","chi2@example.com","UNI124","chin","pw456");
        assertFalse(second);
    }

    @Test
    public void testCreateAccountNullPassword() {
        boolean created = facade.createAccount("Null", "Password", "null.password@example.com", 
            "UNI24680", "nullPassUser", null);
        assertFalse(created);
    }

    @Test
    public void testCreateAccountEmptyPassword() {
        boolean created = facade.createAccount("chi", "Password", "empty.password@example.com", 
            "UNI13579", "emptyPassUser", "");
        assertFalse(created);
    }

    @Test
    public void testCreateAccountEmptyFirst() {
        boolean created = facade.createAccount("", "Password", "empty.password@example.com",                    
            "UNI13579", "emptyPassUser", "123");
        assertFalse(created);
    }

    @Test
    public void testCreateAccountEmptyLast() {
        boolean created = facade.createAccount("chi", "", "empty.password@example.com", 
            "UNI13579", "emptyPassUser", "123");
        assertFalse(created);
    }
    
    @Test
    public void testCreateAccountEmptyEmail() {
        boolean created = facade.createAccount("chi", "Password", "", 
            "UNI13579", "emptyPassUser", "123");
        assertFalse(created);
    }
    
    @Test
    public void testCreateAccountEmptyID() {
        boolean created = facade.createAccount("chi", "Password", "chi@email.com", 
            "", "emptyPassUser", "123");
        assertFalse(created);
    }

    /**
     * Login tests
     */
    @Test
    public void testLogin() {
        boolean created = facade.createAccount("Chi", "Nguyen", "chi@example.com", "UNI123",
            "chin", "pw123"
        );
        assertTrue(created);
        User logged = facade.login("chin", "pw123");
        assertNotNull(logged);
        assertEquals("chin", logged.getUsername());
        assertEquals("pw123", logged.getPassword());
    }

    @Test
    public void testLoginNotFound() {
        User missing = facade.login("feas", "wef");
        assertNull(missing);
    }

    @Test
    public void testLoginEmptyUser() {
        User missing = facade.login("", "wef");
        assertNull(missing);
    }

    @Test
    public void testLoginEmptyPassword() {
        User missing = facade.login("chi", "");
        assertNull(missing);
    }

    @Test
    public void testLoginNullUser() {
        User missing = facade.login(null, "wef");
        assertNull(missing);
    }

    @Test
    public void testLoginNullPassword() {
        User missing = facade.login("chi", null);
        assertNull(missing);
    }
    
    /**
     * Generate grocery list tests
     */
    @Test
    public void testGenerateGroceryListNullMealPlan() {
        assertNull(facade.generateGroceryList(null));
    }

    /**
     * Update profile tests
     */
    @Test
    public void testUpdateProfileFirstName() {
        boolean created = facade.createAccount("f", "l", "f@enauk.com", "234", "first", "pass42");
        assertTrue(created);

        User logged = facade.login("first", "pass42");
        assertNotNull(logged);

        logged.setFirstName("f2");

        facade.updateProfile(logged);

        User updated = facade.login("first", "pass42");
        assertNotNull(updated);

        assertEquals("f2", updated.getFirstName());
    }

    @Test
    public void testUpdateProfileLastName() {
        boolean created = facade.createAccount("f", "l", "f@enauk.com", "234", "first", "pass42");
        assertTrue(created);

        User logged = facade.login("first", "pass42");
        assertNotNull(logged);

        logged.setLastName("l2");

        facade.updateProfile(logged);

        User updated = facade.login("first", "pass42");
        assertNotNull(updated);

        assertEquals("l2", updated.getLastName());
    }

    @Test
    public void testUpdateProfileEmail() {
        boolean created = facade.createAccount("f", "l", "f@enauk.com", "234", "first", "pass42");
        assertTrue(created);

        User logged = facade.login("first", "pass42");
        assertNotNull(logged);

        logged.setEmail("new@last.com");

        facade.updateProfile(logged);

        User updated = facade.login("first", "pass42");
        assertNotNull(updated);

        assertEquals("new@last.com", updated.getEmail());
    }
    
    @Test
    public void testUpdateProfilePassword() {
        boolean created = facade.createAccount("f", "l", "f@enauk.com", "234", "first", "pass42");
        assertTrue(created);

        User logged = facade.login("first", "pass42");
        assertNotNull(logged);

        logged.setPassword("321");

        facade.updateProfile(logged);

        User updated = facade.login("first", "pass42");
        assertNotNull(updated);

        assertEquals("321", updated.getPassword());
    }
    
    @Test
    public void testUpdateProfileNotLoggedIn() {
        User dummy = new User("X", "Y", "x@y.com", "222", "xyuser", "pwd");
        facade.updateProfile(dummy);
        assertNull(facade.login("xyuser", "pwd"));
    }

    /**
     * Get recipe by keyword tests
     */
    @Test
    public void testGetRecipeByKeyword() {
        Recipe found = facade.getRecipeByKeyWord("Quinoa Bowl");
        assertNotNull(found);
        assertEquals("Quinoa Bowl", found.getName());
    }

    @Test
    public void testGetRecipeByKeywordPartialMatch() {
        Recipe found = facade.getRecipeByKeyWord("Quinoa");
        assertNotNull(found);
        assertEquals("Quinoa Bowl", found.getName());
    }

    @Test
    public void testGetRecipeByKeywordIgnoreCase() {
        Recipe found = facade.getRecipeByKeyWord("quinoa bowl");
        assertNotNull(found);
        assertEquals("Quinoa Bowl", found.getName());
    }

    /**
     * Get all recipes tests
     */
    @Test
    public void testGetAllRecipe() {
        List<Recipe> all = facade.getAllRecipe();
        assertNotNull(all);
        assertEquals(2, all.size());

        List<String> names = new ArrayList<>();
        for (Recipe r : all) {
            names.add(r.getName());
        }
        assertTrue(names.contains("Quinoa Bowl"));
        assertTrue(names.contains("Grilled Chicken Salad"));
    }

    @Test
    public void testGetAllRecipesEmptyWhenRecipeListIsEmpty() {
        List<Recipe> all = facade.getAllRecipe();
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    /**
     * Submit recipe tests
     */
    @Test
    public void testSubmitRecipe() {
        facade.submitRecipe("Test Recipe", "A simple test", 15, steps, ingredients, categories, cultures, dietaryList, author, RecipeStatus.PENDING);
        List<Recipe> all = facade.getAllRecipe();
        assertEquals(1, all.size());
        Recipe r = all.get(0);
        assertEquals("Test Recipe", r.getName());
        assertEquals(15, r.getDuration());
        assertSame(author, r.getAuthor());
    }

    /**
     * Approve recipes test
     */
    @Test
    public void testApproveRecipe() {
        // Arrange: admin logged in
        Admin admin = new Admin("Admin", "Two", "admin2@site.com", "ADM002", "admin2", "pw");

        Recipe already = new Recipe("Done", "Already approved", 10,steps, ingredients, courses, cultures, dietaryList, admin, RecipeStatus.APPROVED);
        assertEquals(RecipeStatus.APPROVED, already.getStatus());
    }

    /**
     * Delete recipe tests
     */
    @Test
    public void testDeleteRecipe() {
        facade.submitRecipe("First", "D1", 10, steps, ingredients, courses, cultures, dietaryList, author, RecipeStatus.APPROVED);
        facade.submitRecipe("Second", "D2", 20, steps, ingredients, courses, cultures, dietaryList, author, RecipeStatus.APPROVED);
        List<Recipe> before = new ArrayList<>(facade.getAllRecipe());
        assertEquals(2, before.size());

        // delete the first one
        Recipe toDelete = before.get(0);
        facade.deleteRecipe(toDelete);

        List<Recipe> after = facade.getAllRecipe();
        assertEquals(1, after.size());
        assertFalse(after.contains(toDelete));
    }
    
    @Test
    public void testDeleteRecipeNotPresent() {
        // add one recipe
        facade.submitRecipe("Existing", "Desc", 15, steps, ingredients, courses, cultures, dietaryList, author, RecipeStatus.APPROVED);
        List<Recipe> before = new ArrayList<>(facade.getAllRecipe());

        // attempt to delete a different Recipe instance
        Recipe notInList = new Recipe("Other", "Desc2", 5, steps, ingredients, courses, cultures, dietaryList, author, RecipeStatus.APPROVED);
        facade.deleteRecipe(notInList);

        List<Recipe> after = facade.getAllRecipe();
        assertEquals("Deleting a non-existent recipe should not change list size", before.size(), after.size());
        assertTrue("Original recipe should still be present", after.containsAll(before));
    }

    /**
     * Rate recipe tests
     */
    @Test
    public void testRateRecipe() {
        User rater = new User("R", "O", "r@o.com", "345", "rater", "pw");
        Date now = new Date();
        Rating rating = new Rating(rater, sampleRecipe, 5, now, "Excellent");

        facade.rateRecipe(sampleRecipe, rating);

        assertTrue(
            sampleRecipe.getRatings().contains(rating)
        );
    }

    @Test
    public void testRateRecipeNullRate() {
        User rater = new User("R", "O", "r@o.com", "345", "rater", "pw");
        Date now = new Date();
        Rating rating = new Rating(rater, sampleRecipe, 5, now, "Excellent");

        facade.rateRecipe(sampleRecipe, rating);

        assertTrue(
            sampleRecipe.getRatings().contains(rating)
        );
    }

    /**
     * Average rating tests
     */
    @Test
    public void testGetAverageRating() {
        User rater1 = new User("U1", "L1", "u1@x.com", "ID1", "user1", "pw1");
        User rater2 = new User("U2", "L2", "u2@x.com", "ID2", "user2", "pw2");
        Date now = new Date();

        Rating rating1 = new Rating(rater1, sampleRecipe, 4, now, "Good");
        Rating rating2 = new Rating(rater2, sampleRecipe, 2, now, "Fair");

        facade.rateRecipe(sampleRecipe, rating1);
        facade.rateRecipe(sampleRecipe, rating2);

        double avg = facade.getAverageRating(sampleRecipe);
        assertEquals(3.0, avg, 1e-6);
    }

    /**
     * Create meal plan tests
     */
    @Test
    public void testCreateMealPlan() {
        List<Recipe> recipes = new ArrayList<>();
        facade.submitRecipe("ToPlan", "Desc", 10, steps, ingredients,categories, cultures, dietaryList, author,RecipeStatus.APPROVED);
        recipes.add(facade.getAllRecipe().get(0));

        facade.createMealPlan("MyPlan", new ArrayList<>(recipes));
        assertEquals(1, author.getMealPlans().size());
        assertEquals("MyPlan", author.getMealPlans().get(0).getName());
    }

    @Test
    public void testCreateMealPlanNotLoggedIn() {
        facade.createMealPlan("AnyPlan", new ArrayList<>());
    }

}
