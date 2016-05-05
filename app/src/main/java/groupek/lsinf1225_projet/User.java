package groupek.lsinf1225_projet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.util.*;

public class User {

    private Context context;
    private int id;
    private Hashtable<String,Object> caracteristiques;
    private static final String[] carac = {"Nom","Prenom","Genre","Age","Cheveux","Yeux","Rue","CodePost","Localite","Pays","Telephone","Inclinaison","Facebook","Langue(s)"};


    public User(Context con, int id){
        this.id = id;
        this.context = con;
        caracteristiques = new Hashtable<String,Object>();
        String[] data = searchDatabase();
        for (int i = 0; i < carac.length; i++)
            setOpt(carac[i], data[i]);
    }

    public User(Context con, String prenom, String nom) {
        DatabaseHelper myHelper = new DatabaseHelper(con);
        SQLiteDatabase database =  myHelper.open();
        String[] param = {prenom, nom};
        String query = "SELECT ID FROM user WHERE Prenom = ? AND Nom = ?";
        Cursor cursor = database.rawQuery(query, param);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        this.id = id;
        this.context = con;
        caracteristiques = new Hashtable<String,Object>();
        String[] data = searchDatabase();
        for (int i = 0; i < carac.length; i++)
            setOpt(carac[i], data[i]);
    }

    public Object getOpt(String field) {
        if(this.caracteristiques.containsKey(field))
            return caracteristiques.get(field);
        return null;
    }

    /*
    * @pre : field and value must NOT be null
    * @post : set value associated to field in caracteristiques
     */
    public void setOpt(String field, Object value) {
        if(this.caracteristiques.containsKey(field))
            caracteristiques.put(field,value);
    }

    public void save() {
        DatabaseHelper myHelper = new DatabaseHelper(context);
        SQLiteDatabase database =  myHelper.open();
        String query = "REPLACE INTO user(Nom, Prenom, Genre, Age, Cheveux, Yeux, Rue, CodePost, Localite, Pays, Telephone, Inclinaison, Facebook, Langue) VALUES("
                + "'"+getOpt("Nom")+"',"
                + "'"+getOpt("Prenom")+"',"
                + "'"+getOpt("Genre")+"',"
                + "'"+getOpt("Age")+"',"
                + "'"+getOpt("Cheveux")+"',"
                + "'"+getOpt("Yeux")+"',"
                + "'"+getOpt("Rue")+"',"
                + "'"+getOpt("CodePost")+"',"
                + "'"+getOpt("Localite")+"',"
                + "'"+getOpt("Pays")+"',"
                + "'"+getOpt("Telephone")+"',"
                + "'"+getOpt("Inclinaison")+"',"
                + "'"+getOpt("Facebook")+"',"
                + "'"+getOpt("Langue")+"')";
        database.execSQL(query);
    }

    private String[] searchDatabase() {
        DatabaseHelper myHelper = new DatabaseHelper(context);
        SQLiteDatabase database =  myHelper.open();
        String [] param = new String [1];
        param[0] = String.valueOf(id);
        Cursor cursor = database.rawQuery("SELECT Nom, Prenom, Genre, Age, Cheveux, Yeux, Rue, CodePost, Localite, Pays, Telephone, Inclinaison, Facebook, Langue FROM user WHERE ID = ? ", param);
        cursor.moveToFirst();
        String [] donnees = new String [14];
        for(int i=0; i<donnees.length; i++){
            if (i==7)
                donnees[i]=Integer.toString(cursor.getInt(i));
            donnees[i]=cursor.getString(i);
        }
        cursor.close();
        return donnees;
    }

    public String[] listeAmis() {
        DatabaseHelper myHelper = new DatabaseHelper(context);
        SQLiteDatabase database =  myHelper.open();
        String[] param = {Integer.toString(id)};
        String query = "SELECT DISTINCT U.ID FROM user U, relations R WHERE (U.ID = ID_to and R.ID_from = ? and R.EtatReq = ?) or ( U.ID = R.ID_from and R.ID_to = ? and R.EtatReq = ?);";
        Cursor cursor = database.rawQuery(query, param);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            int IdAmi = cursor.getInt(0);
            String[] param2 = {Integer.toString(IdAmi)};
            Cursor cursor2 = database.rawQuery("SELECT Prenom, Nom FROM user WHERE ID = ?;", param2);
            cursor2.moveToFirst();
            String prenom = cursor2.getString(0);
            String nom = cursor2.getString(1);
            cursor2.close();
            String nomPrenom = prenom+" "+nom;
            list.add(nomPrenom);
            cursor.moveToNext();
        }
        cursor.close();
        return list.toArray(new String[list.size()]);
    }

    public static Date[] dispo(int ID1, int ID2, Context con) {
        DatabaseHelper myHelper = new DatabaseHelper(con);
        SQLiteDatabase database =  myHelper.open();
        String[] param = {Integer.toString(ID1), Integer.toString(ID2)};
        String query = "SELECT strftime('%s', d.Jour) FROM dispo d WHERE d.ID_from = ? AND d.ID_to = ?";
        Cursor cursor = database.rawQuery(query, param);
        Date[] donnees = new Date[cursor.getCount()];
        for (int i = 0; i < donnees.length; i++) {
            donnees[i] = new Date(cursor.getLong(0));
            cursor.moveToNext();
        }
        cursor.close();
        return donnees;
    }

    public void newMessage(int idTo, String message) {
        DatabaseHelper myHelper = new DatabaseHelper(context);
        SQLiteDatabase database =  myHelper.open();
        String query = "INSERT INTO messages(ID_from, ID_to, Content, Time) VALUES(" + getId() + ", " + idTo
                + ", " + message + ", " + DateFormat.getDateTimeInstance().format(new Date()) + ")";
        database.execSQL(query);
    }

    public int getId() {
        return id;
    }

    public Context getContext() {
        return context;
    }

}
