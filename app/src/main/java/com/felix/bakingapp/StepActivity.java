package com.felix.bakingapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.felix.bakingapp.fragment.StepFragment;
import com.felix.bakingapp.model.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {

    private ArrayList<Step> stepList;
    private int position;
    private StepFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setTitle("");
            }
            getIncomingIntent();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (this.getSupportActionBar() != null) {
                this.getSupportActionBar().hide();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (this.getSupportActionBar() != null) {
                this.getSupportActionBar().show();
            }
        }
    }

    private void getIncomingIntent() {
        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        if (intent.hasExtra(StepFragment.ARG_ITEM_ID)) {
            stepList = getIntent().getParcelableArrayListExtra(StepFragment.ARG_ITEM_ID);
            position = getIntent().getIntExtra("id", 0);

            bundle.putParcelableArrayList(StepFragment.ARG_ITEM_ID, stepList);
            bundle.putInt("id", position);

            fragment = new StepFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }

    public void prevStep(View view) {
        if (position > 0) {
            position--;
            fragment.setupView(position);
        }
    }

    public void nextStep(View view) {
        if (position < stepList.size() - 1) {
            position++;
            fragment.setupView(position);
        }
    }
}
