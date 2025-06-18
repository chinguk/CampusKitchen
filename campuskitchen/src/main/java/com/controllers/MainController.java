package com.controllers;

import java.io.IOException;

import com.campus.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label lblSaying;

    @FXML
    void handleLoginClick(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSignupClick(ActionEvent event) {
        try {
            App.setRoot("signup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
