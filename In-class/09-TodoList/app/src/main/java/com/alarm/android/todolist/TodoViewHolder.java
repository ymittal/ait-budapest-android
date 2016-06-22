package com.alarm.android.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvTodo;
    public final CheckBox checkboxTodo;

    public TodoViewHolder(View itemView) {
        super(itemView);
        tvTodo = (TextView) itemView.findViewById(R.id.tvTodo);
        checkboxTodo = (CheckBox) itemView.findViewById(R.id.checkboxTodo);
    }

}
