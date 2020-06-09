package com.github.edngulele.bankingapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.adapter.IngredientAdapter;
import com.github.edngulele.bankingapp.adapter.StepAdapter;
import com.github.edngulele.bankingapp.model.Recipe;
import com.github.edngulele.bankingapp.ui.activity.InfoActivity;
import com.github.edngulele.bankingapp.util.StepItemClickListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private Recipe recipe;
    private StepAdapter stepAdapter;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView recyclerViewIngredients;
    private RecyclerView recyclerViewStep;
    private TabHost tabHost;
    private StepItemClickListener stepItemClickListener;
    private Context context;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        context = requireContext();

        if (recipe == null) {
            recipe = ((InfoActivity) Objects.requireNonNull(getActivity())).getRecipe();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerViewIngredients = view.findViewById(R.id.rv_ingredients);
        recyclerViewIngredients.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(getContext());
        recyclerViewStep = view.findViewById(R.id.rv_steps);
        recyclerViewStep.setLayoutManager(layoutManagerSteps);

        tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Ingredients")
                .setIndicator("Ingredients")
                .setContent(R.id.tab_ingredients));
        tabHost.addTab(tabHost.newTabSpec("Steps")
                .setIndicator("Steps")
                .setContent(R.id.tab_step));


        ingredientAdapter = new IngredientAdapter(requireContext(), recipe.getIngredients());
        recyclerViewIngredients.setAdapter(ingredientAdapter);

        stepAdapter = new StepAdapter(requireContext(), recipe.getSteps(), (int itemClickedIndex) -> {
            stepItemClickListener.onStepItemClick(itemClickedIndex);

            Log.d("Step", recipe.getSteps().get(itemClickedIndex).getDescription());
        });

        recyclerViewStep.setAdapter(stepAdapter);


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            stepItemClickListener = (StepItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnStepClickListener");
        }
    }

//    private void initViews() {
////        IngredientAdapter ingredientsAdapter = new IngredientAdapter(context, recipe.getIngredients());
//        IngredientAdapter ingredientsAdapter = new IngredientAdapter(recipe.getIngredients());
//        recyclerViewIngredients.setAdapter(ingredientsAdapter);
//
////        stepAdapter = new StepAdapter(context, recipe.getSteps(), (int itemClickedIndex) -> {
////            stepItemClickListener.onStepItemClick(itemClickedIndex);
////        });
////        recyclerViewStep.setAdapter(stepAdapter);
//    }
}
