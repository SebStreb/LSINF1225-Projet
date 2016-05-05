package groupek.lsinf1225_projet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexandre on 5/2/16.
 * Cette classe a pour but de gerer la communication entre
 * l'application et la BDD.
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

        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('sebstreb@yolo.be', 'Yolo1234', 'Strebelle', 'Sebastien')");
        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('pierreort@yolo.be', 'Yolo1234', 'Ortegat', 'Pierre')");
        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('alexrucq@yolo.be', 'Yolo1234', 'Rucquoy', 'Alexandre')");
        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('antoinepop@yolo.be', 'Yolo1234', 'Popeler', 'Antoine')");
        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('damienvan@yolo.be', 'Yolo1234', 'Vaneberk', 'Damien')");
        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('angmerk@yolo.be', 'Yolo1234', 'Merkel', 'Angela')");
        db.execSQL("INSERT OR REPLACE INTO user(Login, Pass, Nom, Prenom) VALUES " +
                "('scarjo@yolo.be', 'Yolo1234', 'Johanson', 'Scarlet')");


        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-01 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-03 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-04 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-07 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-09 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-12 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-17 12:00:00')");

        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-02 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-07 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-08 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-09 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-10 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-11 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-12 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-15 12:00:00')");
        db.execSQL("INSERT OR IGNORE INTO dispo VALUES (2,1,'2016-05-17 12:00:00')");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ){ //en cas de modification majeure dans la bdd, supprime tout et reconstruit tout en incrementant DB_VERSION (SQLite oblige -_-)
        deleteDB(db);
        onCreate(db);
    }

    public void deleteDB(SQLiteDatabase db){
        db.execSQL("DROP TABLE user");
        db.execSQL("DROP TABLE messages");
        db.execSQL("DROP TABLE photos");
        db.execSQL("DROP TABLE relations");
        db.execSQL("DROP TABLE dispo");
        db.execSQL("DROP TABLE rencontre");
        db.execSQL("DROP INDEX INDEX_ID");
        db.execSQL("DROP INDEX INDEX_PHOTO");
    }

    public SQLiteDatabase open(){
        try{
            return getWritableDatabase();
        } catch(SQLiteException e){
            System.err.print("Failure when trying to open database:" + this.DB_NAME);
            return null;
        }
    }
}
