package com.github.edngulele.bankingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.holders.RecipeViewHolder;
import com.github.edngulele.bankingapp.model.Recipe;
import com.github.edngulele.bankingapp.util.RecipeItemClickListener;

import java.util.List;

public class RecipeMainAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeData;
    private RecipeItemClickListener recipeItemClickListener;

    public RecipeMainAdapter(Context context, List<Recipe> recipeData, RecipeItemClickListener recipeItemClickListener) {
        this.context = context;
        this.recipeData = recipeData;
        this.recipeItemClickListener = recipeItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_recipe_list, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {

        Recipe recipe = recipeData.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.servings.setText(recipe.getServings() + "");

        String recipeImage = recipeData.get(position).getImage();
        if (!recipeImage.isEmpty()) {
            Glide.with(context)
                    .load(recipeImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.image);
        }

        holder.itemView.setOnClickListener(view -> {
            if (recipeItemClickListener != null) {
                recipeItemClickListener.onRecipeItemClick(position);
            }
        });
    }

    public Recipe getRecipeAtIndex(int index) {
        return recipeData.get(index);
    }

    @Override
    public int getItemCount() {
        return recipeData.size();
    }
}
