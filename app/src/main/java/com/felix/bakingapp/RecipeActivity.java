package com.felix.bakingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class RecipeActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private StepAdapter mAdapter;
    private ArrayList<Step> stepList;
    private ArrayList<Ingredient> ingredientList;

    @BindView(R.id.recycler_view_step)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }

        getIncomingIntent();
        setupStepRecyclerView();
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(RECIPE_KEY);
        stepList = recipe.getSteps();
        ingredientList = recipe.getIngredients();
        toolbar.setTitle(recipe.getName());
    }

    private void setupStepRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StepAdapter(this, stepList, mTwoPane);
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
}
