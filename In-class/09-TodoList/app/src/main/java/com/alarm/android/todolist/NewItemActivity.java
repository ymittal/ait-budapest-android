package com.alarm.android.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewItemActivity extends AppCompatActivity {
    public static final String NEW_TODO_ITEM = "NEW_TODO_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);

        final EditText etTodo = (EditText) findViewById(R.id.etTodo);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveIntent = new Intent();
                saveIntent.putExtra(NEW_TODO_ITEM, etTodo.getText().toString());
                setResult(Activity.RESULT_OK, saveIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent failIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, failIntent);
        finish();
    }
}
