package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.campus.App;
import com.model.Ingredient;
import com.model.MealPlan;
import com.model.RecipeSystemFACADE;


public class GroceryListController {

    @FXML
    private Label label;

    @FXML
    private ScrollPane paneScroll;

    @FXML
    private VBox boxListContainer;

    @FXML
    private HBox boxMenu;

    @FXML
    private Button btnRecipe;

    @FXML
    private Button btnMealPlan;

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnHome;

    private RecipeSystemFACADE facade;

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

    private void loadGroceryList() {
        
        boxListContainer.getChildren().clear();
        
        List<MealPlan> userMealPlans = facade.getUserMealPlans();
        
        if (userMealPlans.isEmpty()) {
            
            Label noMealPlansLabel = new Label("No meal plans found. Create a meal plan to generate a grocery list.");
            noMealPlansLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            boxListContainer.getChildren().add(noMealPlansLabel);
            return;
        }

        List<Ingredient> aggregatedGroceryList = aggregateIngredientsFromMealPlans(userMealPlans);
        
        if (aggregatedGroceryList.isEmpty()) {
            Label emptyListLabel = new Label("No ingredients found in your meal plans.");
            emptyListLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            boxListContainer.getChildren().add(emptyListLabel);
            return;
        }

        
        label.setText("Grocery List (" + aggregatedGroceryList.size() + " items)");

        
        for (Ingredient ingredient : aggregatedGroceryList) {
            HBox ingredientBox = createIngredientRow(ingredient);
            boxListContainer.getChildren().add(ingredientBox);
        }
    }

    /**
     * Aggregates ingredients from multiple meal plans, combining quantities of duplicate ingredients
     */
    private List<Ingredient> aggregateIngredientsFromMealPlans(List<MealPlan> mealPlans) {
        List<Ingredient> allIngredients = new ArrayList<>();
        
        for (MealPlan mealPlan : mealPlans) {
            List<Ingredient> mealPlanGroceries = facade.generateGroceryList(mealPlan);
            allIngredients.addAll(mealPlanGroceries);
        }
        
        return allIngredients;
    }

    /**
     * Creates a row in the grocery list for a single ingredient
     */
    private HBox createIngredientRow(Ingredient ingredient) {
        HBox ingredientBox = new HBox(10);
        ingredientBox.setPadding(new Insets(8, 12, 8, 12));
        ingredientBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; " + "-fx-border-width: 0 0 1 0; -fx-alignment: center-left;");

        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                ingredientBox.setStyle("-fx-background-color: #d4edda; -fx-border-color: #c3e6cb; " + "-fx-border-width: 0 0 1 0; -fx-alignment: center-left; -fx-opacity: 0.7;");
            } else {
                ingredientBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; " + "-fx-border-width: 0 0 1 0; -fx-alignment: center-left;");
            }
        });

        String displayText = formatIngredientDisplay(ingredient);
        Label ingredientLabel = new Label(displayText);
        ingredientLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: normal;");

        ingredientBox.getChildren().addAll(checkBox, ingredientLabel);
        return ingredientBox;
    }

    /**
     * Formats ingredient for display in the grocery list
     */
    private String formatIngredientDisplay(Ingredient ingredient) {
        StringBuilder display = new StringBuilder();
        
        if (ingredient.getAmount() > 0) {
            
            if (ingredient.getAmount() == (int) ingredient.getAmount()) {
                display.append((int) ingredient.getAmount());
            } else {
                display.append(String.format("%.1f", ingredient.getAmount()));
            }
            
            if (ingredient.getUnit() != null) {
                display.append(" ").append(ingredient.getUnit().toString().toLowerCase());
            }
            display.append(" ");
        }
        
        display.append(ingredient.getName());
        
        return display.toString();
    }

    /**
     * Refreshes the grocery list display
     */
    public void refreshGroceryList() {
        loadGroceryList();
    }
}




