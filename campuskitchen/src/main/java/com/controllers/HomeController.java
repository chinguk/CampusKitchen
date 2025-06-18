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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;



public class HomeController {

    @FXML
    private VBox boxMealPlan;

    @FXML
    private VBox boxHome;

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
    private ImageView imgHome;

    @FXML
    private Label lblFavorite;

    @FXML
    private Label lblMealPlan;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblTodayPlan;


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
    }

    @FXML
    public void initialize() {
        User user = UserList.getInstance().getCurrentUser(); 
        if (user == null) return;

        // Show favorite recipe preview
        ArrayList<Recipe> favorites = user.getFavoriteRecipes();
        if (!favorites.isEmpty()) {
            Recipe favorite = favorites.get(0);
            lblFavorite.setText("Favorite: " + favorite.getName());

            String path = favorite.getImagePath(); 
            if (path != null && !path.isEmpty()) {
                try {
                    Image favImg = new Image(path);
                    imgHome.setImage(favImg);
                } catch (Exception e) {
                    System.err.println("Could not load image: " + path);
                }
            }
        } else {
            lblFavorite.setText("No favorites yet");
        }

        // Show meal plan name
        ArrayList<MealPlan> plans = user.getMealPlans();
        if (!plans.isEmpty()) {
            MealPlan todayPlan = plans.get(0); 
            lblMealPlan.setText("“" + todayPlan.getName() + "”");
        } else {
            lblMealPlan.setText("No meal plan selected");
        }
    }
}