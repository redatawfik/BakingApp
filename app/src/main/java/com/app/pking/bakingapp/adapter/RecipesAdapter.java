package com.app.pking.bakingapp.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.model.Recipe;

import java.util.List;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private final List<Recipe> mRecipeList;

    private final RecipeAdapterOnClickHandler mClickHandler;
    private final Context mContext;

    public interface RecipeAdapterOnClickHandler {
        void onClick(int position);
    }

    public RecipesAdapter(List<Recipe> recipeList, Context context, RecipeAdapterOnClickHandler mClickHandler) {
        this.mRecipeList = recipeList;
        mContext = context;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {

        holder.recipeNameTextView.setText(mRecipeList.get(position).getName());
        holder.servings.setText(mContext.getResources().getString(R.string.servings) + mRecipeList.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        if (mRecipeList != null) {
            return mRecipeList.size();
        } else {
            return 0;
        }

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final CardView cv;
        final TextView recipeNameTextView;
        final TextView servings;

        RecipeViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            recipeNameTextView = itemView.findViewById(R.id.tv_recipe_name);
            servings = itemView.findViewById(R.id.tv_servings);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);

        }
    }
}
