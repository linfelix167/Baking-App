package com.felix.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.felix.bakingapp.R;
import com.felix.bakingapp.model.Step;

import static com.felix.bakingapp.ui.RecipeStepDetailActivity.EXTRA_STEP;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        Step step = intent.getExtras().getParcelable(EXTRA_STEP);
    }
}
