package com.felix.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.bakingapp.adapter.RecipeAdapter;
import com.felix.bakingapp.fragment.StepFragment;
import com.felix.bakingapp.model.Recipe;
import com.felix.bakingapp.request.RecipeApi;
import com.felix.bakingapp.request.RecipeService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    public static final String RECIPE_KEY = "recipe";

    private ArrayList<Recipe> recipeList;
    private boolean isTablet = false;
    RecipeAdapter mAdapter;
    private RecipeService recipeService = RecipeApi.getService();

    @BindView(R.id.recycler_view_recipe) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recipeList = new ArrayList<>();

        mProgressBar.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            getApiData();
        } else {
            ArrayList<Parcelable> recipes = savedInstanceState.getParcelableArrayList(RECIPE_KEY);
            if (recipes != null) {
                for (int i = 0; i < recipes.size(); i++) {
                    recipeList.add((Recipe)recipes.get(i));
                }
            }
            initDisplay();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_KEY, recipeList);
    }

    private void getApiData() {
        mProgressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<Recipe>> call = recipeService.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipeList = response.body();

                if (!response.isSuccessful()) {
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }

                if (recipeList != null) {
                    initDisplay();
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initDisplay() {
        LinearLayoutManager linearLayoutManager = null;
        GridLayoutManager gridLayoutManager = null;

        if (!isTablet(this)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridLayoutManager = new GridLayoutManager(this, 2);
            }
        } else {
            isTablet = true;
            gridLayoutManager = new GridLayoutManager(this, 2);
        }

        if (!isTablet) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mRecyclerView.setLayoutManager(linearLayoutManager);
            } else {
                mRecyclerView.setLayoutManager(gridLayoutManager);
            }
        } else {
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(recipeList);
        mAdapter.setOnRecipeClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean isTablet(Context context) {
        boolean isTablet = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean isMobile = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (isTablet || isMobile);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(StepFragment.ARG_ITEM_ID, recipe);
        startActivity(intent);
    }
}
