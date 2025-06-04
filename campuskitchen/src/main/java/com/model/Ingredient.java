package com.model;

public class Ingredient {
    
    private String name;
    private double amount;
    private Unit unit;
    
    public Ingredient (String name, double amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public void updateAmount(double amount) {
        this.amount = amount;
    }

    public void updateUnit(Unit unit) {
        this.unit = unit;
    }
}
