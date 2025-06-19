package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.campus.App;

public class BrowseController  {

    @FXML
    private Label lblTitle;

    @FXML
    private ScrollPane paneScroll;

    @FXML
    private VBox boxRecipeContainer;

    @FXML
    private VBox root;

    @FXML
    private Button btnRecipe;

    @FXML
    private Button btnMealPlan;

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnHome;

    @FXML
    void handleHomeClick(ActionEvent event) {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleHomeClick2(ActionEvent event) {
        try {
            App.setRoot("recipe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleBrowseClick(ActionEvent event) {
        try {
            App.setRoot("browse");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMealPlanClick(ActionEvent event) {
        try {
            App.setRoot("mealplan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
