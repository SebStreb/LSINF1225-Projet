package groupek.lsinf1225_projet;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class BrowserActivity extends AppCompatActivity {
    private int ID;
    private int other_user_id;
    private int index;
    private String nom;
    private String genre;
    private String couleur_cheveux;
    private String localite;
    private String age;
    private String couleur_yeux;
    private String inclinaison;
    private Bitmap bmpProfilePicture;
    private int mode;
    private UserTable[] users;
    private UserTable user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Bundle b = getIntent().getExtras();
        if(getIntent().hasExtra("id")) {
            this.ID = b.getInt("id");
        }
        else {
            this.ID = 1;
        }
        if(getIntent().hasExtra("index")) {
            this.index = b.getInt("index");
        }
        else {
            this.index = 0;
        }
        if(getIntent().hasExtra("mode")) {
            this.mode = b.getInt("mode");
        }
        else {
            this.mode = 1;
        }

        DatabaseHelper db = new DatabaseHelper(BrowserActivity.this);

        UserTable[] userTables = getOtherUsers(db);

        this.users = getOtherUsers(db);

        if(userTables.length == 0) {

            Toast.makeText(BrowserActivity.this, "Aucun utilisateur restant!", Toast.LENGTH_LONG).show();
            
            Intent myIntent = new Intent(BrowserActivity.this, MenuActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", this.getID());
            myIntent.putExtras(bundle);

            finish();
            startActivity(myIntent);
        }

        else {
            this.users = userTables;
            this.user = users[index];
            // PhotoTable[] userPhotos = db.getAllPhotos(user.getId());

            ImageView profilePicture = (ImageView) findViewById(R.id.profile_picture);
            // profilePicture.setImageBitmap(userPhotos[0]);

            TextView nom_text = (TextView) findViewById(R.id.nom);
            if (user.getCacherNom()) {
                nom_text.setText(user.getPrenom());
            } else {
                nom_text.setText(user.getPrenom() + " " + user.getNom());
            }

            TextView genre_text = (TextView) findViewById(R.id.genre);
            genre_text.setText(user.getGenre());

            TextView couleur_cheveux_text = (TextView) findViewById(R.id.couleur_cheveux);
            couleur_cheveux_text.setText(user.getCheveux());

            TextView localite_text = (TextView) findViewById(R.id.localite);
            if (user.getCacherAdresse()) {
                localite_text.setText(R.string.hiddenAdrr);
            } else {
                localite_text.setText(user.getLocalite());
            }

            TextView age_text = (TextView) findViewById(R.id.age);
            age_text.setText(user.getAge());

            TextView couleur_yeux_text = (TextView) findViewById(R.id.couleur_yeux);
            couleur_yeux_text.setText(user.getYeux());

            TextView inclinaison_text = (TextView) findViewById(R.id.inclinaison);
            inclinaison_text.setText(user.getInclinaison());

            ImageButton accept_button = (ImageButton) findViewById(R.id.accept_button);
            accept_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // ajouter l'utilisateur à la liste d'amis

                    DatabaseHelper db = new DatabaseHelper(BrowserActivity.this);
                    sendFriendRequest(db, ID, user.getId());

                    Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", ID);
                    bundle.putInt("index", 0);
                    bundle.putInt("mode", mode);

                    finish();
                    startActivity(myIntent);
                }
            });

            ImageButton decline_button = (ImageButton) findViewById(R.id.decline_button);
            decline_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // passer l'utilisateur et ne plus l'afficher pour la suite

                    DatabaseHelper db = new DatabaseHelper(BrowserActivity.this);
                    removeUserFromAnyList(db, ID, user.getId());
                    Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", ID);
                    bundle.putInt("index", 0);
                    bundle.putInt("mode", mode);

                    finish();
                    startActivity(myIntent);
                }
            });

            ImageButton previous_button = (ImageButton) findViewById(R.id.previous_button);
            previous_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // retourner à la personne précédente

                    Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", ID);
                    bundle.putInt("index", setPreviousIndex(index, users.length));
                    bundle.putInt("mode", mode);

                    myIntent.putExtras(bundle);

                    finish();
                    startActivity(myIntent);
                }
            });

            ImageButton next_button = (ImageButton) findViewById(R.id.next_button);
            next_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // passer la personne mais garder la possibilité de l'afficher par la suite

                    Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", ID);
                    bundle.putInt("index", setNextIndex(index, users.length));
                    bundle.putInt("mode", mode);

                    myIntent.putExtras(bundle);

                    finish();
                    startActivity(myIntent);
                }
            });
        }

    }

    public UserTable[] getOtherUsers(DatabaseHelper db) {
        ArrayList<UserTable> users = new ArrayList<UserTable>();

        // MODE BROWSER "ALL"
        if(BrowserActivity.this.getMode() == 1) {

            ArrayList<Integer> rel_ids = new ArrayList<Integer>();

            SQLiteDatabase myDB = db.open();
            Cursor cursor = myDB.rawQuery("SELECT ID_to FROM relations WHERE ID_from = " + BrowserActivity.this.getID(), null);
            if(cursor.moveToFirst()) {
                for(int i = 0 ; i < cursor.getCount() ; i++) {
                    rel_ids.add(cursor.getInt(0));
                    cursor.moveToNext();
                }
            }

            int i = 1;
            while(db.getUser(i) != null) {
                boolean condition = true;
                if(db.getUser(i).getId() == BrowserActivity.this.getID()) {
                    condition = false;
                }
                for(int j = 0 ; j < rel_ids.size() ; j++) {
                    if(db.getUser(i).getId() == rel_ids.get(j)) {
                        condition = false;
                    }
                }
                if(condition) {
                    users.add(db.getUser(i));
                }
                i++;
            }

            UserTable[] tab = new UserTable[users.size()];
            for(int j = 0 ; j < tab.length ; j++) {
                tab[j] = users.get(j);
            }
            return tab;
        }
        // MODE DEMANDES
        else if(BrowserActivity.this.getMode() == 2) {

            ArrayList<Integer> rel_ids = new ArrayList<Integer>();

            SQLiteDatabase myDB = db.open();
            Cursor cursor = myDB.rawQuery("SELECT ID_from FROM relations WHERE ID_to = " + BrowserActivity.this.getID() + " AND EtatReq = 0", null);
            if(cursor.moveToFirst()) {
                for(int i = 0 ; i < cursor.getCount() ; i++) {
                    rel_ids.add(cursor.getInt(0));
                    cursor.moveToNext();
                }
            }

            int i = 1;
            while(db.getUser(i) != null) {
                for(int j = 0 ; j < rel_ids.size() ; j++) {
                    if(db.getUser(i).getId() == rel_ids.get(j)) {
                        users.add(db.getUser(i));
                    }
                }
                i++;
            }

            UserTable[] tab = new UserTable[users.size()];
            for(int j = 0 ; j < tab.length ; j++) {
                tab[j] = users.get(j);
            }
            return tab;

        }



        return null;
    }

    /**
     * Classe et retourne la liste de personnes inscrites dans un ordre tel que celles avec qui
     * le user a le plus de similarités se retrouvent au début du tableau.
     * Ce degré de similitude va de 0 à 5 ; 0 signifiant aucune caractéristique en commun
     * et 5 toutes en commun.Les caractéristiques sont l'âge, la couleur de cheveux, la couleur
     * des yeux, le code postal et la langue maternelle
     * @param db la bdd utilisée
     * @param users_id un tableau avec l'id de tous les autres utilisateurs
     * @return un tableau avec les id des users (classés du plus similaire au moins similaire)
     */
    public ArrayList<Integer> sortUsers(DatabaseHelper db, int[] users_id) {
        SQLiteDatabase myDB = db.open();
        Hashtable<Integer, Integer> similarite = new Hashtable<Integer, Integer>();
        ArrayList<Integer> users = new ArrayList<Integer>();

        String sql = "SELECT sum((u1.Age=u2.Age)+(u1.Cheveux=u2.Cheveux)+(u1.Yeux=u2.Yeux)+" +
                "(u1.CodePost=u2.CodePost)+(u1.Langue=u2.Langue))/5.0" +
                "FROM user u1, user u2 WHERE u1.ID=? AND u2.ID=?";

        ArrayList<Integer> values = new ArrayList<Integer>();

        for(int i = 0 ; i < users_id.length ; i++) {
            String[] args = new String[2];
            args[0] = Integer.toString(this.ID);
            args[1] = Integer.toString(users_id[i]);
            Cursor cursor = myDB.rawQuery(sql, args);
            cursor.moveToFirst();

            similarite.put(users_id[i], cursor.getInt(0));
            values.add(cursor.getInt(0));

            cursor.close();
        }
        Collections.sort(values);

        for(int i = 0 ; i < values.size() ; i++) {
            for(int j = 0 ; j < similarite.size() ; j++) {
                if(similarite.get(users_id[j]) == values.get(i)) {
                    users.add(similarite.get(users_id[j]));
                }
            }
        }

        /*
        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList(similarite.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<?, Integer>>(){

            public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }});


        Iterator<Map.Entry<Integer, Integer>> it = list.iterator();

        int i = 0;
        while(it.hasNext() || i != users.size()) {
            Map.Entry<Integer, Integer> entry = it.next();
            users.add(entry.getKey());
            i++;
        }
        */

        return users;
    }

    /**
     * Insert une nouvelle entrée dans la table relations de la bdd signifiant que l'utilisateur actif
     * désire devenir ami avec l'utilisateur visé (demande d'ami envoyée)
     * @param db la bdd utilisée
     * @param user_id_1 id de l'utilisateur actif
     * @param user_id_2 id de l'utilisateur visé
     */
    public void sendFriendRequest(DatabaseHelper db, int user_id_1, int user_id_2) {
        SQLiteDatabase myDB = db.open();

        String[] params = new String[2];
        params[0] = Integer.toString(user_id_1);
        params[1] = Integer.toString(user_id_2);

        String[] params2 = new String[2];
        params2[0] = Integer.toString(user_id_2);
        params2[1] = Integer.toString(user_id_1);
        String sql1 = "SELECT EtatReq FROM relations WHERE ID_from = ? AND ID_to = ?";
        Cursor cursor = myDB.rawQuery(sql1, params2);
        if (cursor.moveToFirst()) {
            String sql2 = "INSERT INTO relations VALUES (?, ?, 1)";
            myDB.execSQL(sql2, params);
            sql2 = "UPDATE relations SET EtatReq = 1 WHERE ID_from = ? AND ID_to = ?";
            myDB.execSQL(sql2, params2);
        } else {
            String sql2 = "INSERT INTO relations VALUES (?, ?, 0)";
            myDB.execSQL(sql2, params);
        }
        cursor.close();
        myDB.close();
    }


    public void removeUserFromAnyList(DatabaseHelper db, int user_id_1, int user_id_2) {
        String[] params = new String[2];
        String[] params2 = new String[2];
        params[0] = params2[1] = Integer.toString(user_id_1);
        params[1] = params2[0] = Integer.toString(user_id_2);

        String sql1 = "INSERT INTO relations VALUES (?, ?, 3)";
        String sql2 = "INSERT INTO relations VALUES (?, ?, 3)";
        SQLiteDatabase myDB = db.open();

        myDB.execSQL(sql1, params);
        myDB.execSQL(sql2, params2);
    }


    public int setNextIndex(int currentIndex, int limit) {
        int nextIndex = 0;

        if(currentIndex < limit - 1) {
            nextIndex = currentIndex + 1;
        }

        return nextIndex;
    }

    public int setPreviousIndex(int currentIndex, int limit) {
        int previousIndex = limit - 1;

        if(currentIndex != 0) {
            previousIndex = currentIndex - 1;
        }

        return previousIndex;
    }

    public int getID() { return this.ID; }
    public int getOther_user_id() { return this.other_user_id; }
    public int getIndex() { return this.index; }
    public int getMode() { return this.mode; }

}
