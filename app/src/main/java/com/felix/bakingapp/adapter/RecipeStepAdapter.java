package com.felix.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.felix.bakingapp.R;
import com.felix.bakingapp.model.Step;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeDetailViewHolder> {

    private Context mContext;
    private List<Step> mSteps;
    private OnItemClickListener mListener;

    public RecipeStepAdapter(Context mContext, List<Step> mSteps) {
        this.mContext = mContext;
        this.mSteps = mSteps;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        void bindTo(Step currentStep) {
            mTitleText.setText(currentStep.getShortDescription());
        }
    }
}
