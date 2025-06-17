package com.controllers;

import java.io.IOException;
import java.lang.classfile.Label;
import java.util.ArrayList;
import java.util.UUID;

import javafx.fxml.FXML;
import com.campus.App;
import com.model.Recipe;
import com.model.RecipeList;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class RecipeController {

    @FXML
    private GridPane grid_recipes;

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

        ArrayList<UUID> recipeIds = user.getRecipesIds();
        ArrayList<Recipe> recipes = recipeList.getByIDs(recipeIds);
        for (int i = 0; i < recipeIds.size(); i++) {
            Recipe recipe = recipes.get(i);
            VBox vbox = new VBox();
            Label recipeName = new Label(recipe.getName());
            recipeName.setFont(new Font(14));
            vbox.getChildren().add(recipeName);
            Image image = new Image(getClass().getResourceAsStream("/images/"));
            ImageView image_recipe = new ImageView(image);
            image_recipe.setFitWidth(100);
            image_recipe.setPreserveRatio(true);
            vbox.getChildren().add(image_recipe);
            vbox.getStyleClass().add("recipe-grid-item");
            grid_recipes.add(vbox, i, 0);
        }

    }

    private HBox createRecipeCard(Recipe recipe) {
        HBox card = new HBox(10);
        Label name = new Label(recipe.getName());
        card.getChildren().add(name);
        boxRecipeContainer.getChildren().add(card);
    }

    public void initialize() {
        recipeList = RecipeList().getInstance();

        for (Recipe recipe : recipeList.getRecipes()) {
            HBox card = createRecipeCard(recipe);
            boxRecipeContainer.getChildren().add(card);
        }
    }
}
