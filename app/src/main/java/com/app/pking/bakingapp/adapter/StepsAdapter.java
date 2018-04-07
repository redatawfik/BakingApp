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
import com.app.pking.bakingapp.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private final List<Step> mStepsList;

    private final StepAdapterOnClickHandler mClickHandler;
    Context mContext;

    public interface StepAdapterOnClickHandler {
        void onClick(int position);
    }

    public StepsAdapter(List<Step> stepList, Context context, StepAdapterOnClickHandler mClickHandler) {
        this.mStepsList = stepList;
        mContext = context;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final StepViewHolder holder, int position) {

        int n = mStepsList.get(position).getId();
        holder.stepShortDescription.setText(n + "." +
                mStepsList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepsList != null) {
            return mStepsList.size();
        } else {
            return 0;
        }

    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        final TextView stepShortDescription;

        StepViewHolder(View itemView) {
            super(itemView);
            stepShortDescription = itemView.findViewById(R.id.tv_step_short_description);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);

        }
    }
}
