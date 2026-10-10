package com.example.smartpantrymanager.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PantryListActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private FloatingActionButton fabAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        fabAddItem = findViewById(R.id.fabAddItem);

        // The adapter and database are connected in later steps
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
    }
}