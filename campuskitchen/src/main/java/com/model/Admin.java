package com.model;

public class Admin extends User {
    private String adminID;

    // matched parameters to parent
    Admin(String firstName, String lastName, String email, String universityID, String username, String password){
        super(firstName, lastName, email, universityID, username, password);
    
    }

    public void approveRecipe(Recipe recipe) {

    }
}
