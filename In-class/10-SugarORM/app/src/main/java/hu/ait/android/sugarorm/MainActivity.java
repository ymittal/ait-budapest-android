package hu.ait.android.sugarorm;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import hu.ait.android.sugarorm.model.Note;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;
    private EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);
        etNote = (EditText) findViewById(R.id.etNote);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newNote = new Note(etNote.getText().toString(),
                        new Date(System.currentTimeMillis()).toString());
                newNote.save();
                Toast.makeText(MainActivity.this, R.string.toast_new_note, Toast.LENGTH_SHORT).show();

                etNote.getText().clear();
                refreshTextView();
            }
        });

        Button btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTextView();
            }
        });

        Button btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note.deleteAll(Note.class);
                refreshTextView();
                Toast.makeText(MainActivity.this, R.string.toast_del_all, Toast.LENGTH_SHORT).show();
            }
        });

        refreshTextView();
    }

    private void refreshTextView() {
        List<Note> notes = Note.listAll(Note.class);

        tvData.setText("");
        for (Note note : notes) {
            tvData.append(note.getDescription() + "\n" + note.getCreateDate() + "\n\n");
        }
    }
}
