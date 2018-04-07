package com.app.pking.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.adapter.RecipesAdapter;
import com.app.pking.bakingapp.idlingResource.EspressoIdlingResource;
import com.app.pking.bakingapp.model.Recipe;
import com.app.pking.bakingapp.rest.ApiClient;
import com.app.pking.bakingapp.rest.ApiInterface;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeAdapterOnClickHandler {


    private static final String TAG = MainActivity.class.getSimpleName();
    Context context;

    private final static String RECIPE_LIST_KEY = "recipeListKey";


    @BindView(R.id.progress_wheel)
    ProgressWheel wheel;
    @BindView(R.id.rv_recipe)
    RecyclerView mRecyclerView;
    RecipesAdapter mRecipesAdapter;
    ArrayList<Recipe> mRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        setWheelVisible();

        mRecipeList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecipesAdapter = new RecipesAdapter(mRecipeList, this, (RecipesAdapter.RecipeAdapterOnClickHandler) context);
        mRecyclerView.setAdapter(mRecipesAdapter);

        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList("recipeList");
            mRecipesAdapter.notifyDataSetChanged();
        }else {
            getResponseRetrofit();
        }




    }

    private void setWheelVisible() {
        wheel.setVisibility(View.VISIBLE);
    }

    private void setWheelInVisible() {
        wheel.setVisibility(View.INVISIBLE);
    }


    private void getResponseRetrofit() {

        EspressoIdlingResource.increment(); // stops Espresso tests from going forward

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Recipe>> call = apiService.getRecipeList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipeList.addAll(response.body());
                setWheelInVisible();
                mRecipesAdapter.notifyDataSetChanged();

                Toast.makeText(context, "Success" + mRecipeList.size(), Toast.LENGTH_LONG).show();

                EspressoIdlingResource.decrement(); // Tells Espresso test to resume

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                setWheelInVisible();
                Toast.makeText(context, "No Internet Connection!", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra("recipe", Parcels.wrap(mRecipeList.get(position)));
        startActivity(intent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipeList",mRecipeList);
    }
}
