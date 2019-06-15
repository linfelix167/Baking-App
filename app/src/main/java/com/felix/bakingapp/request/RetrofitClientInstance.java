package com.felix.bakingapp.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.felix.bakingapp.utils.Constants.REQUEST_URL;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(REQUEST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
