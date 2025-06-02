package com.model;

public class Ingredients {
    
    private String name;
    private double amount;
    private Unit unit;
    
    public Ingredients (String name, double amount, Unit unit) {
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
