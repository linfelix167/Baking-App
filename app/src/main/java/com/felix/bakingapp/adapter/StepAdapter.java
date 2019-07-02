package com.felix.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.bakingapp.R;
import com.felix.bakingapp.RecipeActivity;
import com.felix.bakingapp.StepActivity;
import com.felix.bakingapp.fragment.StepFragment;
import com.felix.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private ArrayList<Step> stepList;
    private boolean mTwoPane;
    private RecipeActivity mParentActivity;

    public StepAdapter(RecipeActivity parent,
                       ArrayList<Step> steps,
                       boolean twoPane) {
        mParentActivity = parent;
        stepList = steps;
        mTwoPane = twoPane;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelableArrayList(StepFragment.ARG_ITEM_ID, stepList);
                            arguments.putInt("id", position);
                            StepFragment fragment = new StepFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.step_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, StepActivity.class);
                            intent.putParcelableArrayListExtra(StepFragment.ARG_ITEM_ID, stepList);
                            intent.putExtra("id", position);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
