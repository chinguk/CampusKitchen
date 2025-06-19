package com.controllers;

import java.util.ArrayList;
import java.util.UUID;

import com.campus.App;
import com.model.MealPlan;
import com.model.Recipe;
import com.model.User;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    private Button btnMealPlans;

    @FXML
    private Button btnRecipe;

    @FXML
    private Label lblMealPlans;

    @FXML
    private VBox mealPlanContainer;

    @FXML
    void handleCreateMealPlan(ActionEvent event) {
        try {
            App.setRoot("createmealplan");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mealPlanContainer.getChildren().clear();

        ArrayList<MealPlan> mealPlans = user.getMealPlans();

       
    }

    @FXML
    void handleGenerateList(ActionEvent event) {
        try {
            App.setRoot("grocerylist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User user;
    private UserList userList;



    public void displayUserMealPlans(){
        if(user == null){
            System.out.println("User is null, cannot display Meal Plans");
            return;
        }



    }

    

}
