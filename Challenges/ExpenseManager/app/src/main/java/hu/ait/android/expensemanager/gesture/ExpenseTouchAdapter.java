package hu.ait.android.expensemanager.gesture;

import android.support.v7.widget.RecyclerView;

public interface ExpenseTouchAdapter {
    void onItemDismiss(int position, RecyclerView recyclerView);
 
    void onItemMove(int fromPosition, int toPosition);
}