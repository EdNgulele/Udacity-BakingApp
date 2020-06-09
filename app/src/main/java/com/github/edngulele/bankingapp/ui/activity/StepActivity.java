package com.github.edngulele.bankingapp.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.model.Step;
import com.github.edngulele.bankingapp.ui.fragment.StepFragment;
import com.github.edngulele.bankingapp.util.Constants;

public class StepActivity extends AppCompatActivity {

    private Fragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Log.d("Step", "Activity");


        //Preserve the state of the of the fragment
        if (savedInstanceState == null) {
            stepFragment = new StepFragment();
        } else {
            stepFragment = getSupportFragmentManager().getFragment(savedInstanceState, Constants.STEP_FRAGMENT_KEY);
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("Step", "not null - StepActivity");
            if (bundle.containsKey("Step")) {
                Step step1 = getIntent().getParcelableExtra("Step");
                String recipeName = getIntent().getStringExtra("recipe_name");

                bundle.putParcelable("Step", step1);
                stepFragment.setArguments(bundle);
            }
        } else {
            Log.d("Step", "is null - StepActivity");

        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_recipe_info, stepFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, Constants.STEP_FRAGMENT_KEY, stepFragment);
    }
}
