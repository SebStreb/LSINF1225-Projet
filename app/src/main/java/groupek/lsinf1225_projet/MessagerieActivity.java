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
        con = this;
        Bundle b = getIntent().getExtras();
        ID = b.getInt("id");
        setContentView(R.layout.activity_messagerie);
        String[] amis = listeAmis();
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
                DatabaseHelper myHelper = new DatabaseHelper(con);
                SQLiteDatabase database =  myHelper.open();
                String[] param = {prenom, nom};
                String query = "SELECT ID FROM user WHERE Prenom = ? AND Nom = ?";
                Cursor cursor = database.rawQuery(query, param);
                b.putInt("hisID", cursor.getInt(0));
                cursor.close();
                intent.putExtras(b);
                startActivity(intent);
            }

        });
    }

    public String[] listeAmis() {
        DatabaseHelper myHelper = new DatabaseHelper(this);
        SQLiteDatabase database =  myHelper.open();
        String[] param = {Integer.toString(ID)};
        String query = "SELECT DISTINCT U.ID FROM user U, relations R WHERE (U.ID = ID_to and R.ID_from = ? and R.EtatReq = ?) or ( U.ID = R.ID_from and R.ID_to = ? and R.EtatReq = ?);";
        Cursor cursor = database.rawQuery(query, param);
        ArrayList<String> list = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            int IdAmi = cursor.getInt(0);
            String[] param2 = {Integer.toString(IdAmi)};
            Cursor cursor2 = database.rawQuery("SELECT Prenom, Nom FROM user WHERE ID = ?;", param2);
            String prenom = cursor2.getString(0);
            String nom = cursor2.getString(1);
            cursor2.close();
            String nomPrenom = prenom+" "+nom;
            list.add(nomPrenom);
            cursor.moveToNext();
        }
        return list.toArray(new String[list.size()]);
    }
}
