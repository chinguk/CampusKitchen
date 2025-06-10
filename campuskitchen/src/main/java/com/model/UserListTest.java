package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

public class UserListTest {

    private UserList users;

    @Before
    public void setup() throws ParseException, org.json.simple.parser.ParseException {
        users = UserList.getInstance();
        users.getUsers().clear();
        users.addUser("Alice", "Wonderland", "alice@example.com", "U1001", "alicew", "pass123");
        users.save();
    }

    /**
     * Test that user list starts with exactly one user after setup.
     */
    @Test
    public void testInitialUserCount() {
        assertEquals(1, users.getUsers().size());
    }

    /**
     * Test adding a new user increases list size by 1.
     */
    @Test
    public void testAddUserIncreasesSize() {
        int before = users.getUsers().size();
        boolean added = users.addUser("Bob", "Builder", "bob@example.com", "U1002", "bobb", "buildit");
        users.save();
        assertTrue(added);
        assertEquals(before + 1, users.getUsers().size());
    }

    /**
     * Test that getUser returns a user object for an existing username.
     */
    @Test
    public void testGetUserReturnsUser() {
        User u = users.getUser("alicew");
        assertNotNull(u);
        assertEquals("Alice", u.getFirstName());
    }

    /**
     * Test that getUser returns null for username not found.
     */
    @Test
    public void testGetUserReturnsNull() {
        User u = users.getUser("nonexistent");
        assertNull(u);
    }

    /**
     * Test editing a user changes their email.
     */
    @Test
    public void testEditUserChangesEmail() {
        users.editUser("Alice", "Wonderland", "alice@newmail.com", "U1001", "alicew", "pass123");
        users.save();
        User u = users.getUser("alicew");
        assertEquals("alice@newmail.com", u.getEmail());
    }

    /**
     * Test editing a user that doesn't exist does not throw exception.
     */
    @Test
    public void testEditNonexistentUser() {
        users.editUser("Fake", "User", "fake@example.com", "U9999", "fakeuser", "nopass");
    }

    /**
     * Test removing a user decreases the list size.
     */
    @Test
    public void testRemoveUserDecreasesSize() {
        users.addUser("Charlie", "Chocolate", "charlie@choco.com", "U1003", "charliec", "chocopass");
        users.save();
        int before = users.getUsers().size();
        users.removeUser("charliec");
        users.save();
        assertEquals(before - 1, users.getUsers().size());
    }

    /**
     * Test removing a user actually removes that user.
     */
    @Test
    public void testRemoveUserRemovesUser() {
        users.addUser("Daisy", "Duck", "daisy@duck.com", "U1004", "daisyd", "duckpass");
        users.save();
        users.removeUser("daisyd");
        users.save();
        assertNull(users.getUser("daisyd"));
    }

    /**
     * Test removing with null username does not affect user list size.
     */
    @Test
    public void testRemoveUserWithNullUsername() {
        int before = users.getUsers().size();
        users.removeUser(null);
        users.save();
        assertEquals(before, users.getUsers().size());
    }

    /**
     * Test save method returns true on successful save.
     */
    @Test
    public void testSaveReturnsTrue() {
        assertTrue(users.save());
    }

    /**
     * Test getUsers returns the actual list of users.
     */
    @Test
    public void testGetUsersReturnsList() {
        ArrayList<User> list = users.getUsers();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    /**
     * Test adding multiple users and verifying the usernames in list.
     */
    @Test
    public void testAddMultipleUsers() {
        users.addUser("Eve", "Evans", "eve@evans.com", "U1005", "evee", "evepass");
        users.addUser("Frank", "Foster", "frank@foster.com", "U1006", "frankf", "frankpass");
        users.save();
        assertNotNull(users.getUser("evee"));
        assertNotNull(users.getUser("frankf"));
    }

    /**
     * Test that getUser returns false when checking if a user exists (via null check).
     */
    @Test
    public void testGetUserReturnsNullForNonexistentUser() {
        User user = users.getUser("ghostuser");
        assertFalse("getUser should return null for nonexistent user", user != null);
    }
}
