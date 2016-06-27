package com.alarm.android.todolist.item;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.alarm.android.todolist.R;
import com.alarm.android.todolist.gesture.TodoTouchHelperAdapter;
import com.alarm.android.todolist.model.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodoViewHolder> implements TodoTouchHelperAdapter {
    List<Todo> todos = new ArrayList<>();

    public TodosAdapter() {
        todos = Todo.listAll(Todo.class);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        return new TodoViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.tvTodo.setText(todo.getTitle());
        holder.checkboxTodo.setChecked(todo.isDone());
        holder.checkboxTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Todo todo = todos.get(holder.getAdapterPosition());
                todo.setIsDone(isChecked);
                todo.save();
            }
        });

        holder.itemView.setTag(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        final Todo todoToBeRemoved = todos.get(position);
        todoToBeRemoved.delete();

        todos.remove(position);
        notifyItemRemoved(position);

        Snackbar snackbar = Snackbar
                .make(recyclerView, R.string.sb_item_deleted, Snackbar.LENGTH_LONG)
                .setAction(R.string.sb_button_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todoToBeRemoved.save();
                        todos.add(position, todoToBeRemoved);
                        notifyItemInserted(position);
                    }
                });
        snackbar.show();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todos, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todos, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addItem(Todo newTodo) {
        newTodo.save();

        todos.add(newTodo);
        notifyDataSetChanged();
    }
}
