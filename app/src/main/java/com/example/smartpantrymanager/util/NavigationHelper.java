package com.example.smartpantrymanager.util;

import android.app.Activity;
import android.content.Intent;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.ui.PantryListActivity;
import com.example.smartpantrymanager.ui.SettingsActivity;
import com.example.smartpantrymanager.ui.SuggestedRecipesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Connects the bottom navigation bar to the three main screens using Intents.
 * Every main screen calls setup(), so the navigation code lives in one place.
 */
public class NavigationHelper {
    public static void setup(Activity activity, BottomNavigationView nav, int selectedId){
        nav.setSelectedItemId(selectedId); // highlight the current screen

        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == selectedId) {
                return true; // already on this screen
            }

            Class<?> target = null;
            if (id == R.id.nav_pantry) {
                target = PantryListActivity.class;
            } else if (id == R.id.nav_suggested) {
                target = SuggestedRecipesActivity.class;
            } else if (id == R.id.nav_settings) {
                target = SettingsActivity.class;
            }

            if (target == null) {
                return false;
            }

            Intent intent = new Intent(activity, target);
            // Reuse the screen if it already exists instead of stacking copies
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.startActivity(intent);
            return true;
        });
    }
}

