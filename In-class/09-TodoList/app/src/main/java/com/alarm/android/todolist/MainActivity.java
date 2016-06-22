package com.alarm.android.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvTodos = (RecyclerView) findViewById(R.id.rvTodos);
        rvTodos.setLayoutManager(new LinearLayoutManager(this));

        TodosAdapter todosAdapter = new TodosAdapter();
        rvTodos.setAdapter(todosAdapter);

        ItemTouchHelper.Callback callback =
                new TodoItemTouchHelperCallback(todosAdapter, rvTodos);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvTodos);
    }
}
