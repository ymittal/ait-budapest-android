package com.alarm.android.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.alarm.android.todolist.gesture.TodoItemTouchHelperCallback;
import com.alarm.android.todolist.item.TodosAdapter;
import com.alarm.android.todolist.model.Todo;

public class MainActivity extends AppCompatActivity {
    private int REQUEST_CODE = 1;

    TodosAdapter todosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTodoList();

        FloatingActionButton actionbutton = (FloatingActionButton) findViewById(R.id.actionbutton);
        actionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, NewItemActivity.class), REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Todo newTodo = new Todo(data.getStringExtra(NewItemActivity.NEW_TODO_ITEM), false);
                todosAdapter.addItem(newTodo);
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, R.string.toast_add_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initTodoList() {
        RecyclerView rvTodos = (RecyclerView) findViewById(R.id.rvTodos);
        rvTodos.setLayoutManager(new LinearLayoutManager(this));

        todosAdapter = new TodosAdapter();
        rvTodos.setAdapter(todosAdapter);

        ItemTouchHelper.Callback callback =
                new TodoItemTouchHelperCallback(todosAdapter, rvTodos);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvTodos);
    }
}
