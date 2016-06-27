package hu.ait.android.expensemanager.item;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.expensemanager.R;
import hu.ait.android.expensemanager.gesture.ExpenseTouchAdapter;
import hu.ait.android.expensemanager.model.Expense;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpenseViewHolder> implements ExpenseTouchAdapter {
    List<Expense> expenses = new ArrayList<>();

    public ExpensesAdapter() {
        expenses = Expense.listAll(Expense.class);
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.tvDesc.setText(expense.getDesc());
        holder.tvAmount.setText(Float.toString(expense.getAmount()));
        if (expense.getIsExpense())
            holder.imageview.setImageResource(R.drawable.down);
        else
            holder.imageview.setImageResource(R.drawable.up);
        holder.itemView.setTag(expense);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        final Expense expenseToBeRemoved = expenses.get(position);
        expenseToBeRemoved.delete();

        expenses.remove(position);
        notifyItemRemoved(position);

        Snackbar snackbar = Snackbar
                .make(recyclerView, R.string.sb_item_deleted, Snackbar.LENGTH_LONG)
                .setAction(R.string.sb_button_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expenseToBeRemoved.save();
                        expenses.add(position, expenseToBeRemoved);
                        notifyItemInserted(position);
                    }
                });
        snackbar.show();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(expenses, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(expenses, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addItem(Expense newExpense) {
        newExpense.save();

        expenses.add(newExpense);
        notifyDataSetChanged();
    }
}
