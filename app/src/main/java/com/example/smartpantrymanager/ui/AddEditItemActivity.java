package com.example.smartpantrymanager.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.model.PantryItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;
public class AddEditItemActivity extends AppCompatActivity{

    private static final String[] UNITS = {"g", "kg", "ml", "l", "tsp", "tbsp", "cup", "piece"};

    private TextView textScreenTitle;
    private TextInputLayout layoutName, layoutQuantity;
    private TextInputEditText editName, editQuantity, editExpiry;
    private Spinner spinnerUnit;
    private Button buttonSave, buttonDelete, buttonClearDate;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        textScreenTitle = findViewById(R.id.textScreenTitle);
        layoutName = findViewById(R.id.layoutName);
        layoutQuantity = findViewById(R.id.layoutQuantity);
        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiry = findViewById(R.id.editExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonClearDate = findViewById(R.id.buttonClearDate);

        dbHelper = new DatabaseHelper(this);

        spinnerUnit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, UNITS));

        editExpiry.setOnClickListener(v -> showDatePicker());
        buttonClearDate.setOnClickListener(v -> editExpiry.setText(""));
        buttonSave.setOnClickListener(v -> saveItem());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            // month is 0-based, so add 1. Stored as yyyy-MM-dd
            editExpiry.setText(String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day));
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private boolean validateInput() {
        boolean valid = true;

        String name = editName.getText().toString().trim();
        if (name.isEmpty()) {
            layoutName.setError("Please enter an ingredient name");
            valid = false;
        } else {
            layoutName.setError(null);
        }

        String qtyText = editQuantity.getText().toString().trim();
        if (qtyText.isEmpty()) {
            layoutQuantity.setError("Please enter a quantity");
            valid = false;
        } else {
            try {
                double qty = Double.parseDouble(qtyText);
                if (qty <= 0) {
                    layoutQuantity.setError("Quantity must be greater than 0");
                    valid = false;
                } else {
                    layoutQuantity.setError(null);
                }
            } catch (NumberFormatException e) {
                layoutQuantity.setError("Enter a valid number");
                valid = false;
            }
        }
        return valid;
    }

    private void saveItem() {
        if (!validateInput()) {
            return;
        }

        String name = editName.getText().toString().trim();
        double quantity = Double.parseDouble(editQuantity.getText().toString().trim());
        String unit = (String) spinnerUnit.getSelectedItem();
        String expiry = editExpiry.getText().toString().trim();
        if (expiry.isEmpty()) {
            expiry = null;
        }

        PantryItem item = new PantryItem(0, name, quantity, unit, expiry);
        long result = dbHelper.addPantryItem(item);

        if (result == -1) {
            Toast.makeText(this, "Could not save ingredient", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
            finish(); // returns to the Pantry List, whose onResume refreshes the list
        }
    }
}

