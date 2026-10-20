package com.example.smartpantrymanager.database;

import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeSeeder {

    // Seeds the 20 starter recipes. If a previous run left a partial set, it is wiped first.
    public static void seedIfEmpty(DatabaseHelper db) {
        if (db.getRecipeCount() >= 20) {
            return;
        }
        db.clearRecipes(); // wipe any half-finished seed first

        add(db, "Scrambled Eggs",
                "1. Beat the eggs with a pinch of salt.\n2. Melt the butter in a pan on medium heat.\n3. Pour in the eggs and stir gently until just set.",
                ing("egg", 3, "piece"), ing("butter", 10, "g"), ing("salt", 1, "tsp"));

        add(db, "Tomato Pasta",
                "1. Boil the pasta in salted water until tender.\n2. Fry the garlic in oil, add chopped tomatoes and simmer 10 minutes.\n3. Toss the pasta through the sauce.",
                ing("pasta", 200, "g"), ing("tomato", 400, "g"), ing("garlic", 2, "piece"), ing("olive oil", 2, "tbsp"));

        add(db, "Cheese Omelette",
                "1. Beat the eggs.\n2. Melt butter in a pan and pour in the eggs.\n3. Add the cheese, fold in half and serve.",
                ing("egg", 3, "piece"), ing("cheese", 50, "g"), ing("butter", 10, "g"));

        add(db, "Garlic Fried Rice",
                "1. Cook the rice and let it cool.\n2. Fry the garlic in oil until golden.\n3. Add the rice and soy sauce and stir-fry for 5 minutes.",
                ing("rice", 300, "g"), ing("garlic", 3, "piece"), ing("oil", 2, "tbsp"), ing("soy sauce", 2, "tbsp"));

        add(db, "Mashed Potatoes",
                "1. Boil the peeled potatoes until soft.\n2. Drain and mash with the butter and milk.\n3. Season with salt.",
                ing("potato", 500, "g"), ing("butter", 30, "g"), ing("milk", 100, "ml"), ing("salt", 1, "tsp"));

        add(db, "Pancakes",
                "1. Whisk the flour, egg and milk into a smooth batter.\n2. Heat a little butter in a pan.\n3. Cook spoonfuls of batter until golden on both sides.",
                ing("flour", 150, "g"), ing("egg", 1, "piece"), ing("milk", 250, "ml"), ing("butter", 10, "g"));

        add(db, "French Toast",
                "1. Whisk the eggs and milk together.\n2. Dip the bread slices in the mixture.\n3. Fry in butter until golden on each side.",
                ing("bread", 4, "piece"), ing("egg", 2, "piece"), ing("milk", 100, "ml"), ing("butter", 10, "g"));

        add(db, "Tomato Soup",
                "1. Soften the onion in butter.\n2. Add the tomatoes and water and simmer for 20 minutes.\n3. Blend until smooth and season with salt.",
                ing("tomato", 500, "g"), ing("onion", 1, "piece"), ing("butter", 20, "g"), ing("water", 500, "ml"), ing("salt", 1, "tsp"));

        add(db, "Cheese Toastie",
                "1. Butter the bread on the outside.\n2. Put the cheese between the slices.\n3. Toast in a pan until golden and melted.",
                ing("bread", 2, "piece"), ing("cheese", 40, "g"), ing("butter", 10, "g"));

        add(db, "Egg Fried Rice",
                "1. Scramble the eggs in oil and set aside.\n2. Stir-fry the rice and onion.\n3. Mix in the eggs and soy sauce.",
                ing("rice", 300, "g"), ing("egg", 2, "piece"), ing("onion", 1, "piece"), ing("oil", 2, "tbsp"), ing("soy sauce", 2, "tbsp"));

        add(db, "Garlic Butter Pasta",
                "1. Boil the pasta until tender.\n2. Melt butter with the garlic in a pan.\n3. Toss the pasta in the garlic butter and add salt.",
                ing("pasta", 200, "g"), ing("butter", 40, "g"), ing("garlic", 3, "piece"), ing("salt", 1, "tsp"));

        add(db, "Fried Potatoes",
                "1. Slice the potatoes thinly.\n2. Fry in hot oil until golden and crisp.\n3. Sprinkle with salt.",
                ing("potato", 400, "g"), ing("oil", 3, "tbsp"), ing("salt", 1, "tsp"));

        add(db, "Cheese Pasta",
                "1. Boil the pasta.\n2. Stir in the butter and milk over low heat.\n3. Add the cheese and stir until creamy.",
                ing("pasta", 200, "g"), ing("cheese", 100, "g"), ing("butter", 20, "g"), ing("milk", 100, "ml"));

        add(db, "Tomato Rice",
                "1. Fry the onion in oil.\n2. Add the tomatoes and rice with water.\n3. Cover and simmer until the rice is cooked.",
                ing("rice", 250, "g"), ing("tomato", 300, "g"), ing("onion", 1, "piece"), ing("oil", 2, "tbsp"), ing("water", 500, "ml"));

        add(db, "Onion Omelette",
                "1. Fry the sliced onion in butter until soft.\n2. Pour in the beaten eggs.\n3. Cook until set and fold over.",
                ing("egg", 3, "piece"), ing("onion", 1, "piece"), ing("butter", 10, "g"), ing("salt", 1, "tsp"));

        add(db, "Garlic Bread",
                "1. Mix the butter with the crushed garlic.\n2. Spread it over the bread.\n3. Bake at 180C for 10 minutes.",
                ing("bread", 4, "piece"), ing("butter", 40, "g"), ing("garlic", 2, "piece"));

        add(db, "Milk Rice Pudding",
                "1. Combine the rice, milk and sugar in a pot.\n2. Simmer gently, stirring often, for 30 minutes.\n3. Serve warm.",
                ing("rice", 100, "g"), ing("milk", 750, "ml"), ing("sugar", 3, "tbsp"));

        add(db, "Boiled Eggs on Toast",
                "1. Boil the eggs for 7 minutes.\n2. Toast the bread and butter it.\n3. Peel the eggs, slice and place on the toast.",
                ing("egg", 2, "piece"), ing("bread", 2, "piece"), ing("butter", 10, "g"), ing("salt", 1, "tsp"));

        add(db, "Potato and Onion Fry",
                "1. Cube the potatoes and slice the onion.\n2. Fry the potatoes in oil until golden.\n3. Add the onion and cook until soft. Season with salt.",
                ing("potato", 400, "g"), ing("onion", 1, "piece"), ing("oil", 3, "tbsp"), ing("salt", 1, "tsp"));

        add(db, "Sweet Pancakes",
                "1. Whisk the flour, eggs, milk and sugar into a batter.\n2. Fry ladlefuls in buttered pan.\n3. Serve stacked.",
                ing("flour", 200, "g"), ing("egg", 2, "piece"), ing("milk", 300, "ml"), ing("sugar", 2, "tbsp"), ing("butter", 15, "g"));
    }

    private static RecipeIngredient ing(String name, double qty, String unit) {
        return new RecipeIngredient(name, qty, unit);
    }

    private static void add(DatabaseHelper db, String name, String steps, RecipeIngredient... ingredients) {
        List<RecipeIngredient> list = new ArrayList<>();
        for (RecipeIngredient i : ingredients) {
            list.add(i);
        }
        db.addRecipe(name, steps, list);
    }
}
