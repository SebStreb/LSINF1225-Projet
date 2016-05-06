package groupek.lsinf1225_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Created by alexandre on 5/2/16.
 * Cette classe est une implémentation minimale de l'articulation entre
 * la base de données et notre application
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GroupeK_BDD.sqlite";
    private static final int DB_VERSION = 3;


    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists user (\n" +
                "\t_id integer primary key autoincrement,\n" +
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

        db.execSQL("create unique index if not exists INDEX_ID on user(_id);");

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

        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('sebstreb@yolo.be', 'Yolo1234', 'Strebelle', 'Sebastien')");
        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('pierreort@yolo.be', 'Yolo1234', 'Ortegat', 'Pierre')");
        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('alexrucq@yolo.be', 'Yolo1234', 'Rucquoy', 'Alexandre')");
        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('antoinepop@yolo.be', 'Yolo1234', 'Popeler', 'Antoine')");
        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('damienvan@yolo.be', 'Yolo1234', 'Vaneberck', 'Damien')");
        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('angmerk@yolo.be', 'Yolo1234', 'Merkel', 'Angela')");
        db.execSQL("INSERT OR IGNORE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('scarjo@yolo.be', 'Yolo1234', 'Johanson', 'Scarlet')");

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

        db.execSQL("INSERT OR IGNORE INTO relations VALUES (5,2,1)");
        db.execSQL("INSERT OR IGNORE INTO relations VALUES (5,3,1)");
        db.execSQL("INSERT OR IGNORE INTO relations VALUES (5,1,1)");
        db.execSQL("INSERT OR IGNORE INTO relations VALUES (1,5,1)");
        db.execSQL("INSERT OR IGNORE INTO relations VALUES (5,4,2)");
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
        String[] cols = {"ID_from", "ID_to", "Content", "Time"};
        String[] args = {Integer.toString(ID_from), Integer.toString(ID_to), Integer.toString(ID_to), Integer.toString(ID_from)};
        Cursor cursor = db.query("messages", cols, "ID_from = ? AND ID_to = ? OR ID_from = ? AND ID_to = ?", args, null, null, "Time ASC");
        if (cursor.moveToFirst()) {
            do {
                ID_from = cursor.getInt(0);
                ID_to = cursor.getInt(1);
                String content = cursor.getString(2);
                String time = cursor.getString(3);
                MessageTable message = new MessageTable(ID_from, ID_to, time, content);
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
        val.put("Cacher_telephone", user.getCacherTelephone());
        val.put("Cacher_facebook", user.getCacherFacebook());
        db.insert("user", null, val);
        db.close();
    }

    public UserTable getUser(int id) {
        SQLiteDatabase db = this.open();
        String[] cols = {"Login", "Pass", "Nom", "Prenom", "Genre", "Age", "Cheveux", "Yeux", "Rue", "CodePost", "Localite", "Pays", "Telephone", "Inclinaison", "Facebook", "Langue", "Cacher_nom", "Cacher_adresse", "Cacher_telephone", "Cacher_facebook"};
        String[] args = {Integer.toString(id)};
        Cursor cursor = db.query("user", cols, "_id = ?", args, null, null, null);
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
        String[] cols = {"_id", "Pass", "Nom", "Prenom", "Genre", "Age", "Cheveux", "Yeux", "Rue", "CodePost", "Localite", "Pays", "Telephone", "Inclinaison", "Facebook", "Langue", "Cacher_nom", "Cacher_adresse", "Cacher_telephone", "Cacher_facebook"};
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
        String[] cols = {"_id", "Login", "Pass", "Genre", "Age", "Cheveux", "Yeux", "Rue", "CodePost", "Localite", "Pays", "Telephone", "Inclinaison", "Facebook", "Langue", "Cacher_nom", "Cacher_adresse", "Cacher_telephone", "Cacher_facebook"};
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

    public void updateUser(int id, String[] newValues){
        SQLiteDatabase db =  this.open();
        ContentValues val = new ContentValues();
        val.put("Nom", newValues[0]);
        val.put("Prenom", newValues[1]);
        val.put("Genre", newValues[2]);
        val.put("Age", newValues[3]);
        val.put("Cheveux", newValues[4]);
        val.put("Yeux", newValues[5]);
        val.put("Rue", newValues[6]);
        val.put("CodePost", newValues[7]);
        val.put("Localite", newValues[8]);
        val.put("Pays", newValues[9]);
        val.put("Telephone", newValues[10]);
        val.put("Inclinaison", newValues[11]);
        val.put("Facebook", newValues[12]);
        val.put("Langue", newValues[13]);
        val.put("Cacher_nom", newValues[14]);
        val.put("Cacher_adresse", newValues[15]);
        val.put("Cacher_telephone", newValues[16]);
        val.put("Cacher_facebook", newValues[17]);
        String[] param = {Integer.toString(id)};
        db.update("user", val, "user._id = ?", param);
        db.close();
    }

    public int connect(String login, String password) {
        SQLiteDatabase db = this.open();
        String[] param = {login, password};
        Cursor cursor = db.rawQuery("SELECT _id FROM user WHERE Login = ? AND Pass = ?", param);
        try {
            if (!cursor.moveToFirst())
                addUser(new UserTable(login, password));
        } catch (SQLiteConstraintException e) {
            return -1;
        }
        Cursor cursor2 = db.rawQuery("SELECT _id FROM user WHERE Login = ? AND Pass = ?", param);
        if (!cursor2.moveToFirst())
            return -1;
        int id = cursor2.getInt(0);
        cursor.close();
        cursor2.close();
        db.close();
        return id;
    }

    public PhotoTable[] getAllPhotos(int id){
        List<PhotoTable> list = new ArrayList<>();
        SQLiteDatabase db = this.open();
        String[] cols = {"nom","image"}; // select nom, image from photos where photos.ID_user = id
        String[] args = {Integer.toString(id)};
        Cursor cursor = db.query("photos", cols, "photos.ID_user = ?", args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nom = cursor.getString(0);
                byte[] bmp = cursor.getBlob(1);
                PhotoTable photos = new PhotoTable(id,nom,bmp);
                list.add(photos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list.toArray(new PhotoTable[list.size()]);
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

        String args [] = new String[2], col [] = new String[1];
        args[0] = Integer.toString(from);
        args[1] = Integer.toString(to);
        col[0] = "Jour";
        //Cursor cursor = db.query("dispo", col, "ID_from = ? AND ID_to = ?", args, null, null, null, null );
        Cursor cursor = db.rawQuery("SELECT d.Jour FROM dispo d WHERE d.ID_from = ? AND d.ID_to = ?", args);
        Date [] res = new Date[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            String temp = cursor.getString(0);
            String sp[] = temp.split("-");
            res[i] = new Date(Integer.parseInt(sp[0])-1900,Integer.parseInt(sp[1])-1,Integer.parseInt((sp[2].split(" "))[0]));//TODO change this deprecated method
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return res;
    }

    public void saveDispo(int from, int to, Hashtable<Long, Boolean> fromDate, Hashtable<Long, Boolean> toDate){
        SQLiteDatabase db = this.open();
        String args[] = {""+from, ""+to, ""+to, ""+from};
        db.delete("dispo", "ID_from = ? and ID_to = ? OR  ID_from = ? and ID_to = ?",args);
        Set<Long> fromSet = fromDate.keySet();
        Set<Long> toSet = toDate.keySet();
        Long [] fromArray = new Long[fromSet.size()];
        Long [] toArray = new Long[toSet.size()];
        fromArray = fromSet.toArray(fromArray);
        toArray = toSet.toArray(toArray);

        Format form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(int i = 0; i < fromArray.length; i++){
            Date d = new Date(fromArray[i]);
            db.execSQL("INSERT OR IGNORE INTO dispo VALUES ("+from+","+to+",'"+ form.format(d)+"')");
        }

        for(int i = 0; i < toArray.length; i++){
            Date d = new Date(toArray[i]);
            db.execSQL("INSERT OR IGNORE INTO dispo VALUES ("+to+","+from+",'"+ form.format(d)+"')");
        }
        db.close();

    }

    public String[] listeAmis(int myId) {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase database =  this.open();
        String[] param = {Integer.toString(myId)};
        String query = "SELECT DISTINCT U._id FROM user U, relations R WHERE (U._id = R.ID_to and R.ID_from = ? and R.EtatReq = 1) or ( U._id = R.ID_from and R.ID_to = ? and R.EtatReq = 1)";
        Cursor cursor = database.rawQuery(query, param);
        if (cursor.moveToFirst()) {
            do {
                int IdAmi = cursor.getInt(0);
                String[] param2 = {Integer.toString(IdAmi)};
                Cursor cursor2 = database.rawQuery("SELECT Prenom, Nom FROM user WHERE _id = ?", param2);
                cursor2.moveToFirst();
                String prenom = cursor2.getString(0);
                String nom = cursor2.getString(1);
                cursor2.close();
                String nomPrenom = prenom+" "+nom;
                list.add(nomPrenom);
                cursor.moveToNext();
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list.toArray(new String[list.size()]);
    }

    public AmisActivity[] potoSearchAmis(int IdUtilisateur){
        ArrayList<AmisActivity> list = new ArrayList<AmisActivity>();
        SQLiteDatabase database =  this.open();
        String[] param = {Integer.toString(IdUtilisateur)};
        String requete = "SELECT DISTINCT U._id FROM user U, relations R WHERE (U._id = R.ID_to and R.ID_from = ? and R.EtatReq = 1) or ( U._id = R.ID_from and R.ID_to = ? and R.EtatReq = 1)";
        Cursor cursor = database.rawQuery(requete, param);
        int IdAmi;
        AmisActivity ami;
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                IdAmi = cursor.getInt(0);
                UserTable pote = getUser(IdAmi);
                String nom = pote.getNom();
                String prenom = pote.getPrenom();
                String nomPrenom = nom + " " + prenom;
                ami = new AmisActivity(nomPrenom, IdAmi);
                list.add(ami);
            }while(cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return list.toArray(new AmisActivity[list.size()]);
    }

    public FavorisActivity[] potoSearchFavoris(int IdUtilisateur){
        ArrayList<FavorisActivity> list = new ArrayList<FavorisActivity>();
        SQLiteDatabase database =  this.open();
        String[] param = {Integer.toString(IdUtilisateur)};
        String requete = "SELECT DISTINCT U._id FROM user U, relations R WHERE (U._id = R.ID_to and R.ID_from = ? and R.EtatReq = 2) or ( U._id = R.ID_from and R.ID_to = ? and R.EtatReq = 1)";
        Cursor cursor = database.rawQuery(requete, param);
        int IdFav;
        FavorisActivity fav;
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                IdFav = cursor.getInt(0);
                UserTable pote = getUser(IdFav);
                String nom = pote.getNom();
                String prenom = pote.getPrenom();
                String nomPrenom = nom + " " + prenom;
                fav = new FavorisActivity(nomPrenom, IdFav);
                list.add(fav);
            }while(cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return list.toArray(new FavorisActivity[list.size()]);
    }

    public String[] caracteristique(int IdAmi){
        ArrayList<String> list = new ArrayList<String>();
        UserTable poto = getUser(IdAmi);
        list.add(poto.getPrenom());
        if (!poto.getCacherNom()) {
            list.add(poto.getNom());
        }
        list.add(poto.getGenre());
        list.add(poto.getAge());
        list.add(poto.getCheveux());
        list.add(poto.getYeux());
        if (!poto.getCacherAdresse()) {
            list.add(poto.getRue());
        }
        if (!poto.getCacherAdresse()) {
            list.add(String.valueOf(poto.getCodePost()));
        }
        if (!poto.getCacherAdresse()) {
            list.add(poto.getLocalite());
        }
        list.add(poto.getPays());
        if (!poto.getCacherTelephone()) {
            list.add(poto.getTelephone());
        }
        list.add(poto.getInclinaison());
        if (!poto.getCacherFacebook()) {
            list.add(poto.getFacebook());
        }
        list.add(poto.getLangue());
        return list.toArray(new String[list.size()]);
    }

}
