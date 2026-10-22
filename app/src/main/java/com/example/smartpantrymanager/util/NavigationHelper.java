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
 */
public class NavigationHelper {

    public static void setup(Activity activity, BottomNavigationView nav, int selectedId) {
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            Class<?> target = null;
            if (id == R.id.nav_pantry) {
                target = PantryListActivity.class;
            } else if (id == R.id.nav_suggested) {
                target = SuggestedRecipesActivity.class;
            } else if (id == R.id.nav_settings) {
                target = SettingsActivity.class;
            }

            // Already on this screen, or unknown item: do nothing
            if (target == null || target.equals(activity.getClass())) {
                return true;
            }

            Intent intent = new Intent(activity, target);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.startActivity(intent);
            return true;
        });

        // Highlight the current screen (after the listener, so it is set correctly)
        nav.getMenu().findItem(selectedId).setChecked(true);
    }
}