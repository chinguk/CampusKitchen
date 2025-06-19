package com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class BrowseController implements Initializable {

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

    @Override
    public void initialize() {

    }

}
