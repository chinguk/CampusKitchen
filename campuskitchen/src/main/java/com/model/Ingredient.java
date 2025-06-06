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

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void updateAmount(double amount) {
        this.amount = amount;
    }

    public void updateUnit(Unit unit) {
        this.unit = unit;
    }

    public String toString() {
        return "Ingredient{" + "name='" + name + '\'' + ", amount=" + amount + ", unit=" + unit + '}';
    }
}
