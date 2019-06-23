package com.felix.bakingapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.felix.bakingapp.util.Constants.BASE_URL;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
