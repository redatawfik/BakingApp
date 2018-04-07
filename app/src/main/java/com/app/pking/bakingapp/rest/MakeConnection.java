package com.app.pking.bakingapp.rest;


import android.content.Context;
import android.widget.Toast;

import com.app.pking.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeConnection {

    static List<Recipe> recipeList;

    public static List<Recipe> getResponseRetrofit(final Context context) throws Throwable {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Recipe>> call = apiService.getRecipeList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {


            }
        });

        return recipeList;

    }

}
