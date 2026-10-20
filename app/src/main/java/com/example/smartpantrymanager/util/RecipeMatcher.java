package com.example.smartpantrymanager.util;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Strict matching rule: a recipe is suggested ONLY if EVERY ingredient
 * is in the pantry in at least the required quantity.
 */
public class RecipeMatcher {

    // Returns only the recipes the user can make right now
    public static List<Recipe> getStrictMatches(List<Recipe> recipes, List<PantryItem> pantry) {
        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (canMake(recipe, pantry)) {
                matches.add(recipe);
            }
        }
        return matches;
    }

    // True only if every required ingredient is satisfied
    public static boolean canMake(Recipe recipe, List<PantryItem> pantry) {
        for (RecipeIngredient needed : recipe.getIngredients()) {
            if (!hasIngredient(needed, pantry)) {
                return false; // one missing ingredient rules the recipe out
            }
        }
        return true;
    }

    // Adds up all pantry rows that match this ingredient, then compares quantities
    private static boolean hasIngredient(RecipeIngredient needed, List<PantryItem> pantry) {
        String neededName = normalise(needed.getName());
        String neededGroup = unitGroup(needed.getUnit());
        double neededBase = toBaseUnit(needed.getQuantity(), needed.getUnit());

        double available = 0;
        for (PantryItem item : pantry) {
            if (normalise(item.getName()).equals(neededName)
                    && unitGroup(item.getUnit()).equals(neededGroup)) {
                available += toBaseUnit(item.getQuantity(), item.getUnit());
            }
        }
        return available >= neededBase;
    }

    // Makes names comparable: lowercase, trimmed, simple singular form
    public static String normalise(String name) {
        String n = name.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", " ");
        if (n.endsWith("ies") && n.length() > 3) {
            return n.substring(0, n.length() - 3) + "y";      // berries -> berry
        }
        if (n.endsWith("oes") && n.length() > 3) {
            return n.substring(0, n.length() - 2);            // tomatoes -> tomato
        }
        if (n.endsWith("ches") || n.endsWith("shes") || n.endsWith("sses")) {
            return n.substring(0, n.length() - 2);            // peaches -> peach
        }
        if (n.endsWith("s") && !n.endsWith("ss") && n.length() > 3) {
            return n.substring(0, n.length() - 1);            // eggs -> egg
        }
        return n;
    }

    // Converts a quantity to a base unit (g, ml, tsp, or count)
    private static double toBaseUnit(double qty, String unit) {
        switch (unit) {
            case "kg":   return qty * 1000;   // -> g
            case "l":    return qty * 1000;   // -> ml
            case "tbsp": return qty * 3;      // -> tsp
            case "cup":  return qty * 240;    // -> ml
            default:     return qty;          // g, ml, tsp, piece
        }
    }

    // Units only compare within the same group (weight, volume, spoon, count)
    private static String unitGroup(String unit) {
        switch (unit) {
            case "g":
            case "kg":    return "weight";
            case "ml":
            case "l":
            case "cup":   return "volume";
            case "tsp":
            case "tbsp":  return "spoon";
            default:      return "count";
        }
    }
}
