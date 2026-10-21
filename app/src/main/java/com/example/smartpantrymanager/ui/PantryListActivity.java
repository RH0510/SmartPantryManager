package com.example.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.database.RecipeSeeder;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.util.NavigationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/** Home screen: lists every pantry item stored in the SQLite database. */
public class PantryListActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView textEmptyPantry;
    private FloatingActionButton fabAddItem;
    private PantryAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);
        setTitle("My Pantry");

        recyclerPantry = findViewById(R.id.recyclerPantry);
        textEmptyPantry = findViewById(R.id.textEmptyPantry);
        fabAddItem = findViewById(R.id.fabAddItem);

        databaseHelper = new DatabaseHelper(this);
        RecipeSeeder.seedIfEmpty(databaseHelper); // loads the 20 recipes on first run

        // Tapping a row opens the same form in edit mode, passing the item's id
        adapter = new PantryAdapter(new ArrayList<>(), item -> {
            Intent intent = new Intent(PantryListActivity.this, AddEditItemActivity.class);
            intent.putExtra(AddEditItemActivity.EXTRA_ITEM_ID, item.getId());
            startActivity(intent);
        });

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(adapter);

        // Opens the Add Ingredient screen when + is tapped
        fabAddItem.setOnClickListener(v ->
                startActivity(new Intent(PantryListActivity.this, AddEditItemActivity.class)));

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        NavigationHelper.setup(this, nav, R.id.nav_pantry);
    }

    // Runs every time the screen becomes visible, so the list and the
    // Settings choice are refreshed after returning from another screen
    @Override
    protected void onResume() {
        super.onResume();

        boolean highlight = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(SettingsActivity.KEY_HIGHLIGHT_EXPIRING, true);
        adapter.setHighlightExpiring(highlight);

        loadPantry();
    }

    private void loadPantry() {
        List<PantryItem> items = databaseHelper.getAllPantryItems();
        adapter.setItems(items);

        if (items.isEmpty()) {
            textEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);
        } else {
            textEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
    }
}