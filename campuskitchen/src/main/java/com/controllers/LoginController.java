package com.controllers;

import com.model.RecipeSystemFACADE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.campus.App;
import com.model.*;

public class LoginController implements Initializable{

     @FXML
    private ImageView imgLogin;

    @FXML
    private Label lblTitle;

    @FXML
    private Button primaryButton;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblErrorLogin;

    @FXML
    private Button btnBack;

    @FXML
    void handleBackClick(ActionEvent event) {
        try {
            App.setRoot("main");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
            lblErrorLogin.setText("Error loading home page");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

}