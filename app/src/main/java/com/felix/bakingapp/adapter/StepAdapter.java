package com.felix.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.bakingapp.R;
import com.felix.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private ArrayList<Step> stepList;
    private OnStepClickListener mListener;

    public interface OnStepClickListener {
        void onStepClick(Step step);
    }

    public void setOnStepClickListener(OnStepClickListener listener) {
        mListener = listener;
    }

    public StepAdapter(ArrayList<Step> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.mStepTitle.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepList == null) {
            return 0;
        }
        return stepList.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        TextView mStepTitle;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onStepClick(stepList.get(position));
                    }
                }
            });
        }
    }
}
