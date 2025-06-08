package com.model;

/**
 * Represents ingredient used in a recipe
 */
public class Ingredient {
    
    private String name;
    private double amount;
    private Unit unit;
    
    /**
     * Constructs an Ingredient
     * @param name Ingredient's name
     * @param amount Quantity of ingredient
     * @param unit Unit of measurement for amount
     */
    public Ingredient (String name, double amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Returns name of ingredient
     * @return ingredient name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns quantity of ingredient
     * @return ingredient amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns Unit of measurement for ingredient amount
     * @return Unit of measurement
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Sets new name for ingredient
     * @param name new ingredient name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets new quantity for ingredient
     * @param amount new ingredient amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets new unit of measurement for ingredient
     * @param unit new unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Updates current amount
     * @param amount new amount to set
     */
    public void updateAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Updates current unit of measurement
     * @param unit New unit to set
     */
    public void updateUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Returns String representation of ingredient
     */
    public String toString() {
        return "Ingredient{" + "name='" + name + '\'' + ", amount=" + amount + ", unit=" + unit + '}';
    }
}
