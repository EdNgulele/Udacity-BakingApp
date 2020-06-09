package com.github.edngulele.bankingapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.bankingapp.R;

public class StepViewHolder extends RecyclerView.ViewHolder {

    public TextView stepOrder;
    public TextView stepName;

    public StepViewHolder(@NonNull View itemView) {
        super(itemView);

        stepName = itemView.findViewById(R.id.tv_step_name);
        stepOrder = itemView.findViewById(R.id.tv_step_order);
    }
}
