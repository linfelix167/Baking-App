package com.felix.bakingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.felix.bakingapp.adapter.StepAdapter;
import com.felix.bakingapp.fragment.StepFragment;
import com.felix.bakingapp.model.Ingredient;
import com.felix.bakingapp.model.Recipe;
import com.felix.bakingapp.model.Step;
import com.felix.bakingapp.widget.RecipeWidget;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.felix.bakingapp.MainActivity.RECIPE_KEY;

public class RecipeActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private StepAdapter mAdapter;
    private ArrayList<Step> stepList;
    private ArrayList<Ingredient> ingredientList;
    private Recipe recipe;

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
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(StepFragment.ARG_ITEM_ID)) {
            recipe = intent.getParcelableExtra(StepFragment.ARG_ITEM_ID);
            stepList = recipe.getSteps();
            ingredientList = recipe.getIngredients();
            saveIngredients();
            getSupportActionBar().setTitle(recipe.getName());
            setupStepRecyclerView();
        }
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

    private void saveIngredients() {
        String widgetText = setupIngredientDescription(ingredientList);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.saved_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.key_string), widgetText);
        editor.putString(getString(R.string.recipe_key_string), recipe.getName());
        editor.apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.recipe_widget);
        ComponentName thisWidget = new ComponentName(this, RecipeWidget.class);

        remoteViews.setTextViewText(R.id.appwidget_ingredients_list, widgetText);
        remoteViews.setTextViewText(R.id.appwidget_text, recipe.getName());

        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
