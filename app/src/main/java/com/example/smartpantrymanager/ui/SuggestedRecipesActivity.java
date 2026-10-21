package com.example.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapter.RecipeAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.util.NavigationHelper;
import com.example.smartpantrymanager.util.RecipeMatcher;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/** Shows only the recipes the user can make right now (strict matching). */
public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerRecipes;
    private TextView textNoRecipes;
    private RecipeAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        setTitle("Suggested Recipes");

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        textNoRecipes = findViewById(R.id.textNoRecipes);
        databaseHelper = new DatabaseHelper(this);

        // Tapping a recipe opens the detail screen, passing the recipe id in the Intent
        adapter = new RecipeAdapter(new ArrayList<>(), recipe -> {
            Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
            startActivity(intent);
        });
        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));
        recyclerRecipes.setAdapter(adapter);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        NavigationHelper.setup(this, nav, R.id.nav_suggested);
    }

    // Re-runs the matching every time the screen is shown, so it always
    // reflects the pantry as it is right now
    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestions();
    }

    private void loadSuggestions() {
        List<PantryItem> pantry = databaseHelper.getAllPantryItems();
        List<Recipe> allRecipes = databaseHelper.getAllRecipes();

        // The strict-matching rule: only recipes where EVERY ingredient is available
        List<Recipe> matches = RecipeMatcher.getStrictMatches(allRecipes, pantry);
        adapter.setRecipes(matches);

        if (matches.isEmpty()) {
            textNoRecipes.setVisibility(View.VISIBLE);
            recyclerRecipes.setVisibility(View.GONE);
        } else {
            textNoRecipes.setVisibility(View.GONE);
            recyclerRecipes.setVisibility(View.VISIBLE);
        }
    }
}