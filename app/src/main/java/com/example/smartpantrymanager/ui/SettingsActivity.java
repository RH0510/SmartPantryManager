package com.example.smartpantrymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.util.NavigationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
public class SettingsActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "smart_pantry_prefs";
    public static final String KEY_HIGHLIGHT_EXPIRING = "highlight_expiring";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        SwitchMaterial switchExpiring = findViewById(R.id.switchExpiring);
        switchExpiring.setChecked(prefs.getBoolean(KEY_HIGHLIGHT_EXPIRING, true));

        //Save the choice immediately whenever the switch is toggled
        //        switchExpiring.setOnCheckedChangeListener((button, isChecked) ->
        //                prefs.edit().putBoolean(KEY_HIGHLIGHT_EXPIRING, isChecked).apply());
        //
        //        BottomNavigationView nav = findViewById(R.id.bottomNav);
        //        NavigationHelper.setup(this, nav, R.id.nav_settings);
        //
        }
    }

