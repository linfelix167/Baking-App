package com.felix.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felix.bakingapp.R;
import com.felix.bakingapp.model.Recipe;
import com.felix.bakingapp.model.Step;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder> {

    private Context mContext;
    private List<Step> mSteps;

    public RecipeDetailAdapter(Context mContext, List<Step> mSteps) {
        this.mContext = mContext;
        this.mSteps = mSteps;
    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, viewGroup, false);
        return new RecipeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder recipeDetailViewHolder, int i) {
        Step currentStep = mSteps.get(i);
        recipeDetailViewHolder.bindTo(currentStep);
    }

    @Override
    public int getItemCount() {
        if (mSteps != null) {
            return mSteps.size();
        }
        return 0;
    }

    class RecipeDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleText;

        public RecipeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.tv_title);
        }

        void bindTo(Step currentStep) {
            mTitleText.setText(currentStep.getShortDescription());
        }
    }
}
