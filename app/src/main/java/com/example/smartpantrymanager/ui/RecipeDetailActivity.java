package com.example.smartpantrymanager.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

/** Shows the full ingredient list and method for one recipe. */
public class RecipeDetailActivity extends AppCompatActivity {

    // Key used to pass the recipe's id from the Suggested Recipes screen
    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        long recipeId = getIntent().getLongExtra(EXTRA_RECIPE_ID, -1);
        Recipe recipe = new DatabaseHelper(this).getRecipe(recipeId);
        if (recipe == null) {
            finish();
            return;
        }

        setTitle("Recipe");
        ((TextView) findViewById(R.id.textDetailName)).setText(recipe.getName());

        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ing : recipe.getIngredients()) {
            double q = ing.getQuantity();
            String qty = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
            sb.append("\u2022 ").append(ing.getName())
                    .append(" - ").append(qty).append(" ").append(ing.getUnit()).append("\n");
        }
        ((TextView) findViewById(R.id.textDetailIngredients)).setText(sb.toString().trim());
        ((TextView) findViewById(R.id.textDetailSteps)).setText(recipe.getSteps());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}