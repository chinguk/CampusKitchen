package com.controllers;

import com.model.Recipe;
import com.model.RecipeSystemFACADE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.model.*;

public class LoginController {

    @FXML
    private Button primaryButton;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblErrorLogin;

    @FXML
    void login(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        RecipeSystemFACADE facade = RecipeSystemFACADE.getInstance();

        if (facade.login(username, password) == null) {
            lblErrorLogin.setText("Invalid Login Credentials");
            return;
        }
            System.out.println("Login successful!");
    }

}