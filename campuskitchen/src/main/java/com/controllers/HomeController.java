package com.controllers;

import java.io.IOException;
import java.util.ResourceBundle;

import com.campus.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class HomeController {

    @FXML
    private VBox boxHome;

    @FXML
    private HBox boxMenu;

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMealPlan;

    @FXML
    private Button btnRecipe;

    @FXML
    private ImageView imgHome;

    @FXML
    private Label lblFavorite;

    @FXML
    private Label lblMealPlan;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblTodayPlan;

    @FXML
    void handleHomeClick(ActionEvent event) {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}