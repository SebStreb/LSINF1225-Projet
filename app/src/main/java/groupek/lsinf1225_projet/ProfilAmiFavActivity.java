package groupek.lsinf1225_projet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfilAmiFavActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHelper dbb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ami_fav);
        Bundle b = getIntent().getExtras();
        final int IdAmi = b.getInt("idAmi");
        final int IdUser = b.getInt("idUser");
        String[] carac = dbb.caracteristique(IdAmi);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, carac);
        final ListView list = (ListView) findViewById(R.id.listView3);
        list.setAdapter(adapter);
        //byte [] picture = getPhotosFromDB(myHelper, Id)[0];
        //Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        //ImageView image = (ImageView) findViewById(R.id.imageView2);
        //image.setImageBitmap(bmp);
        Button bouttonFavori = (Button) findViewById(R.id.button3);
        bouttonFavori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                addFavoris(IdAmi, IdUser);
            }
        });
        Button bouttonCalendrier = (Button) findViewById(R.id.button4);
        bouttonCalendrier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(ProfilAmiFavActivity.this, MeetActivity.class);
                Bundle b2 = new Bundle();
                b2.putInt("hisID", IdAmi);
                b2.putInt("myID", IdUser);
                intent.putExtras(b2);
                startActivity(intent);
            }
        });

        Button bouttonDelete = (Button) findViewById(R.id.button5);
        bouttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                plusPoto(IdAmi, IdUser);
            }
        });


    }



    public byte[][] getPhotosFromDB(DatabaseHelper db, int user_id) { //TODO: Add photo in BDD to test
        String[] user = new String[1];
        user[0] = Integer.toString(user_id);
        SQLiteDatabase myDB = db.getReadableDatabase();
        Cursor c = myDB.rawQuery("SELECT Photo FROM photos WHERE ID_user = ?", user);
        c.moveToFirst();
        byte[][] photos = new byte[1][c.getCount()];
        for(int i = 0 ; i < c.getCount() ; i++) {
            photos[i] = c.getBlob(i);
        }
        c.close();
        return photos;
    }

    public void addFavoris (int IdAmi, int IdUser) {
        DatabaseHelper dbb = new DatabaseHelper(this);
        SQLiteDatabase database =  dbb.open();
        String req = "SELECT EtatReq FROM relations WHERE ID_from = ? and ID_to = ?";
        String[] param = {Integer.toString(IdUser) ,Integer.toString(IdAmi)};
        Cursor cursor = database.rawQuery(req, param);
        int etat = 0;
        if (cursor.moveToFirst()) {
            etat = cursor.getInt(0);
        }
        cursor.close();
        String requete = null;
        if (etat == 1) {
            requete = "UPDATE relations SET EtatReq = 2 WHERE ID_from = " + IdUser + " and ID_to = " + IdAmi + "";
            Toast.makeText(getApplicationContext(),
                    "Ajoute en favoris !", Toast.LENGTH_LONG).show();
        }
        if (etat == 2) {
            requete = "UPDATE relations SET EtatReq = 1 WHERE ID_from = " + IdUser + " and ID_to = " + IdAmi + "";
            Toast.makeText(getApplicationContext(),
                    "Retire des favoris !", Toast.LENGTH_LONG).show();
        }
        database.execSQL(requete);
        dbb.close();
    }

    public void plusPoto (int IdAmi, int IdUser){
        DatabaseHelper dbb = new DatabaseHelper(this);
        SQLiteDatabase database =  dbb.open();
        String requete = "UPDATE relations SET EtatReq = 3 WHERE ID_from = "+IdUser+" and ID_to = "+IdAmi+"";
        database.execSQL(requete);
        String requete2 = "UPDATE relations SET EtatReq = 3 WHERE ID_from = "+IdAmi+" and ID_to = "+IdUser+"";
        database.execSQL(requete2);
        dbb.close();
    }
}
