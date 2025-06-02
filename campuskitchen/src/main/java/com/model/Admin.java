package com.model;

public class Admin extends User {
    private String adminID;

    Admin(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName, email, username, password);
        this.adminID = null;
    }

    public void approveRecipe(Recipe) {

    }
}
