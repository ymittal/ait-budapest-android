package hu.ait.android.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NewExpenseDialog extends AppCompatActivity {
    public static final String EXPENSE_DESC = "EXPENSE_DESC";
    public static final String EXPENSE_AMOUNT = "EXPENSE_AMOUNT";
    public static final String EXPENSE_TOGGLE = "EXPENSE_TOGGLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        this.setTitle(getString(R.string.activity_new_expense_title));

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);

        final EditText etExpense = (EditText) findViewById(R.id.etExpense);
        final EditText etAmount = (EditText) findViewById(R.id.etAmount);
        final ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        toggle.setChecked(true); // default: Expense

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveIntent = new Intent();
                saveIntent.putExtra(EXPENSE_DESC, etExpense.getText().toString())
                        .putExtra(EXPENSE_AMOUNT, Float.parseFloat(etAmount.getText().toString()))
                        .putExtra(EXPENSE_TOGGLE, toggle.isChecked());
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
