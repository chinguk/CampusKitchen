package com.controllers;

import com.model.Recipe;
import com.model.RecipeSystemFACADE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.*;

public class LoginController implements Initializable{

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
            lblErrorLogin.setText("");
            System.out.println("Login successful!");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

}