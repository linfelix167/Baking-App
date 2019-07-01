package com.felix.bakingapp.request;

public class RecipeApi {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static RecipeService getService() {
        return RetrofitClient.getInstance(BASE_URL).create(RecipeService.class);
    }
}
