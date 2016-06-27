package hu.ait.android.expensemanager.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hu.ait.android.expensemanager.R;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvDesc;
    public final TextView tvAmount;

    public ExpenseViewHolder(View itemView) {
        super(itemView);
        tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
        tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
    }

}
