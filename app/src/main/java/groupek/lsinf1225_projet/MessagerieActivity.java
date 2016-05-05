package groupek.lsinf1225_projet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessagerieActivity extends AppCompatActivity {

    private int ID;
    private Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie);
        Bundle b = getIntent().getExtras();
        this.con = this;
        this.ID = b.getInt("id");
        DatabaseHelper db = new DatabaseHelper(this);
        String[] amis = db.listeAmis(this.ID);
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
                DatabaseHelper db = new DatabaseHelper(con);
                UserTable other = db.getUser(prenom, nom);
                Intent intent = new Intent(MessagerieActivity.this, ChatActivity.class);
                Bundle b = new Bundle();
                b.putString("nom", prenom);
                b.putInt("myID", ID);
                b.putInt("hisID", other.getId());
                intent.putExtras(b);
                startActivity(intent);
            }

        });
    }
}
