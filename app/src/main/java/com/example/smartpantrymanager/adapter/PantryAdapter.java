package com.example.smartpantrymanager.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.PantryItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Binds pantry items to the cards in the Pantry List RecyclerView. */
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    private List<PantryItem> items;
    private final OnItemClickListener listener;
    private boolean highlightExpiring = true;

    public PantryAdapter(List<PantryItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    // Replaces the list contents and redraws the RecyclerView
    public void setItems(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    // Controlled by the Settings screen
    public void setHighlightExpiring(boolean highlight) {
        this.highlightExpiring = highlight;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);

        holder.textName.setText(item.getName());

        // Show 500 instead of 500.0 for whole numbers
        double qty = item.getQuantity();
        String qtyText = (qty == Math.floor(qty)) ? String.valueOf((long) qty) : String.valueOf(qty);
        holder.textQuantity.setText(qtyText + " " + item.getUnit());

        String expiry = item.getExpiryDate();
        // Reset the colour first, because RecyclerView reuses row views
        holder.textExpiry.setTextColor(holder.defaultExpiryColor);

        if (expiry == null || expiry.isEmpty()) {
            holder.textExpiry.setText("No expiry date");
        } else if (highlightExpiring && isExpiringSoon(expiry)) {
            holder.textExpiry.setText("Expires: " + expiry + " (expiring soon!)");
            holder.textExpiry.setTextColor(Color.RED);
        } else {
            holder.textExpiry.setText("Expires: " + expiry);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    // True if the date is today, already past, or within the next 3 days
    private boolean isExpiringSoon(String dateText) {
        try {
            Date expiry = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateText);
            if (expiry == null) {
                return false;
            }
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            long days = (expiry.getTime() - today.getTimeInMillis()) / (24L * 60 * 60 * 1000);
            return days <= 3;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textQuantity, textExpiry;
        ColorStateList defaultExpiryColor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textItemName);
            textQuantity = itemView.findViewById(R.id.textItemQuantity);
            textExpiry = itemView.findViewById(R.id.textItemExpiry);
            defaultExpiryColor = textExpiry.getTextColors();
        }
    }
}