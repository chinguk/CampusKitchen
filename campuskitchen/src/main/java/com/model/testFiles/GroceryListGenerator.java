package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroceryListGenerator {

    public static void generateGroceryList(MealPlan plan) {
        if (plan == null || plan.getRecipes().isEmpty()) {
            System.out.println("No recipes in meal plan; skipping grocery‐list generation.");
            return;
        }

        // 1) Gather every Ingredient from each Recipe in the plan
        //    and sum up quantities for identical ingredient names.
        //    We'll key by ingredientName + unit
        Map<String, Double> Quantities = new HashMap<>();
        Map<String, Unit> ingredientUnit    = new HashMap<>();

        for (Recipe r : plan.getRecipes()) {
            ArrayList<Ingredient> ings = r.generateGroceryList();

            for (Ingredient ing : ings) {
                String name = ing.getName();        
                Unit unit = ing.getUnit();
                double amount   = ing.getAmount();

                String key = name + "#" + unit.toString();
                double prev = Quantities.getOrDefault(key, 0.0);
                Quantities.put(key, prev + amount);
                ingredientUnit.put(key, unit);
            }
        }

        // 2) Write out to a text file.
        String outFile = "campuskitchen/src/main/resources/grocerylist_" + plan.getID() + ".txt";

        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write("Grocery List for MealPlan ID: " + plan.getID() + "\n");
            writer.write("––––––––––––––––––––––––––––––––––––––––––––\n");

            for (Map.Entry<String, Double> entry : Quantities.entrySet()) {
                String Key = entry.getKey();        
                double totalAmt    = entry.getValue();
                Unit unit        = ingredientUnit.get(Key);
                String name        = Key.split("#")[0];

                // Format: "2.0 pcs Tomato"
                writer.write(String.format("%.2f %s %s%n", totalAmt, unit.toString(), name));
            }

            System.out.println("Wrote grocery list to: " + outFile);
        } catch (IOException e) {
            System.err.println("Error writing grocery‐list file: " + e.getMessage());
        }
    }
}