package groupek.lsinf1225_projet;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Browser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        setTitle("Menu");

        /*
        final DatabaseHelper myDB = new DatabaseHelper(this.getApplicationContext());
        SQLiteDatabase db = myDB.getReadableDatabase();

        int this_user_id = 0;
        int[] users_id = getUsers(myDB, this_user_id);

        int[] users = sortUsers(myDB, users_id, this_user_id);

        String prenom_user = "Antoine";

        Cursor c1 = db.rawQuery("SELECT ID FROM user WHERE Prenom = " + prenom_user, null);

        c1.moveToFirst();
        int ID_user = c1.getInt(0);

        byte[] byteProfilePicture = getPhotosFromDB(myDB, ID_user)[0];
        Bitmap bmp = BitmapFactory.decodeByteArray(byteProfilePicture, 0, byteProfilePicture.length);


        ImageView profilePicture = (ImageView)findViewById(R.id.profile_picture);
        profilePicture.setImageBitmap(bmp);
        */

        ImageButton acceptButton = (ImageButton)findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // ajouter la personne à sa liste d'amis
                /*
                SQLiteDatabase db = myDB.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put();
                */

                Intent myIntent = new Intent(Browser.this, Browser.class);

                // myIntent.putExtra("profile", new Gson().toJson(profile));

                finish();
                startActivity(myIntent);
            }
        });

        ImageButton declineButton = (ImageButton)findViewById(R.id.decline_button);
        declineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // passer la personne et ne plus l'afficher pour la suite

                Intent myIntent = new Intent(Browser.this, Browser.class);

                // myIntent.putExtra("profile", new Gson().toJson(profile));

                finish();
                startActivity(myIntent);
            }
        });

        ImageButton previousButton = (ImageButton)findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // retourner à la personne précédente

                Intent myIntent = new Intent(Browser.this, Browser.class);

                // myIntent.putExtra("profile", new Gson().toJson(profile));

                finish();
                startActivity(myIntent);
            }
        });

        ImageButton nextButton = (ImageButton)findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // passer la personne mais garder la possibilité de l'afficher par la suite

                Intent myIntent = new Intent(Browser.this, Browser.class);

                // myIntent.putExtra("profile", new Gson().toJson(profile));

                finish();
                startActivity(myIntent);
            }
        });

    }

    /**
     * Retourne la liste des autres utilisateurs
     * @param db la bdd utilisée
     * @return un tableau avec les id de tous les autres utilisateurs présents dans la bdd
     */
    public int[] getUsers(DatabaseHelper db, int id) {
        String sql = "SELECT ID FROM user WHERE ID <> " + id;
        SQLiteDatabase myDB = db.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(sql, null);

        int[] tab = new int[cursor.getCount()];

        for(int i = 0 ; i < tab.length ; i++) {
            tab[i] = cursor.getInt(i);
        }

        return tab;
    }

    /**
     * Classe et retourne la liste de personnes inscrites dans un ordre tel que celles avec qui le user a le plus de similarités se retrouvent au début du tableau
     * @param db la bdd utilisée
     * @param users_id un tableau avec l'id de tous les autres utilisateurs
     * @param this_user_id l'id du user courant
     * @return un tableau avec les id des users (classés du plus similaire au moins similaire)
     */
    public int[] sortUsers(DatabaseHelper db, int[] users_id, int this_user_id) {
        SQLiteDatabase myDB = db.getReadableDatabase();
        Hashtable<Integer, Integer> similarite = new Hashtable<Integer, Integer>();
        int[] tab = new int[users_id.length];


        for(int i = 0 ; i < users_id.length ; i++) {
            Cursor cursor = myDB.rawQuery(similaritySQLRequest(this_user_id, users_id[i]), null);
            cursor.moveToFirst();

            similarite.put(users_id[i], cursor.getInt(0));
        }

        ArrayList<Map.Entry<?, Integer>> list = new ArrayList(similarite.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<?, Integer>>(){

            public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }});

        Iterator<Map.Entry<Integer, Integer>> it = similarite.entrySet().iterator();

        int i = 0;
        while(it.hasNext() || i == tab.length) {
            Map.Entry<Integer, Integer> entry = it.next();
            tab[i] = entry.getKey();
            i++;
        }

        return tab;
    }

    /**
     * Retourne toutes les photos d'un utilisateur donné
     * @param db la bdd utilisée
     * @param ID l'id de l'utilisateur
     * @return un tableau de byte ou chaque entrée contient un tableau de byte avec une des photos
     */
    public byte[][] getPhotosFromDB(DatabaseHelper db, int ID) {
        SQLiteDatabase myDB = db.getReadableDatabase();
        Cursor c = myDB.rawQuery("SELECT Photo FROM photos WHERE ID_user = " + ID, null);
        c.moveToFirst();
        byte[][] photos = new byte[1][c.getCount()];

        for(int i = 0 ; i < c.getCount() ; i++) {
            photos[i] = c.getBlob(i);
        }
        c.close();

        return photos;
    }

    /**
     * Retourne la requête sql calculant le degré de similitude entre 2 utilisateurs. Ce degré de
     * similitude va de 0 à 5, 0 signifiant aucune caractéristique en commun et 5 toutes en commun.
     * Les caractéristiques sont l'âge, la couleur de cheveux, la couleur des yeux, le code postal
     * et la langue maternelle
     * @param ID_user_1 l'id de l'utilisateur 1
     * @param ID_user_2 l'id de l'utilisateur 2
     * @return une string contenant la requête sql
     */
    public String similaritySQLRequest(int ID_user_1, int ID_user_2) {
        return "SELECT sum((u1.Age=u2.Age)+(u1.Cheveux=u2.Cheveux)+(u1.Yeux=u2.Yeux)" +
                "+(u1.CodePost=u2.CodePost)+(u1.Langue=u2.Langue))/5.0 " +
                "FROM user u1, user u2 WHERE u1.ID=ID_user_1 AND u2.ID=ID_user_2";
    }

    public void addUserToFriendList(int user_id) {

    }

    public void removeUserFromUserList(int user_id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.browser_menu_edit):
                break;
            case (R.id.browser_menu_add):
                break;
            case (R.id.browser_menu_delete):
                break;

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
