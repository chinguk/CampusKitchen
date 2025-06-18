package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField firstNameField;

    @FXML 
    private TextField lastNameField;

    @FXML 
    private TextField emailField;

    @FXML
    private TextField universityIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button btnSubmit;
    
    @FXML
    private Label lblWelcome;

    @FXML
    private void handleSubmit() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String universityId = universityIdField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        System.out.println("Sign Up Info:");
        System.out.println("First: " + firstName);
        System.out.println("Last: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("University ID: " + universityId);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        
    }
}
