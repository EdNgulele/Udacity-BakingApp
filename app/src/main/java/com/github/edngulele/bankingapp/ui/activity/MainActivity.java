package com.github.edngulele.bankingapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.adapter.RecipeMainAdapter;
import com.github.edngulele.bankingapp.model.Recipe;
import com.github.edngulele.bankingapp.network.RecipeService;
import com.github.edngulele.bankingapp.network.RetrofitClientManager;
import com.github.edngulele.bankingapp.util.TextFormatting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeMainAdapter recipeMainAdapter;
    private List<Recipe> recipesData;
    private Call<List<Recipe>> recipeCall;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        recyclerView = findViewById(R.id.rv_recipes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadRecipes();

    }

    private void loadRecipes() {
        RecipeService recipeService = RetrofitClientManager.getRetrofitClient(MainActivity.this);
        recipeCall = recipeService.getRecipes();

        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();

                recipeMainAdapter = new RecipeMainAdapter(context, recipes, clickedRecipe -> {
                    Recipe recipe = recipeMainAdapter.getRecipeAtIndex(clickedRecipe);

                    Intent intent = new Intent(context, InfoActivity.class);
                    intent.putExtra(getApplicationContext().getString(R.string.EXTRA_RECIPE), recipe);
                    startActivityForResult(intent, 1);
                });
                recyclerView.setAdapter(recipeMainAdapter);

                TextFormatting.setIdleResourceTo(true);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("JSON RESPONSE", "FAIL: " + t.getMessage());

            }
        });

    }
}
