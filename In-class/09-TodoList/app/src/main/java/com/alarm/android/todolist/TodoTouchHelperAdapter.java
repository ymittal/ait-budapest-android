package com.alarm.android.todolist;

import android.support.v7.widget.RecyclerView;

public interface TodoTouchHelperAdapter {
    void onItemDismiss(int position, RecyclerView recyclerView);
 
    void onItemMove(int fromPosition, int toPosition);
}