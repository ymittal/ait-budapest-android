package hu.ait.android.expensemanager;

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

import hu.ait.android.expensemanager.gesture.ExpenseItemTouchCallback;
import hu.ait.android.expensemanager.item.ExpensesAdapter;
import hu.ait.android.expensemanager.model.Expense;

public class ExpensesActivity extends AppCompatActivity {
    private int REQUEST_CODE = 1;
    private ExpensesAdapter expensesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        initExpenses();

        FloatingActionButton actionbutton = (FloatingActionButton) findViewById(R.id.actionbutton);
        actionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ExpensesActivity.this, NewExpenseDialog.class), REQUEST_CODE);
            }
        });
    }

    private void initExpenses() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expensesAdapter = new ExpensesAdapter();
        recyclerView.setAdapter(expensesAdapter);

        ItemTouchHelper.Callback callback =
                new ExpenseItemTouchCallback(expensesAdapter, recyclerView);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Expense newExpense = new Expense(
                        data.getStringExtra(NewExpenseDialog.EXPENSE_DESC),
                        data.getFloatExtra(NewExpenseDialog.EXPENSE_AMOUNT, 0.0f),
                        data.getBooleanExtra(NewExpenseDialog.EXPENSE_TOGGLE, true)
                );
                expensesAdapter.addItem(newExpense);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(ExpensesActivity.this, R.string.toast_add_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
