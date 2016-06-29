package hu.ait.android.aitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.Iterator;

import hu.ait.android.aitchat.data.ForumMessage;

public class ForumActivity extends AppCompatActivity {
    private EditText etMessage;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        etMessage = (EditText) findViewById(R.id.etMessage);
        tvMessage = (TextView) findViewById(R.id.tvMessages);

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMessages();
            }
        });
    }

    private void sendMessage() {
        ForumMessage forumMessage =
                new ForumMessage(etMessage.getText().toString(),
                        (String) Backendless.UserService.CurrentUser().getProperty("userName")
                );

        Backendless.Persistence.save(forumMessage, new AsyncCallback<ForumMessage>() {
            @Override
            public void handleResponse(ForumMessage response) {
                Toast.makeText(ForumActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ForumActivity.this, "Error: " + fault.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshMessages() {
        Backendless.Persistence.of(ForumMessage.class).find(new BackendlessCallback<BackendlessCollection<ForumMessage>>() {
            @Override
            public void handleResponse(BackendlessCollection<ForumMessage> response) {
                Iterator<ForumMessage> messages = response.getCurrentPage().iterator();

                tvMessage.setText("");
                while (messages.hasNext()) {
                    ForumMessage message = messages.next();

                    tvMessage.append("<" + message.getSender() + "> " +
                            message.getText() + "\n");
                }
            }
        });
    }
}
