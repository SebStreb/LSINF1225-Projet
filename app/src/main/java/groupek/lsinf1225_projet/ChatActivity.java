package groupek.lsinf1225_projet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private ChatAdapter adapter;
    private UserTable me;
    private UserTable other;
    private Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        Button sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);

        Bundle b = getIntent().getExtras();
        String value = b.getString("nom");
        DatabaseHelper db = new DatabaseHelper(this);
        me = db.getUser(b.getInt("myID"));
        other = db.getUser(b.getInt("hisID"));
        con = this;

        companionLabel.setText(value);
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                String date = DateFormat.getDateTimeInstance().format(new Date());
                if (TextUtils.isEmpty(messageText))
                    return;
                DatabaseHelper db = new DatabaseHelper(con);
                MessageTable message = new MessageTable(me.getId(), other.getId(), date, messageText);
                db.addMessage(message);
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessage(messageText);
                chatMessage.setDate(date);
                chatMessage.setMe(true);

                messageET.setText("");
                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory() {
        DatabaseHelper db = new DatabaseHelper(this);
        MessageTable[] messages = db.getAllMessage(me.getId(), other.getId());
        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);
        for(int i=0; i < messages.length; i++) {
            ChatMessage message = new ChatMessage();
            message.setMessage(messages[i].getContent());
            message.setDate(messages[i].getTime());
            message.setMe(messages[i].getID_from()==me.getId());
            displayMessage(message);
        }
    }

}
