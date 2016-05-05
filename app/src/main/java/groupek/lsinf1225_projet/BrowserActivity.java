package groupek.lsinf1225_projet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private int mode = 1;
    private UserTable[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        setTitle("Menu");

        Bundle b = getIntent().getExtras();
        if(getIntent().hasExtra("id")) {
            this.ID = b.getInt("id");
        }
        else {
            this.ID = 0;
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


        this.users = getOtherUsers(db);
        UserTable user = users[index];



        // DatabaseHelper myDB = new DatabaseHelper(this);

        //SQLiteDatabase db = myDB.open();

        /*
        db.execSQL("UPDATE user SET Genre='Female', Age='25 ans', Cheveux='Cheveux Noirs', Yeux='Yeux bleus'" +
                ", Localite='Ottignies', Inclinaison='Hétéro' WHERE ID='4'");
        */


        // setUser(myDB);


        ImageView profilePicture = (ImageView)findViewById(R.id.profile_picture);
        // profilePicture.setImageBitmap(bmpProfilePicture);

        TextView nom_text = (TextView)findViewById(R.id.nom);
        nom_text.setText(user.getPrenom() + " " + user.getNom());

        TextView genre_text = (TextView)findViewById(R.id.genre);
        genre_text.setText(user.getGenre());

        TextView couleur_cheveux_text = (TextView)findViewById(R.id.couleur_cheveux);
        couleur_cheveux_text.setText(user.getCheveux());

        TextView localite_text = (TextView)findViewById(R.id.localite);
        localite_text.setText(user.getLocalite());

        TextView age_text = (TextView)findViewById(R.id.age);
        age_text.setText(user.getAge());

        TextView couleur_yeux_text = (TextView)findViewById(R.id.couleur_yeux);
        couleur_yeux_text.setText(user.getYeux());

        TextView inclinaison_text = (TextView)findViewById(R.id.inclinaison);
        inclinaison_text.setText(user.getInclinaison());

        ImageButton accept_button = (ImageButton)findViewById(R.id.accept_button);
        accept_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // ajouter l'utilisateur à la liste d'amis
                /*
                addUserToFriendList(myDB, users.get(0), this.ID);

                Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                myIntent.putExtra("index", setNextIndex(index, users));
                */
                Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                finish();
                startActivity(myIntent);
            }
        });

        ImageButton decline_button = (ImageButton)findViewById(R.id.decline_button);
        decline_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // passer l'utilisateur et ne plus l'afficher pour la suite
                // removeUserFromUserList(myDB, users.get(0), this.ID);
                Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                finish();
                startActivity(myIntent);
            }
        });

        ImageButton previous_button = (ImageButton)findViewById(R.id.previous_button);
        previous_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // retourner à la personne précédente

                Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                myIntent.putExtra("index", setPreviousIndex(index, users.length));

                finish();
                startActivity(myIntent);
            }
        });

        ImageButton next_button = (ImageButton)findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // passer la personne mais garder la possibilité de l'afficher par la suite

                Intent myIntent = new Intent(BrowserActivity.this, BrowserActivity.class);

                myIntent.putExtra("index", setNextIndex(index, users.length));

                finish();
                startActivity(myIntent);
            }
        });

    }

    public UserTable[] getOtherUsers(DatabaseHelper db) {
        ArrayList<UserTable> users = new ArrayList<UserTable>();

        if(this.mode == 1) {
            int i = 1;
            while(db.getUser(i) != null) {
                if(i != this.ID) {
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
        else if(this.mode == 2) {
            int i = 1;
            while(db.getUser(i) != null) {
                if(i != this.ID) {
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



        return null;
    }

    /**
     * Retourne la liste des autres utilisateurs
     * @param db la bdd utilisée
     * @return un tableau avec les id de tous les autres utilisateurs présents dans la bdd
     */
    public int[] getUsers(DatabaseHelper db) {
        String[] id = new String[1];
        id[0] = Integer.toString(this.ID);
        String sql = "SELECT ID FROM user WHERE ID <> ?";
        SQLiteDatabase myDB = db.open();
        Cursor cursor = myDB.rawQuery(sql, id);
        Log.i("msg", cursor.getCount() + " getUsers");

        int[] tab = new int[cursor.getCount()];

        cursor.moveToFirst();
        for(int i = 0 ; i < tab.length ; i++) {
            tab[i] = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();

        // mode browse people
        if(mode == 1) {

        }
        // mode browse requetes
        else if(mode == 2){
            String[] params = new String[2];
            params[0] = Integer.toString(this.ID);
            params[1] = "0";
            String sql2 = "SELECT ID_from FROM relations WHERE ID_to = ? AND EtatReq = ?";
            Cursor cursor2 = myDB.rawQuery(sql2, params);
            cursor2.moveToFirst();

            int[] newTab = new int[cursor2.getCount()];

            for(int i = 0 ; i < newTab.length ; i++) {
                newTab[i] = cursor.getInt(0);
                cursor2.moveToNext();
            }
            cursor2.close();

            return newTab;
        }
        else {

        }

        return tab;
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
     * Retourne toutes les photos d'un utilisateur donné
     * @param db la bdd utilisée
     * @param user_id l'id de l'utilisateur donné
     * @return un tableau de byte ou chaque entrée contient un tableau de byte avec une des photos
     */
    public byte[][] getPhotosFromDB(DatabaseHelper db, int user_id) {
        String[] user = new String[1];
        user[0] = Integer.toString(user_id);
        SQLiteDatabase myDB = db.open();
        Cursor c = myDB.rawQuery("SELECT Photo FROM photos WHERE ID_user = ?", user);
        c.moveToFirst();
        byte[][] photos = new byte[1][c.getCount()];

        for(int i = 0 ; i < c.getCount() ; i++) {
            photos[i] = c.getBlob(i);
        }
        c.close();

        return photos;
    }

    public String[] getUserInfosFromDB(DatabaseHelper db, int user_id) {
        String[] tab = new String[8];
        String[] user = new String[1];
        user[0] = Integer.toString(user_id);

        SQLiteDatabase myDB = db.open();
        Cursor c = myDB.rawQuery("SELECT Nom, Prenom, Genre, Age, Cheveux, Yeux, Localite," +
                " Inclinaison FROM user WHERE ID = ?", user);
        c.moveToFirst();

        Log.i("msg", c.getCount()+" getUsersInfosFromDB");

        for(int i = 0 ; i < c.getCount() ; i++) {
            Log.i("truc", c.getString(i)+"trucbidule");
            tab[i] = Integer.toString(c.getInt(i));
        }
        c.close();

        return tab;
    }

    public void setUser(DatabaseHelper db) {
        int[] users_id = getUsers(db);
        final ArrayList<Integer> users = sortUsers(db, users_id);

        other_user_id = 1;//users.get(index);

        String[] user_infos = getUserInfosFromDB(db, other_user_id);

        nom = user_infos[1] + " " + user_infos[0];
        genre = user_infos[2];
        age = user_infos[3];
        couleur_cheveux = user_infos[4];
        couleur_yeux = user_infos[5];
        localite = user_infos[6];
        inclinaison = user_infos[7];

        byte[] byteProfilePicture = getPhotosFromDB(db, other_user_id)[0];

        bmpProfilePicture = BitmapFactory.decodeByteArray(byteProfilePicture, 0, byteProfilePicture.length);
    }



    /**
     * Insert une nouvelle entrée dans la table relations de la bdd signifiant que l'utilisateur actif
     * désire devenir ami avec l'utilisateur visé (demande d'ami envoyée)
     * @param db la bdd utilisée
     * @param user_id_1 id de l'utilisateur actif
     * @param user_id_2 id de l'utilisateur visé
     */
    public void addUserToFriendList(DatabaseHelper db, int user_id_1, int user_id_2) {
        String[] params = new String[2];
        params[0] = Integer.toString(user_id_1);
        params[1] = Integer.toString(user_id_2);

        String sql = "INSERT INTO relations VALUES (?, ?, 0)";
        SQLiteDatabase myDB = db.open();

        myDB.execSQL(sql, params);
    }


    public void removeUserFromUserList(DatabaseHelper db, int user_id_1, int user_id_2) {
        String[] params = new String[2];
        String[] params2 = new String[2];
        params[0] = params2[1] = Integer.toString(user_id_1);
        params[1] = params[0] = Integer.toString(user_id_2);

        String sql1 = "UPDATE relations SET EtatReq = 3 WHERE ID_from = ? AND ID_to = ?";
        String sql2 = "UPDATE relations SET EtatReq = 3 WHERE ID_from = ? AND ID_to = ?";
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

}
