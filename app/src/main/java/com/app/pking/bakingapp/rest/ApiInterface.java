package com.app.pking.bakingapp.rest;


import com.app.pking.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {


    @GET("baking.json")
    Call<List<Recipe>> getRecipeList();

}