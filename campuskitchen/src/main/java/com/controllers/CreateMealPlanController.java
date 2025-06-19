package com.controllers;

import java.io.IOException;

import com.campus.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateMealPlanController {

    @FXML
    private TextField nameField;

    @FXML
    private ScrollPane paneScroll;

    @FXML
    private VBox boxRecipeContainer;

    @FXML
    private Button btnSubmit;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleSubmitClick(ActionEvent event) {
        String planName = nameField.getText().trim();
        try {
            App.setRoot("mealplan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
