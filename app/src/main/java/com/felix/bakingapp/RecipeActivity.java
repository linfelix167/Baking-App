package com.felix.bakingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.felix.bakingapp.adapter.StepAdapter;
import com.felix.bakingapp.model.Ingredient;
import com.felix.bakingapp.model.Recipe;
import com.felix.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.felix.bakingapp.MainActivity.RECIPE_KEY;

public class RecipeActivity extends AppCompatActivity implements StepAdapter.OnStepClickListener{

    private boolean mTwoPane;
    private StepAdapter mAdapter;
    private ArrayList<Step> stepList;
    private ArrayList<Ingredient> ingredientList;

    @BindView(R.id.recycler_view_step)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        getIncomingIntent();
        setupStepRecyclerView();
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(RECIPE_KEY);
        stepList = recipe.getSteps();
        ingredientList = recipe.getIngredients();
    }

    private void setupStepRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StepAdapter(stepList);
        mAdapter.setOnStepClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showIngredients(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingredients");
        builder.setMessage(setupIngredientDescription(ingredientList));
        builder.create().show();
    }

    private String setupIngredientDescription(ArrayList<Ingredient> ingredientList) {
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

    @Override
    public void onStepClick(Step step) {

    }
}
