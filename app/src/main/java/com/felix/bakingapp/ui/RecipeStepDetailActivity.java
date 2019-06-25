package com.felix.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.felix.bakingapp.R;
import com.felix.bakingapp.adapter.RecipeStepAdapter;
import com.felix.bakingapp.model.Ingredient;
import com.felix.bakingapp.model.Recipe;
import com.felix.bakingapp.model.Step;

import java.util.List;

import static com.felix.bakingapp.ui.RecipeListActivity.EXTRA_RECIPE;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private RecipeStepAdapter mAdapter;
    private Button mIngredientButton;
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Toolbar toolbar = findViewById(R.id.step_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Intent intent = getIntent();
        Recipe recipe = intent.getExtras().getParcelable(EXTRA_RECIPE);
        mSteps = recipe.getSteps();
        mIngredients = recipe.getIngredients();

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }

        generateStepList(mSteps);
    }

    private void generateStepList(List<Step> steps) {
        mRecyclerView = findViewById(R.id.recipe_detail_recyclerview);
        mAdapter = new RecipeStepAdapter(this, steps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupIngredientButton() {

    }
}
