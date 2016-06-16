package hu.ait.android.checklist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout listContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etItem = (EditText) findViewById(R.id.etItem);
        listContainer = (LinearLayout) findViewById(R.id.listContainer);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = etItem.getText().toString();

                View newRow = getLayoutInflater().inflate(R.layout.list_item, null, false);

                TextView tvItem = (TextView) newRow.findViewById(R.id.tvItem);
                tvItem.setText(newItem);

                listContainer.addView(newRow);
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listContainer.removeAllViews();
            }
        });
    }
}
