package com.felix.bakingapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.felix.bakingapp.request.GetDataService;
import com.felix.bakingapp.R;
import com.felix.bakingapp.request.RetrofitClientInstance;
import com.felix.bakingapp.adapter.RecipeAdapter;
import com.felix.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        setupProgressDialog();
        parseJSONData();
    }

    private void parseJSONData() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Recipe>> call = service.getAllRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                progressDialog.dismiss();
                generateRecipeList(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RecipeListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(RecipeListActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void generateRecipeList(List<Recipe> recipeList) {
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new RecipeAdapter(this, recipeList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}