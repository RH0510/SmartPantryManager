package com.example.smartpantrymanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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

        recyclerPantry = findViewById(R.id.recyclerPantry);
        textEmptyPantry = findViewById(R.id.textEmptyPantry);
        fabAddItem = findViewById(R.id.fabAddItem);

        databaseHelper = new DatabaseHelper(this);

        adapter = new PantryAdapter(new ArrayList<>(), item ->{

        });

        // The adapter and database are connected in later steps
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(adapter);

    }
    // Runs every time the screen becomes visible, so the list is refreshed
    // after returning from the Add/Edit screen
    @Override
    protected void onResume() {
        super.onResume();
        loadPantry();
    }

    private void loadPantry(){
        List<PantryItem> items = databaseHelper.getAllPantryItems();
        adapter.setItems(items);

        if (items.isEmpty()){
            textEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);
        } else {
            textEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
    }
}