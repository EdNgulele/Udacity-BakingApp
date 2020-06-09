package com.github.edngulele.bankingapp.network;

import android.content.Context;

import com.github.edngulele.bankingapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClientManager {

    private static RecipeService service;

    public static RecipeService getRetrofitClient(Context context) {

        if (service == null) {

            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service = retrofit.create(RecipeService.class);

        }

        return service;
    }
}
