package com.github.edngulele.bankingapp.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.github.edngulele.bankingapp.BuildConfig;
import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.model.Ingredient;
import com.github.edngulele.bankingapp.model.Recipe;
import com.github.edngulele.bankingapp.model.Step;
import com.github.edngulele.bankingapp.ui.fragment.StepFragment;
import com.github.edngulele.bankingapp.ui.widget.RecipeWidgetProvider;
import com.github.edngulele.bankingapp.util.Constants;
import com.github.edngulele.bankingapp.util.StepItemClickListener;

public class InfoActivity extends AppCompatActivity implements StepItemClickListener {

    private Recipe recipe;
    private SharedPreferences sharedPreferences;
    private boolean isTablet;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("Ingredient", "is not null");
            if (bundle.containsKey(this.getString(R.string.EXTRA_RECIPE))) {
                recipe = getIntent().getParcelableExtra(this.getString(R.string.EXTRA_RECIPE));
            }
        } else {
            Log.d("Ingredient", "is  null");

        }

        setTitle(recipe.getImage());
        setContentView(R.layout.activity_info);
        linearLayout = findViewById(R.id.layout_recipe_info);
        isTablet = (findViewById(R.id.container_info) != null);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public void onStepItemClick(int clickedItemIndex) {
        Step step = recipe.getSteps().get(clickedItemIndex);
        Log.d("Step", step.getShortDescription());


        if (isTablet) {
            StepFragment stepFragment = new StepFragment();
            Bundle extras = new Bundle();
            extras.putParcelable("Step", step);
            stepFragment.setArguments(extras);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_detail, stepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra("Step", step);
            intent.putExtra("recipe_name", recipe.getName());
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        //Add persistence
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if ((sharedPreferences.getInt("ID", -1) == recipe.getId())) {
            menu.findItem(R.id.action_add_widget).setIcon(R.drawable.ic_favorite);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_widget) {
            boolean isWidget = (sharedPreferences.getInt(Constants.PREFERENCES_ID, -1) == recipe.getId());

            //remove widget
            if (isWidget) {
                sharedPreferences.edit()
                        .remove(Constants.PREFERENCES_ID)
                        .remove(Constants.PREFERENCES_WIDGET_TITLE)
                        .remove(Constants.PREFERENCES_WIDGET_CONTENT)
                        .apply();
                item.setIcon(R.drawable.ic_favorite_border);
                Toast.makeText(this, "Widget Removed", Toast.LENGTH_LONG).show();
            } else {
                // add widget
                sharedPreferences.edit()
                        .putInt(Constants.PREFERENCES_ID, recipe.getId())
                        .putString(Constants.PREFERENCES_WIDGET_TITLE, recipe.getName())
                        .putString(Constants.PREFERENCES_WIDGET_CONTENT, getIngredientString())
                        .apply();

                item.setIcon(R.drawable.ic_favorite);
                Toast.makeText(this, "Widget Added", Toast.LENGTH_LONG).show();
            }

            //Update Widget
            ComponentName componentName = new ComponentName(this, RecipeWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] ids = appWidgetManager.getAppWidgetIds(componentName);
            RecipeWidgetProvider recipeWidgetProvider = new RecipeWidgetProvider();
            recipeWidgetProvider.onUpdate(this, appWidgetManager, ids);
        }


        return super.onOptionsItemSelected(item);
    }

    private String getIngredientString() {
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient : recipe.getIngredients()) {
            result.append(ingredient.getDoseStr()).append(" ").append(ingredient.getIngredient()).append("\n");
        }
        return result.toString();
    }
}
