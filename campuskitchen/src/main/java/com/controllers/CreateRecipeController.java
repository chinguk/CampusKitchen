package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.campus.App;
import com.model.Course;
import com.model.Culture;
import com.model.Dietary;
import com.model.Ingredient;
import com.model.RecipeList;
import com.model.RecipeStatus;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    private User user;

    @FXML
    void handleHomeClick(ActionEvent event) {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRecipeClick(ActionEvent event) {
        try {
            App.setRoot("recipe");
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

    @FXML
    void handleBrowseClick(ActionEvent event) {
        try {
            App.setRoot("browse");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

    }

    @FXML
    void handleSubmitClick(ActionEvent event) {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String durText = durationField.getText().trim();
        String stepsText = stepsField.getText().trim();
        String ingText = ingredientsField.getText().trim();
        String cultureText = categoriesField.getText().trim(); 

        if (name.isEmpty() || durText.isEmpty() || stepsText.isEmpty() || ingText.isEmpty()) {
            showError("Name, Duration, Steps, and Ingredients are required.");
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durText);
        } catch (NumberFormatException e) {
            showError("Duration must be a number.");
            return;
        }

        ArrayList<String> steps = splitAndTrim(stepsText, "\\|");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (String item : splitAndTrim(ingText, ",")) {
            ingredients.add(new Ingredient(item)); 
        }

        ArrayList<Culture> cultures = new ArrayList<>();
        for (String tag : splitAndTrim(cultureText, ",")) {
            cultures.add(Culture.valueOf(tag.toUpperCase())); 
        }

        ArrayList<Dietary> dietary = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();  

        RecipeStatus status = RecipeStatus.APPROVED;

        RecipeList.getInstance().addRecipe(name,description,duration,steps,ingredients,cultures,dietary,courses,user,status);
        com.model.DataWriter.saveRecipes();
        try {
            App.setRoot("recipe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private ArrayList<String> splitAndTrim(String text, String delimiter) {
        return new ArrayList<>(Arrays.stream(text.split(delimiter)).map(String::trim).filter(s -> !s.isEmpty()).toList());
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}



