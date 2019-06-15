package com.felix.bakingapp.request;

import com.felix.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/baking.json")
    Call<List<Recipe>> getAllRecipes();
}
