package com.github.edngulele.bankingapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    public TextView ingredientName;
    public TextView amount;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
        amount = itemView.findViewById(R.id.tv_ingredient_amount);
    }
}
