package groupek.lsinf1225_projet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MessagerieActivity extends AppCompatActivity {

    private Context con;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie);
        Bundle b = getIntent().getExtras();
        con = this;
        ID = b.getInt("id");
        User me = new User(this, ID);
        String[] amis = me.listeAmis();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, amis);
        final ListView list = (ListView) findViewById(R.id.listView2);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String amiName = (String) list.getItemAtPosition(position);
                String prenom = amiName.split(" ")[0];
                String nom = amiName.split(" ")[1];
                Intent intent = new Intent(MessagerieActivity.this, ChatActivity.class);
                Bundle b = new Bundle();
                b.putString("nom", prenom);
                b.putInt("myID", ID); //Mettre mon ID
                b.putInt("hisID", User.find(prenom, nom, con));
                intent.putExtras(b);
                startActivity(intent);
            }

        });
    }
}
