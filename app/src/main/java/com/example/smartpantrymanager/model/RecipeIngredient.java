package com.example.smartpantrymanager.model;

/** One ingredient line a recipe requires (a row in recipe_ingredients). */
public class RecipeIngredient {
    private String name;
    private double quantity;
    private String unit;

    public RecipeIngredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName(){
        return name;
    }
    public double getQuantity(){
        return quantity;
    }
    public String getUnit(){
        return unit;
    }
}
