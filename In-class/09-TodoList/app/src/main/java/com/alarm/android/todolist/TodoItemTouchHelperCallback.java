package com.alarm.android.todolist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class TodoItemTouchHelperCallback extends
        ItemTouchHelper.Callback {
 
    private final TodoTouchHelperAdapter mAdapter;
    private RecyclerView recyclerView;
 
    public TodoItemTouchHelperCallback(TodoTouchHelperAdapter adapter, RecyclerView recyclerView) {
        mAdapter = adapter;
        this.recyclerView = recyclerView;
    }
 
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
 
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
 
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }
 
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }
 
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), recyclerView);
    }
}