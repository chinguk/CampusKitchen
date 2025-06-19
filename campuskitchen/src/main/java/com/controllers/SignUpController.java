package com.controllers;

import java.io.IOException;

import com.campus.App;
import com.model.DataWriter;
import com.model.RecipeSystemFACADE;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        UserList.getInstance().addUser(firstName, lastName, email, universityId, username, password);

        RecipeSystemFACADE facade = RecipeSystemFACADE.getInstance();
        boolean accountCreated = facade.createAccount(firstName, lastName, email, universityId, username, password);
        
        if (accountCreated) {
        
            boolean saved = DataWriter.saveUsers();
            if (saved) {
                System.out.println("User successfully created and saved!");
                showSuccessAlert("Account created successfully!");
                
                clearForm();
                
                try {
                    App.setRoot("main"); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.err.println("User created but failed to save to file!");
                showErrorAlert("Account created but there was an issue saving. Please contact support.");
            }
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        universityIdField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void handleBackClick(ActionEvent event) {
        try {
            App.setRoot("main");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSubmitClick(ActionEvent event) {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
