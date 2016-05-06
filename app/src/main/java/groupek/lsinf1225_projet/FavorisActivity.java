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

public class FavorisActivity extends AppCompatActivity {

    private Context context;
    private int Id;
    private String name;

    public FavorisActivity(String name, int Id) {
        this.Id = Id;
        this.name = name;
    }
    public FavorisActivity() {
        this.Id = -1;
        this.name = "Roger";
    }

    public String getNom () {
        return this.name;
    }

    public int getId () {return this.Id;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHelper dbb = new DatabaseHelper(this);
        Bundle b2 = getIntent().getExtras();
        final int Id = b2.getInt("id");
        final FavorisActivity[] listAmi = dbb.potoSearchFavoris(Id);
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
                Intent intent = new Intent(FavorisActivity.this, ProfilAmiFavActivity.class);
                Bundle b = new Bundle();
                b.putInt("idAmi",listAmi[position].getId());
                b.putInt("idUser", Id);
                intent.putExtras(b);
                startActivity(intent);
            };

        });

    }
}
