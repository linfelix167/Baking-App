package com.felix.bakingapp.ui;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.felix.bakingapp.R;
import com.felix.bakingapp.adapter.RecipeStepAdapter;
import com.felix.bakingapp.model.Ingredient;
import com.felix.bakingapp.model.Recipe;
import com.felix.bakingapp.model.Step;

import java.util.List;

import static com.felix.bakingapp.ui.RecipeListActivity.EXTRA_RECIPE;

public class RecipeStepDetailActivity extends AppCompatActivity implements RecipeStepAdapter.OnItemClickListener {

    public static final String EXTRA_STEP = "step";

    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private RecipeStepAdapter mAdapter;
    private Button mIngredientButton;
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;
    private String mIngredientDescription;

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
        mIngredientDescription = setupIngredientDescription(mIngredients);

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }

        generateStepList(mSteps);
        setupIngredientButton();
    }

    private void generateStepList(List<Step> steps) {
        mRecyclerView = findViewById(R.id.recipe_detail_recyclerview);
        mAdapter = new RecipeStepAdapter(this, steps);
        mAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupIngredientButton() {
        mIngredientButton = findViewById(R.id.show_ingredient_button);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(15);
        shape.setColor(getResources().getColor(R.color.colorPrimary));
        mIngredientButton.setBackground(shape);
    }

    private String setupIngredientDescription(List<Ingredient> ingredientList) {
        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : ingredientList) {
            sb.append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(" ")
                    .append(ingredient.getIngredient())
                    .append("\n");
        }

        return sb.toString().trim();
    }

    public void showIngredients(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingredients");
        builder.setMessage(mIngredientDescription);
        builder.create().show();
    }

    @Override
    public void onItemClick(int position) {
        Intent stepDetailIntent = new Intent(this, RecipeDetailActivity.class);
        Step step = mSteps.get(position);
        stepDetailIntent.putExtra(EXTRA_STEP, step);
        startActivity(stepDetailIntent);
    }
}
