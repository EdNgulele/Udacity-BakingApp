package com.github.edngulele.bankingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.holders.IngredientViewHolder;
import com.github.edngulele.bankingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private Context context;
    private List<Ingredient> ingredientData;

    public IngredientAdapter(Context context, List<Ingredient> ingredientData) {
        this.context = context;
        this.ingredientData = ingredientData;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientData.get(position);
        holder.ingredientName.setText(ingredient.getIngredient());
        holder.amount.setText(ingredient.getDoseStr());
    }

    @Override
    public int getItemCount() {
        return ingredientData.size();
    }
}
