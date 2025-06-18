package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.campus.App;
import com.model.Recipe;
import com.model.RecipeList;
import com.model.User;

public class RecipeController {

    @FXML 
    private HBox boxMenu;

    @FXML
    private VBox boxRecipeContainer;

    @FXML
    private VBox boxRecipePage;

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMealPlan;

    @FXML
    private Button btnRecipe;

    @FXML
    private Label lblTitle;

    @FXML
    private ScrollPane paneScroll;

    private User user;
    private RecipeList recipeList;

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
        displayUserRecipes();
    }

    private void displayUserRecipes() {
        if (user == null) {
            System.out.println("User is null, cannot display recipes");
            return;
        }
        boxRecipeContainer.getChildren().clear();

        ArrayList<UUID> recipeIds = user.getRecipesIds();
        ArrayList<Recipe> recipes = recipeList.getByIDs(recipeIds);

        for (Recipe recipe : recipes) {
            VBox card = createRecipeCard(recipe);
            boxRecipeContainer.getChildren().add(card);
        }
    }

    private VBox createRecipeCard(Recipe recipe) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 10;");
        card.setPrefHeight(100);

        // Recipe Image
        ImageView imageView = new ImageView();
        try {
            String imagePath = recipe.getImagePath() != null ? recipe.getImagePath() : "placeholder.png";
            Image image = new Image(getClass().getResourceAsStream("/images/" + imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Could not load image for recipe: " + recipe.getName());
        }
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);

        // Recipe Info
        VBox info = new VBox(5);
        Label name = new Label(recipe.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
        Label duration = new Label("Duration: " + recipe.getDuration() + " mins");
        Label rating = new Label("Rating: " + recipe.getAverageRating());

        info.getChildren().addAll(name, duration, rating);
        card.getChildren().addAll(imageView, info);
        return card;
    }

    public void initialize() {

        recipeList = RecipeList.getInstance();

        boxRecipeContainer.getChildren().clear();
        if (user != null) {
            displayUserRecipes();
        } else {
            for (Recipe recipe : recipeList.getRecipes()) {
                VBox card = createRecipeCard(recipe);
                boxRecipeContainer.getChildren().add(card);
            }
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
