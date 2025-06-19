package com.controllers;

import java.util.ArrayList;

import com.campus.App;
import com.model.Recipe;
import com.model.RecipeSystemFACADE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    private ArrayList<Recipe> selectedRecipes;
    private RecipeSystemFACADE recipeSystem;

    @FXML
    private void initialize() {
        selectedRecipes = new ArrayList<>();
        recipeSystem = RecipeSystemFACADE.getInstance(); 
        loadAvailableRecipes();
    }

    /**
     * Load all available recipes and display them as selectable items
     */
    private void loadAvailableRecipes() {
        try {
            ArrayList<Recipe> recipeList = recipeSystem.getAllRecipes(); 
            
            if (recipeList != null) {
                boxRecipeContainer.getChildren().clear();
                
                for (Recipe recipe : recipeList) {
                    HBox recipeRow = createRecipeSelectionRow(recipe);
                    boxRecipeContainer.getChildren().add(recipeRow);
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createRecipeSelectionRow(Recipe recipe) {
        HBox row = new HBox(10);
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                if (!selectedRecipes.contains(recipe)) {
                    selectedRecipes.add(recipe);
                }
            } else {
                selectedRecipes.remove(recipe);
            }
        });
        
        Label recipeLabel = new Label(recipe.getName());
        recipeLabel.getStyleClass().add("recipe-label");
        
        row.getChildren().addAll(checkBox, recipeLabel);
        return row;
    }

    @FXML
private void handleSubmitClick(ActionEvent event) {
    String planName = nameField.getText().trim();

    if (planName.isEmpty()) {
        new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR,
            "Please enter a name for the meal plan.").show();
        return;
    }

    if (selectedRecipes.isEmpty()) {
        new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR,
            "Please select at least one recipe.").show();
        return;
    }

    // Create meal plan using Recipe objects directly
    com.model.MealPlan newPlan = new com.model.MealPlan(planName, selectedRecipes, generateRandomId());

    // Add the meal plan to the current user
    recipeSystem.addMealPlan(newPlan);
    
    // Save the users data to persist the meal plan
    com.model.DataWriter.saveUsers();

    try {
        App.setRoot("mealplan");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private String generateRandomId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < 5; i++) {
            id.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return id.toString();
    }
    

}
