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

public class Favoris extends AppCompatActivity {

    private Context context;
    private int Id;
    private String name;

    public Favoris (String name, int Id) {
        this.Id = Id;
        this.name = name;
    }
    public Favoris () {
        this.Id = -1;
        this.name = "Roger";
    }

    public String getNom () {
        return this.name;
    }

    public int getId () {return this.Id;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Favoris pote = new Favoris("Gerard", 42);
        final Favoris[] listFavoris = new Favoris[1];
        //listFavoris = listeFavoris(1);
        listFavoris[0] = pote;

        String[] listNom = new String[listFavoris.length];
        for (int i = 0; i < listNom.length; i++) {
            listNom[i] = listFavoris[i].getNom();
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

                Bundle b2 = getIntent().getExtras();
                int Id = b2.getInt("id");
                Intent intent = new Intent(Favoris.this, ProfilAmiFav.class);
                Bundle b = new Bundle();
                b.putString("idAmi",String.valueOf(listFavoris[position].getId()));
                b.putInt("idUser", Id);
                intent.putExtras(b);
                startActivity(intent);
            };

        });

    }

    public Favoris[] listeFavoris(int IdUtilisateur){
        ArrayList<Favoris> list = new ArrayList<Favoris>();
        DatabaseHelper myHelper = new DatabaseHelper(this);
        SQLiteDatabase database =  myHelper.open();
        String [] param = new String [1];
        param[0] = String.valueOf(IdUtilisateur);
        String requete = "SELECT DISTINCT U.ID FROM user U, relations R WHERE (U.ID = ID_to and R.ID_from = ? and R.EtatReq = 2) or ( U.ID = R.ID_from and R.ID_to = ? and R.EtatReq = 2);";
        Cursor cursor = database.rawQuery(requete, param);
        int IdAmi;
        Favoris favo;
        for (int i=0; i < cursor.getCount(); i++) {
            IdAmi = cursor.getInt(i);
            String [] param2 = new String [1];
            param2[0] = String.valueOf(IdAmi);
            Cursor cursor2 = database.rawQuery("SELECT Nom FROM user WHERE ID = ?;", param2);
            String nom = cursor2.getString(0);
            Cursor cursor3 = database.rawQuery("SELECT Prenom FROM user WHERE ID = ?;", param2);
            String prenom = cursor3.getString(0);
            String nomPrenom = nom+" "+prenom;
            favo = new Favoris(nomPrenom, IdAmi);
            list.add(favo);
            cursor2.close();
            cursor3.close();
        }
        cursor.close();

        Favoris[] tab = list.toArray(new Favoris[list.size()]);
        return tab;
    }
}
