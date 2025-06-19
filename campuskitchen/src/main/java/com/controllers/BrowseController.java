package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.campus.App;
import com.model.Recipe;
import com.model.RecipeList;
import com.model.User;

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

    private RecipeList recipeList;
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

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        recipeList = RecipeList.getInstance();

        boxRecipeContainer.getChildren().clear();
        if (user != null) {
            displayRecipes();
        } else {
            for (Recipe recipe : recipeList.getRecipes()) {
                VBox card = createRecipeCard(recipe);
                boxRecipeContainer.getChildren().add(card);
            }
        }
    }

    public void displayRecipes(){
        boxRecipeContainer.getChildren().clear();

        ArrayList<Recipe> allRecipes = recipeList.getRecipes();
        for (Recipe recipe : allRecipes) {
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
        imageView.setFitWidth(70);
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

    
}
