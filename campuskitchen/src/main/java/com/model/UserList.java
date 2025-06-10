package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages collection of all users
 */
public class UserList {
    private ArrayList<User> users;
    private static UserList userList;

    /**
     * Initializes user list
     */
    public UserList(){
        this.users = DataLoader.getUsers();
    }

    /**
     * @return single instance of UserList
     */
    public static UserList getInstance(){
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    /**
     * Adds new user to the system
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param universityID University ID
     * @param username Usernam
     * @param password Password
     * @return True if added successfully
     */
    public boolean addUser(String firstName, String lastName, String email, String universityID, String username, String password){
        User newUser = new User(firstName, lastName, email, universityID, username, password);
        users.add(newUser);
        return true;
    }
    
    /**
     * Finds user by username
     * @param username Username to search for
     * @return The matching User
     */
    public User getUser(String username){
        for (User u : users) {
            String myUserName = u.getUsername();
            
            if (u != null && u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * @return List of all users
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * Updates user's information
     * @param firstName new first name
     * @param lastName new last name
     * @param email new email
     * @param universityID new ID
     * @param username username
     * @param password new password
     */
    public void editUser(String firstName, String lastName, String email, String universityID, String username, String password){
        User existing = getUser(username);
        // if username does not exit return nothing
        if (existing == null) {
            return;
        }        

        // update list
        existing.setFirstName(firstName);
        existing.setLastName(lastName);
        existing.setEmail(email);
        existing.setUniversityID(universityID);
        existing.setPassword(password);
    }

    /**
     * Removes user
     * @param username Username of user to remove
     */
    public void removeUser(String username) {
        if (username == null) return;
        User delete = null;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                delete = u;
                break;
            }
        }
        if (delete != null) {
            users.remove(delete);
        }
    }
    
    /**
     * Saves user
     * @return True if save has succeeded
     */
    public boolean save() {
        return DataWriter.saveUsers();
    }

    public List<Ingredient> generateGroceryList(MealPlan plan) {
        return plan.generateGroceryList();
    }
}
