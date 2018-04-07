package com.app.pking.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RemoteViews;

import com.app.pking.bakingapp.IngredientListWidget;
import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.model.Ingredient;
import com.app.pking.bakingapp.model.Recipe;

import org.parceler.Parcels;


import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnItemClickListener {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String STEP_INDEX_KEY = "stepIndex";

    private int stepIndex;
    private Recipe mRecipe;

    private boolean mTwoPane;
    private StepFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(STEP_INDEX_KEY)) {
            stepIndex = savedInstanceState.getInt(STEP_INDEX_KEY);
        }
        setRecipe();

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setmRecipe(mRecipe, this);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.details_container, detailsFragment)
                .commit();


        if (findViewById(R.id.separating_view) != null) {
            mTwoPane = true;

            FragmentManager stepFragmentManager = getSupportFragmentManager();

            StepFragment fragment = new StepFragment();
            fragment.setmStep(mRecipe.getStepList().get(stepIndex));
            fragment.setContext(this);

            stepFragmentManager.beginTransaction()
                    .add(R.id.step_container, fragment)
                    .commit();

            Log.v(TAG, "Error in inflating ");

        } else {
            mTwoPane = false;

        }


        setIngredientWidget();
    }

    private void setIngredientWidget() {

        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_widget);
        ComponentName thisWidget = new ComponentName(context, IngredientListWidget.class);

        StringBuilder ingredientText = new StringBuilder();
        int count = 1;
        for (Ingredient ingredient : mRecipe.getIngredientList()) {
            ingredientText.append(count).append("- ").append(ingredient.getIngredient()).append(" : ").append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append("\n");
            count++;
        }

        remoteViews.setTextViewText(R.id.wd_ingredients_list, ingredientText.toString());
        remoteViews.setTextViewText(R.id.ingredients_text, mRecipe.getName() + " Ingredients : ");
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

    }

    private void setRecipe() {
        Intent intent = getIntent();
        mRecipe = Parcels.unwrap(intent.getParcelableExtra("recipe"));
    }


    @Override
    public void onItemClickListener(int position) {

        if (newFragment != null) {
            newFragment.releasePlayer();
        }

        if (mTwoPane) {
            stepIndex = position;
            newFragment = new StepFragment();
            newFragment.setmStep(mRecipe.getStepList().get(stepIndex));
            newFragment.setContext(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, newFragment)
                    .commit();


        } else {
            Intent intent = new Intent(this, StepActivity.class);

            intent.putExtra("step", Parcels.wrap(mRecipe.getStepList()));
            intent.putExtra("index", position);
            startActivity(intent);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (newFragment != null) {
            newFragment.releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (newFragment != null) {
            newFragment.releasePlayer();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_INDEX_KEY, stepIndex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
