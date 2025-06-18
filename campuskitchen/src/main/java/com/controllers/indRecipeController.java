package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class indRecipeController {

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
    private ImageView imgRecipe;

    @FXML
    private Label lblIngredients;

    @FXML
    private Label lblIngredientsTitle;

    @FXML
    private Label lblStepTitle;

    @FXML
    private Label lblSteps;

    @FXML
    private VBox boxIndRecipe;

    @FXML
    private Label lblTitle;

    public void setRecipeData(String title, String imagePath, String ingredients, String steps) {
        lblTitle.setText(title);
        lblIngredients.setText(ingredients);
        lblSteps.setText(steps);

        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imgRecipe.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image: " + imagePath);
            e.printStackTrace();
        }
    }
}