package com.github.edngulele.bankingapp.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeName;
    public TextView servings;
    public ImageView image;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);

        recipeName = itemView.findViewById(R.id.tv_recipe_name);
        servings = itemView.findViewById(R.id.tv_servings);
        image = itemView.findViewById(R.id.iv_recipe_thumbnail);
    }
}
