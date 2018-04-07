package com.app.pking.bakingapp.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.adapter.StepsAdapter;
import com.app.pking.bakingapp.model.Ingredient;
import com.app.pking.bakingapp.model.Recipe;


public class DetailsFragment extends Fragment implements StepsAdapter.StepAdapterOnClickHandler {

    private static final String TAG = StepFragment.class.getSimpleName();

    private Recipe mRecipe;
    private Context context;

    private OnItemClickListener mCallback;

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public DetailsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView ingredients = rootView.findViewById(R.id.tv_recipe_ingredients);
        RecyclerView stepRecyclerView = rootView.findViewById(R.id.rv_recipe_steps);

        if (mRecipe != null) {
            for (Ingredient ingredient : mRecipe.getIngredientList()) {
                ingredients.append(ingredient.getIngredient() + " : " +
                        ingredient.getQuantity() + " " +
                        ingredient.getMeasure() + "\n");
            }

            StepsAdapter mAdapter = new StepsAdapter(mRecipe.getStepList(), context, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            stepRecyclerView.setLayoutManager(layoutManager);
            stepRecyclerView.setAdapter(mAdapter);


        } else {
            Log.v(TAG, "This fragment has a null step");
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement onClickListener");
        }
    }

    public void setmRecipe(Recipe recipe, Context context) {
        this.mRecipe = recipe;
        this.context = context;
    }

    @Override
    public void onClick(int position) {

        mCallback.onItemClickListener(position);
    }
}