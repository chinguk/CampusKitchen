package com.model;

/*
 * Represents admin user who has privilege to approve submitted recipes
 * Inherits fields and behaviors of User and adds ability to approve Recipes
 */
public class Admin extends User {

    /**
     * Constructs new Admin
     * @param firstName Admin's first name
     * @param lastName Admin's last name
     * @param email Admin's eemail
     * @param universityID Admin's ID
     * @param username Admin's username
     * @param password Admin's passowrd
     */
    Admin(String firstName, String lastName, String email, String universityID, String username, String password){
        super(firstName, lastName, email, universityID, username, password);
    }

    /*
     * Approves pending recipe and changing it's status
     */
    public void approveRecipe(Recipe recipe) {

    }
}
