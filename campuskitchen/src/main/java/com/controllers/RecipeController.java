package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javafx.fxml.FXML;
import com.campus.App;
import com.model.Recipe;
import com.model.RecipeList;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class RecipeController {

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
        VBox card = new VBox(5); 
    
        
        ImageView imageView;
        try {
            String imagePath = recipe.getImagePath() != null ? recipe.getImagePath() : "placeholder.png";
            String path = "/images/" + imagePath;
            Image image = new Image(getClass().getResourceAsStream(path));
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            
            imageView = new ImageView();
            imageView.setFitWidth(150);
            imageView.setFitHeight(100);
        }
    
        Label nameLabel = new Label(recipe.getName());
        nameLabel.getStyleClass().add("recipe-name");
        Label descLabel = new Label(recipe.getDescription());
        descLabel.getStyleClass().add("recipe-description");
        Label durationLabel = new Label("Cook time: " + recipe.getDuration() + " mins");
        durationLabel.getStyleClass().add("recipe-duration");
    
        card.getChildren().addAll(imageView, nameLabel, descLabel, durationLabel);
        card.getStyleClass().add("recipe-card");
    
        return card;
    }
    
    

    public void initialize() {
        recipeList = RecipeList.getInstance();

        if (user != null) {
            displayUserRecipes();
        } else {
            boxRecipeContainer.getChildren().clear();
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
