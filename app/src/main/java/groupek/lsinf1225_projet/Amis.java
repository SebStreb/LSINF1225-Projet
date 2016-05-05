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

public class Amis extends AppCompatActivity {

    private Context context;
    private int Id;
    private String name;

    public Amis (String name, int Id) {
        this.Id = Id;
        this.name = name;
    }
    public Amis () {
        this.Id = -1;
        this.name = "Roger";
    }

    public String getNom () {
        return this.name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Amis[] listAmi;
        listAmi = listeAmis(1);
        String[] listNom = new String[listAmi.length];
        for (int i = 0; i < listNom.length; i++) {
            listNom[i] = listAmi[i].getNom();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listNom);
        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                 String test = (String) list.getItemAtPosition(position);
                Intent intent = new Intent(Amis.this, ProfilAmiFav.class);
                Bundle b = new Bundle();
                b.putString("nom", test); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            };

        });

    }

    public Amis[] listeAmis(int IdUtilisateur){
        ArrayList<Amis> list = new ArrayList<Amis>();
        DatabaseHelper myHelper = new DatabaseHelper(this);
        SQLiteDatabase database =  myHelper.open();
        String [] param = new String [1];
        param[0] = String.valueOf(IdUtilisateur);
        String requete = "SELECT DISTINCT U.ID FROM user U, relations R WHERE (U.ID = ID_to and R.ID_from = ? and R.EtatReq = ?) or ( U.ID = R.ID_from and R.ID_to = ? and R.EtatReq = ?);";
        Cursor cursor = database.rawQuery(requete, param);
        int IdAmi;
        Amis ami;
        for (int i=0; i < cursor.getCount(); i++) {
            IdAmi = cursor.getInt(i);
            String [] param2 = new String [1];
            param2[0] = String.valueOf(IdAmi);
            Cursor cursor2 = database.rawQuery("SELECT Nom FROM user WHERE ID = ?;", param2);
            String nom = cursor2.getString(0);
            Cursor cursor3 = database.rawQuery("SELECT Prenom FROM user WHERE ID = ?;", param2);
            String prenom = cursor3.getString(0);
            String nomPrenom = nom+" "+prenom;
            ami = new Amis(nomPrenom, IdAmi);
            list.add(ami);
        }
        Amis[] tab = list.toArray(new Amis[list.size()]);
        return tab;
    }
}
