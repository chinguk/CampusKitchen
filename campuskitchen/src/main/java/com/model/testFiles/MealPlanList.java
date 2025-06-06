package com.model;

import java.util.ArrayList;
import java.util.List;

public class MealPlanList {
    private static MealPlanList instance = null;
    private List<MealPlan> mealplans;

    private MealPlanList(List<MealPlan> initialPlans) {
        this.mealplans = (initialPlans != null) ? new ArrayList<>(initialPlans) : new ArrayList<>();
    }

    public static MealPlanList getInstance (List<MealPlan> Dataloader) {
        if (instance == null) {
            instance = new MealPlanList(Dataloader);
        }
        return instance;
    }

    public static MealPlanList getInstance() {
        if (instance == null) {
            instance = new MealPlanList(new ArrayList<>());
        }
        return instance;
    }

    public List<MealPlan> getMealPlans() {
        return mealplans;
    }

    public void addMealPlan(MealPlan mealPlan) {
        if (mealPlan != null) {
            mealplans.add(mealPlan);
        }
    }

    public MealPlan getByID(String id) {
        for (MealPlan mp : mealplans) {
            if (mp.getID().equals(id)) {
                return mp;
            }
        }
        return null;
    }

    public void removeMealPlan(String id) {
        mealplans.removeIf(mp -> mp.getID().equals(id));
    }
}
