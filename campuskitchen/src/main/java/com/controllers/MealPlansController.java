package com.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.campus.App;
import com.model.MealPlan;
import com.model.Recipe;
import com.model.User;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;


public class MealPlansController {

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnCreateMealPlan;

    @FXML
    private Button btnGroceryList;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMealPlan;

    @FXML
    private Button btnRecipe;

    @FXML
    private Label lblMealPlans;

    @FXML
    private ScrollPane scrollPane;

    private VBox mealPlanContainer;
    private User user;
    private UserList userList;

    @FXML
    void handleCreateMealPlan(ActionEvent event) {
        try {
            App.setRoot("createmealplan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGenerateList(ActionEvent event) {
        try {
            App.setRoot("grocerylist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    void handleBrowseClick(ActionEvent event) {
        try {
            App.setRoot("browse");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        userList = UserList.getInstance();
        user = userList.getCurrentUser();
        
        mealPlanContainer = (VBox) scrollPane.getContent();
        
        displayUserMealPlans();
    }

    private void displayUserMealPlans() {
        
        mealPlanContainer.getChildren().clear();

        ArrayList<MealPlan> mealPlans = user.getMealPlans();

        if (mealPlans == null || mealPlans.isEmpty()) {
            displayNoMealPlansMessage();
            return;
        }

        for (MealPlan mealPlan : mealPlans) {
            VBox card = createMealPlanCard(mealPlan);
            mealPlanContainer.getChildren().add(card);
        }
    }

    private VBox createMealPlanCard(MealPlan mealPlan) {
        VBox card = new VBox(10);
        card.setPrefHeight(120);

        VBox info = new VBox(8);
        
        Label name = new Label(mealPlan.getName());
        
        int recipeCount = 0;
        if (mealPlan.getRecipes() != null) {
            recipeCount = mealPlan.getRecipes().size();
        } else if (mealPlan.getRecipeIds() != null) {
            recipeCount = mealPlan.getRecipeIds().size();
        }
        
        Label recipeCountLabel = new Label("Recipes: " + recipeCount);
        
        Label idLabel = new Label("ID: " + mealPlan.getID().substring(0, 8) + "...");

        info.getChildren().addAll(name, recipeCountLabel, idLabel);

        card.setOnMouseClicked(e -> showMealPlanDetails(mealPlan));
        
        card.getChildren().add(info);
        return card;
    }

    private void showMealPlanDetails(MealPlan mealPlan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Meal Plan Details");
        alert.setHeaderText(mealPlan.getName());
        
        StringBuilder content = new StringBuilder();
        content.append("Meal Plan ID: ").append(mealPlan.getID()).append("\n\n");
        
        // Get recipes
        ArrayList<Recipe> recipes = mealPlan.getRecipes();
        if (recipes != null && !recipes.isEmpty()) {
            content.append("Recips in Meal Plan:\n");
            content.append("=".repeat(40)).append("\n\n");
            
            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                content.append((i + 1)).append(". ").append(recipe.getName()).append("\n");
                
                if (recipe.getDuration() > 0) {
                    content.append("   Duration: ").append(recipe.getDuration()).append(" mins\n");
                }
                if (recipe.getAverageRating() > 0) {
                    content.append("   Rating: ").append(recipe.getAverageRating()).append("/5\n");
                }
                
                if (recipe.getSteps() != null && !recipe.getSteps().isEmpty()) {
                    content.append("   Instructions: ").append(recipe.getSteps()).append("\n");
                }
                content.append("\n");
            }
            
        } else {
            content.append("No recipes in this meal plan.\n");
        }
        
        TextArea textArea = new TextArea(content.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(15);
        textArea.setPrefColumnCount(50);
        textArea.setStyle("-fx-font-family: 'Arial', sans-serif; -fx-font-size: 12px;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(500, 400);
        
        alert.showAndWait();
    }

    private void displayNoMealPlansMessage() {
        Label noMealPlansLabel = new Label("No meal plans found. Create your first meal plan!");
        noMealPlansLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: #7f8c8d; -fx-padding: 20;");
        mealPlanContainer.getChildren().add(noMealPlansLabel);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
