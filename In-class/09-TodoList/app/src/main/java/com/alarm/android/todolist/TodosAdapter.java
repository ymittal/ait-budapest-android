package com.alarm.android.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    List<Todo> todos = new ArrayList<>();

    public TodosAdapter() {
        for (int i = 0; i < 20; ++i) {
            todos.add(new Todo("Todo " + i, false));
        }
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        return new TodoViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.tvTodo.setText(todo.getTitle());
        holder.checkboxTodo.setChecked(todo.isDone());
        holder.itemView.setTag(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}
