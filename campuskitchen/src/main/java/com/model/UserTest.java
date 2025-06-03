package com.model;

// File: TestUserToString.java
import java.util.ArrayList;

public class UserTest {
    public static void main(String[] args) {
        ArrayList<Dietary> diets = new ArrayList<>();
        diets.add(Dietary.VEGETARIAN);
        diets.add(Dietary.KOSHER);

        ArrayList<MealPlan> plans = new ArrayList<>();

        User emily = new User(
            "Emily",
            "Miller",
            "emily.miller@gmail.com",
            "F193742",
            "emill23",
            "321"
        );
        emily.setDietaryRestrictions(diets);
        emily.setMealPlans(plans);
        System.out.println(emily);
    }
}
