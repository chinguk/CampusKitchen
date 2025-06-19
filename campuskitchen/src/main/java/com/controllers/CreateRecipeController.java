package com.controllers;

import java.io.IOException;

import com.campus.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateRecipeController {

    @FXML
    private Label lblWelcome;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField stepsField;

    @FXML
    private TextField ingredientsField;

    @FXML
    private TextField categoriesField;

    @FXML
    private Button btnSubmit;

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String durText = durationField.getText().trim();
        String stepsText = stepsField.getText().trim();
        String ingText = ingredientsField.getText().trim();
        String catText = categoriesField.getText().trim();
    }

    @FXML
    void handleSubmitClick(ActionEvent event) {
        try {
            App.setRoot("recipe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
