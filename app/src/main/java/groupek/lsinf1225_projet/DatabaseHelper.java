package groupek.lsinf1225_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexandre on 5/2/16.
 * Cette classe est une implémentation minimale de l'articulation entre
 * la base de données et notre application
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GroupeK_BDD.sqlite";
    private static final int DB_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        onCreate(getWritableDatabase());
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists user (\n" +
                "\tID integer auto_increment primary key unique,\n" +
                "\tLogin char not null unique,\n" +
                "\tPass char not null,\n" +
                "\tNom char not null default 'vide',\n" +
                "\tPrenom char not null default 'vide',\n" +
                "\tGenre char not null default 'vide',\n" +
                "\tAge datetime not null default '1970-01-01',\n" +
                "\tCheveux char not null default 'vide',\n" +
                "\tYeux char not null default 'vide',\n" +
                "\tRue char not null default 'vide',\n" +
                "\tCodePost integer not null default 0,\n" +
                "\tLocalite char not null default 'vide',\n" +
                "\tPays char not null default 'vide',\n" +
                "\tTelephone char not null default 'vide',\n" +
                "\tInclinaison char not null default 'vide',\n" +
                "\tFacebook char default 'vide',\n" +
                "\tLangue char not null default 'vide',\n" +
                "\tCacher_nom bool not null default true,\n" +
                "\tCacher_adresse bool not null default true,\n" +
                "\tCacher_telephone bool not null default true,\n" +
                "\tCacher_facebook bool not null default true\n" +
                ");\n");

        db.execSQL("create unique index if not exists INDEX_ID on user(ID);");

        db.execSQL("create table if not exists messages (\n" +
                "\tID_from  integer not null,\n" +
                "\tID_to  integer not null,\n" +
                "\tTime datetime not null,\n" +
                "\tContent char not null,\n" +
                "\tforeign key (ID_from) references user,\n" +
                "\tforeign key (ID_to) references user\n" +
                ");");

        db.execSQL("create table if not exists photos (\n" +
                "\tID_user  integer not null,\n" +
                "\tNom char not null,\n" +
                "\tPhoto blob not null,\n" +
                "\tprimary key (ID_user, nom),\n" +
                "\tunique(ID_user, nom),\n" +
                "\tforeign key (ID_user) references user\n" +
                ");");

        db.execSQL("create index if not exists INDEX_PHOTO on photos(ID_user);");

        db.execSQL("create table if not exists relations (\n" +
                "\tID_from  integer not null,\n" +
                "\tID_to  integer not null,\n" +
                "\tEtatReq integer default 0 not null,\n" +
                "\tprimary key (ID_from, ID_to),\n" +
                "\tunique (ID_from, ID_to),\n" +
                "\tforeign key (ID_from) references user,\n" +
                "\tforeign key (ID_to) references user\n" +
                ");");

        db.execSQL("create table if not exists dispo (\n" +
                "\tID_from  integer not null,\n" +
                "\tID_to  integer not null,\n" +
                "\tJour datetime not null,\n" +
                "\tforeign key (ID_from) references user,\n" +
                "\tforeign key (ID_to) references user\n" +
                ");");

        db.execSQL("create table if not exists rencontre (\n" +
                "\tID_user1  integer not null,\n" +
                "\tID_user2  integer not null,\n" +
                "\tJour datetime not null,\n" +
                "\tunique (ID_user1, ID_user2, jour),\n" +
                "\tforeign key (ID_user1) references user,\n" +
                "\tforeign key (ID_user2) references user\n" +
                ");");

        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(1, 'sebstreb@yolo.be', 'Yolo1234', 'Strebelle', 'Sebastien')");
        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(2, 'pierreort@yolo.be', 'Yolo1234', 'Ortegat', 'Pierre')");
        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(3, 'alexrucq@yolo.be', 'Yolo1234', 'Rucquoy', 'Alexandre')");
        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(4, 'antoinepop@yolo.be', 'Yolo1234', 'Popeler', 'Antoine')");
        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(5, 'damienvan@yolo.be', 'Yolo1234', 'Vaneberk', 'Damien')");
        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(6, 'angmerk@yolo.be', 'Yolo1234', 'Merkel', 'Angela')");
        db.execSQL("INSERT OR IGNORE INTO user(ID, Login, Pass, Nom, Prenom) VALUES " +
                "(7, 'scarjo@yolo.be', 'Yolo1234', 'Johanson', 'Scarlet')");

        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-01 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-03 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-04 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-07 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-09 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-12 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-17 22:00:00')");

        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-02 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-07 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-08 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-09 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-10 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-11 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-12 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-15 22:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (1,2,'2016-05-17 22:00:00')");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ){ //en cas de modification majeure dans la bdd, supprime tout et reconstruit tout en incrementant DB_VERSION (SQLite oblige -_-)
        deleteDB(db);
        onCreate(db);
    }

    public void deleteDB(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS messages");
        db.execSQL("DROP TABLE IF EXISTS photos");
        db.execSQL("DROP TABLE IF EXISTS relations");
        db.execSQL("DROP TABLE IF EXISTS dispo");
        db.execSQL("DROP TABLE IF EXISTS rencontre");
    }

    public SQLiteDatabase open(){
        try{
            return getWritableDatabase();
        } catch(SQLiteException e){
            System.err.print("Failure when trying to open database:" + this.DB_NAME);
            return null;
        }
    }

    /*    Message    */
    public void addMessage(MessageTable message) {
        SQLiteDatabase db = this.open();
        ContentValues val = new ContentValues();
        val.put("ID_from", message.getID_from());
        val.put("ID_to", message.getID_to());
        val.put("Time", message.getTime());
        val.put("Content", message.getContent());
        db.insert("messages", null, val);
        db.close();
    }

    public MessageTable[] getAllMessage(int ID_from, int ID_to) {
        List<MessageTable> list = new ArrayList<>();
        SQLiteDatabase db = this.open();
        String[] cols = {"Content", "Time"};
        String[] args = {Integer.toString(ID_from), Integer.toString(ID_to), Integer.toString(ID_to), Integer.toString(ID_from)};
        Cursor cursor = db.query("messages", cols, "ID_from = ? AND ID_to = ? OR ID_from = ? AND ID_to = ?", args, null, null, "Time ASC");
        if (cursor.moveToFirst()) {
            do {
                String content = cursor.getString(0);
                String time = cursor.getString(1);
                MessageTable message = new MessageTable(ID_from, ID_to, content, time);
                list.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list.toArray(new MessageTable[list.size()]);
    }

    /*    User    */
    public void addUser(UserTable user) {
        SQLiteDatabase db = this.open();
        ContentValues val = new ContentValues();
        //val.put("ID", user.getId()); Pas sûr
        val.put("Login", user.getLogin());
        val.put("Pass", user.getPass());
        val.put("Nom", user.getNom());
        val.put("Prenom", user.getPrenom());
        val.put("Genre", user.getGenre());
        val.put("Age", user.getAge());
        val.put("Cheveux", user.getCheveux());
        val.put("Yeux", user.getYeux());
        val.put("Rue", user.getRue());
        val.put("CodePost", user.getCodePost());
        val.put("Localite", user.getLocalite());
        val.put("Pays", user.getPays());
        val.put("Telephone", user.getTelephone());
        val.put("Inclinaison", user.getInclinaison());
        val.put("Facebook", user.getFacebook());
        val.put("Langue", user.getLangue());
        val.put("Cacher_nom", user.getCacherNom());
        val.put("Cacher_adresse", user.getCacherAdresse());
        val.put("Cachet_telephone", user.getCacherTelephone());
        val.put("Cacher_facebook", user.getCacherFacebook());
        db.insert("user", null, val);
        db.close();
    }

    public UserTable getUser(int id) {
        SQLiteDatabase db = this.open();
        String[] cols = {"Login", "Pass", "Nom", "Prenom", "Genre", "Age", "Cheveux", "Yeux", "Rue", "CodePost", "Localite", "Pays", "Telephone", "Inclinaison", "Facebook", "Langue", "Cacher_nom", "Cacher_adresse", "Cacher_telephone", "Cacher_facebook"};
        String[] args = {Integer.toString(id)};
        Cursor cursor = db.query("user", cols, "ID = ?", args, null, null, null);
        if (!cursor.moveToFirst())
            return null;
        String login = cursor.getString(0);
        String pass = cursor.getString(1);
        String nom = cursor.getString(2);
        String prenom = cursor.getString(3);
        String genre = cursor.getString(4);
        String age = cursor.getString(5);
        String cheveux = cursor.getString(6);
        String yeux = cursor.getString(7);
        String rue = cursor.getString(8);
        int codepost = cursor.getInt(9);
        String localite = cursor.getString(10);
        String pays = cursor.getString(11);
        String telephone = cursor.getString(12);
        String inclinaison = cursor.getString(13);
        String facebook = cursor.getString(14);
        String langue = cursor.getString(15);
        boolean cacher_nom = cursor.getInt(16) == 1;
        boolean cacher_adresse = cursor.getInt(17) == 1;
        boolean cacher_telephone = cursor.getInt(18) == 1;
        boolean cacher_facebook = cursor.getInt(19) == 1;
        UserTable user = new UserTable(id, login, pass, nom, prenom, genre, age, cheveux, yeux, rue, codepost, localite, pays, telephone, inclinaison, facebook, langue, cacher_nom, cacher_adresse, cacher_telephone, cacher_facebook);
        cursor.close();
        db.close();
        return user;
    }

    public UserTable getUser(String login) {
        SQLiteDatabase db = this.open();
        String[] cols = {"ID", "Pass", "Nom", "Prenom", "Genre", "Age", "Cheveux", "Yeux", "Rue", "CodePost", "Localite", "Pays", "Telephone", "Inclinaison", "Facebook", "Langue", "Cacher_nom", "Cacher_adresse", "Cacher_telephone", "Cacher_facebook"};
        String[] args = {login};
        Cursor cursor = db.query("user", cols, "Login = ?", args, null, null, null);
        if (!cursor.moveToFirst())
            return null;
        int id = cursor.getInt(0);
        String pass = cursor.getString(1);
        String nom = cursor.getString(2);
        String prenom = cursor.getString(3);
        String genre = cursor.getString(4);
        String age = cursor.getString(5);
        String cheveux = cursor.getString(6);
        String yeux = cursor.getString(7);
        String rue = cursor.getString(8);
        int codepost = cursor.getInt(9);
        String localite = cursor.getString(10);
        String pays = cursor.getString(11);
        String telephone = cursor.getString(12);
        String inclinaison = cursor.getString(13);
        String facebook = cursor.getString(14);
        String langue = cursor.getString(15);
        boolean cacher_nom = cursor.getInt(16) == 1;
        boolean cacher_adresse = cursor.getInt(17) == 1;
        boolean cacher_telephone = cursor.getInt(18) == 1;
        boolean cacher_facebook = cursor.getInt(19) == 1;
        UserTable user = new UserTable(id, login, pass, nom, prenom, genre, age, cheveux, yeux, rue, codepost, localite, pays, telephone, inclinaison, facebook, langue, cacher_nom, cacher_adresse, cacher_telephone, cacher_facebook);
        cursor.close();
        db.close();
        return user;
    }

    public UserTable getUser(String prenom, String nom) {
        SQLiteDatabase db = this.open();
        String[] cols = {"ID", "Login", "Pass", "Genre", "Age", "Cheveux", "Yeux", "Rue", "CodePost", "Localite", "Pays", "Telephone", "Inclinaison", "Facebook", "Langue", "Cacher_nom", "Cacher_adresse", "Cacher_telephone", "Cacher_facebook"};
        String[] args = {prenom, nom};
        Cursor cursor = db.query("user", cols, "Prenom = ? AND Nom = ?", args, null, null, null);
        if (!cursor.moveToFirst())
            return null;
        int id = cursor.getInt(0);
        String login = cursor.getString(1);
        String pass = cursor.getString(2);
        String genre = cursor.getString(3);
        String age = cursor.getString(4);
        String cheveux = cursor.getString(5);
        String yeux = cursor.getString(6);
        String rue = cursor.getString(7);
        int codepost = cursor.getInt(8);
        String localite = cursor.getString(9);
        String pays = cursor.getString(10);
        String telephone = cursor.getString(11);
        String inclinaison = cursor.getString(12);
        String facebook = cursor.getString(13);
        String langue = cursor.getString(14);
        boolean cacher_nom = cursor.getInt(15) == 1;
        boolean cacher_adresse = cursor.getInt(16) == 1;
        boolean cacher_telephone = cursor.getInt(17) == 1;
        boolean cacher_facebook = cursor.getInt(18) == 1;
        UserTable user = new UserTable(id, login, pass, nom, prenom, genre, age, cheveux, yeux, rue, codepost, localite, pays, telephone, inclinaison, facebook, langue, cacher_nom, cacher_adresse, cacher_telephone, cacher_facebook);
        cursor.close();
        db.close();
        return user;
    }

    public void updateUser(UserTable user, String[] newValues){
        SQLiteDatabase db =  this.open();
        String query = "REPLACE INTO user(Nom, Prenom, Genre, Age, Cheveux, Yeux, Rue, CodePost, Localite, Pays, Telephone, Inclinaison, Facebook, Langue, Cacher_nom bool, Cacher_adresse, Cacher_telephone, Cacher_facebook) VALUES("
                + "'"+newValues[0]+"',"
                + "'"+newValues[1]+"',"
                + "'"+newValues[2]+"',"
                + "'"+newValues[3]+"',"
                + "'"+newValues[4]+"',"
                + "'"+newValues[5]+"',"
                + "'"+newValues[6]+"',"
                + "'"+newValues[7]+"',"
                + "'"+newValues[8]+"',"
                + "'"+newValues[9]+"',"
                + "'"+newValues[10]+"',"
                + "'"+newValues[11]+"',"
                + "'"+newValues[12]+"',"
                + "'"+newValues[13]+"',"
                + "'"+newValues[14]+"',"
                + "'"+newValues[15]+"',"
                + "'"+newValues[16]+"',"
                + "'"+newValues[17]+"') WHERE user.ID ="+Integer.toString(user.getId());
        db.execSQL(query);
        db.close();
    }

    /*    Rencontre    */
    public void addRencontre(RencontreTable rencontre){
        SQLiteDatabase db = this.open();
        ContentValues content = new ContentValues();
        content.put("ID_user1",rencontre.getID_user1());
        content.put("ID_user2", rencontre.getID_user2());
        content.put("Jour", rencontre.getJour());
        db.insert("rencontre",null,content);
        db.close();
    }

    /*    Dispo    */
    public void deleteDispo(DispoTable dispo){
        SQLiteDatabase db = this.open();
        String [] params = new String[3];
        params[0] = Integer.toString(dispo.getID_from());
        params[1] = Integer.toString(dispo.getID_to());
        params[2] = dispo.getJour();
        db.delete("dispo", "dispo.ID_from = ? AND dispo.ID_to = ? AND dispo.jour = ?", params);
        db.close();
    }

    //Querry: SELECT strftime('%s', d.Jour) AS "timestamps" FROM dispo d WHERE d.ID_from = ? AND d.ID_to = ?
    public Date[] getDispo(int from, int to){
        SQLiteDatabase db = this.open();
        String args [] = new String[2];
        args[0] = Integer.toString(from);
        args[1] = Integer.toString(to);
        Cursor cursor = db.rawQuery("SELECT strftime('%s', d.Jour) FROM dispo d WHERE d.ID_from = ? AND d.ID_to = ?", args);
        Date [] res = new Date[cursor.getCount()];
        for(int i = 0; i < cursor.getCount(); i++){
            res[i] = new Date(cursor.getLong(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return res;
    }

}
