package groupek.lsinf1225_projet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends ActionBarActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private int myID;
    private int hisID;
    private Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = this;
        setContentView(R.layout.activity_chat);
        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);

        Bundle b = getIntent().getExtras();
        String value = b.getString("nom");
        myID = b.getInt("myID");
        hisID = b.getInt("hisID");

        companionLabel.setText(value);

        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                String date = DateFormat.getDateTimeInstance().format(new Date());
                if (TextUtils.isEmpty(messageText))
                    return;

                DatabaseHelper myHelper = new DatabaseHelper(con);
                SQLiteDatabase database =  myHelper.open();
                String query = "INSERT INTO messages(ID_from, ID_to, Content, Time) VALUES(" + myID + ", " + hisID
                        + ", " + messageText + ", " + date + ")";
                database.execSQL(query);

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
        chatHistory = new ArrayList<ChatMessage>();
        String[] param = {Integer.toString(myID), Integer.toString(hisID), Integer.toString(hisID), Integer.toString(myID)};
        DatabaseHelper myHelper = new DatabaseHelper(this);
        SQLiteDatabase database =  myHelper.open();
        String query = "SELECT ID_from, Content, Time FROM messages WHERE ID_from = ? AND ID_to = ? OR ID_from = ? AND ID_to = ?";
        Cursor cursor = database.rawQuery(query, param);
        while (!cursor.isAfterLast()) {
            ChatMessage m = new ChatMessage();
            m.setMe(cursor.getInt(0) == myID);
            m.setMessage(cursor.getString(1));
            m.setDate(cursor.getString(2));
            chatHistory.add(m);
            cursor.moveToNext();
        }
        cursor.close();

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);
        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

}
