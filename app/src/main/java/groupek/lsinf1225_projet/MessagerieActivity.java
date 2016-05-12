package groupek.lsinf1225_projet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;

public class MessagerieActivity extends AppCompatActivity {

    private int ID;
    private Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagerie);

        /*    Get from Bundle    */
        Bundle b = getIntent().getExtras();
        this.con = this;
        this.ID = b.getInt("id");

        /*    Get friends    */
        DatabaseHelper db = new DatabaseHelper(this);
        final String[] amis = db.listeAmis(this.ID);

        /*    Show friends    */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, amis);
        final ListView list = (ListView) findViewById(R.id.listView2);
        list.setAdapter(adapter);

        /*    Set button on friends    */
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

        /*   Set search button    */
        final SearchView search = (SearchView) findViewById(R.id.searchView);
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String restrict = search.getQuery().toString();
                if (restrict != null) {
                    ArrayList<String> newAmis = new ArrayList<String>();
                    for (int i = 0; i < amis.length; i++) {
                        if (amis[i].contains(restrict))
                            newAmis.add(amis[i]);
                    }
                    String[] nAmis = newAmis.toArray(new String[newAmis.size()]);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(con,
                            android.R.layout.simple_list_item_1, android.R.id.text1, nAmis);
                    final ListView list = (ListView) findViewById(R.id.listView2);
                    list.setAdapter(adapter);
                }
            }
        });
    }
}
