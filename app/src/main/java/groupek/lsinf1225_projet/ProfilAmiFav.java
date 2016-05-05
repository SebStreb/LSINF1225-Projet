package groupek.lsinf1225_projet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilAmiFav extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ami_fav);
        Bundle b = getIntent().getExtras();
        int IdAmi = Integer.parseInt(b.getString("idAmi"));
        int IdUser = b.getInt("idUser");
        String[] carac2 = caracteristique(1);
        String[] carac = new String [3];
        carac[0] ="Bob";
        carac[1] = "Grenier";
        carac[2] = "M";
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
                Bundle b = getIntent().getExtras();
                int IdAmi = Integer.parseInt(b.getString("idAmi"));
                int IdUser = b.getInt("idUser");
                addFavoris(IdAmi, IdUser);
            }
        });
        Button bouttonCalendrier = (Button) findViewById(R.id.button4);
        bouttonCalendrier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(ProfilAmiFav.this, MeetActivity.class);
                Bundle b = getIntent().getExtras();
                int IdAmi = Integer.parseInt(b.getString("idAmi"));
                int IdUser = b.getInt("idUser");
                Bundle b2 = new Bundle();
                b2.putInt("thisID", IdAmi);
                b2.putInt("myID", IdUser);
                intent.putExtras(b2);
                startActivity(intent);
            }
        });


    }



    public byte[][] getPhotosFromDB(DatabaseHelper db, int user_id) {
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
    public String[] caracteristique(int IdAmi){
        DatabaseHelper myHelper = new DatabaseHelper(ProfilAmiFav.this);
        SQLiteDatabase database =  myHelper.open();
        String [] param = new String [1];
        param[0] = String.valueOf(IdAmi);
        String requete = "SELECT Nom, Prenom, Genre, Age, Cheveux, Yeux, Rue, CodePost, Localite, Pays, Telephone, Inclinaison, Facebook, Langue, Cacher_nom, Cacher_adresse, Cacher_telephone,  Cacher_facebook FROM user WHERE (ID = ?);";
        Cursor cursor = database.rawQuery(requete, param);
        String[] caracte = new String [18];
        for (int i=0; i < cursor.getCount(); i++) {
            caracte[i] = cursor.getString(i);
        }
        cursor.close();
        return caracte;
    }

    public void addFavoris (int IdAmi, int IdUser) {
        DatabaseHelper myHelper = new DatabaseHelper(ProfilAmiFav.this);
        SQLiteDatabase database =  myHelper.open();
        String [] param = new String [1];
        param[0] = String.valueOf(IdAmi);
        String requete = "UPDATE relations SET EtatReq = 1 WHERE ID_from = "+IdUser+" and ID_to = "+IdAmi+";";
        database.execSQL(requete);
        Toast.makeText(getApplicationContext(),
                "Ajoute en favoris !", Toast.LENGTH_LONG).show();
    }
}
