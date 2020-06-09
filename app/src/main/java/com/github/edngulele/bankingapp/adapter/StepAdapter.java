package com.github.edngulele.bankingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;
import com.github.edngulele.bankingapp.holders.StepViewHolder;
import com.github.edngulele.bankingapp.model.Step;
import com.github.edngulele.bankingapp.util.StepItemClickListener;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private Context context;
    private List<Step> stepData;
    private StepItemClickListener stepItemClickListener;


    public StepAdapter(Context context, List<Step> stepData, StepItemClickListener stepItemClickListener) {
        this.context = context;
        this.stepData = stepData;
        this.stepItemClickListener = stepItemClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = stepData.get(position);
        holder.stepName.setText(step.getShortDescription());

        String stepOrderStr = "";
        if (position > 0) {
            stepOrderStr = position + "";
        }
        holder.stepOrder.setText(stepOrderStr);

        holder.itemView.setOnClickListener(view -> {
            if (stepItemClickListener != null) {
                stepItemClickListener.onStepItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepData.size();
    }
}
