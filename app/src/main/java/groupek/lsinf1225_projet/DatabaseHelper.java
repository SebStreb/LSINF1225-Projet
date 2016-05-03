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
    private static Context context;


    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table user (\n" +
                "\tID integer auto_increment primary key unique,\n" +
                "\tLogin char not null unique,\n" +
                "\tPass char not null,\n" +
                "\tNom char not null,\n" +
                "\tPrenom char not null,\n" +
                "\tGenre char not null,\n" +
                "\tAge datetime not null,\n" +
                "\tCheveux char not null,\n" +
                "\tYeux char not null,\n" +
                "\tRue char not null,\n" +
                "\tCodePost integer not null,\n" +
                "\tLocalite char not null,\n" +
                "\tPays char not null,\n" +
                "\tTelephone char not null,\n" +
                "\tInclinaison char not null,\n" +
                "\tFacebook char,\n" +
                "\tLangue char not null,\n" +
                "\tCacher_nom bool not null default true,\n" +
                "\tCacher_adresse bool not null default true,\n" +
                "\tCacher_telephone bool not null default true,\n" +
                "\tCacher_facebook bool not null default true\n" +
                ");\n");

        db.execSQL("create unique index INDEX_ID on user(ID);");

        db.execSQL("create table messages (\n" +
                "\tID_from  integer not null,\n" +
                "\tID_to  integer not null,\n" +
                "\tTime datetime not null,\n" +
                "\tContent char not null,\n" +
                "\tforeign key (ID_from) references user,\n" +
                "\tforeign key (ID_to) references user\n" +
                ");");

        db.execSQL("create table photos (\n" +
                "\tID_user  integer not null,\n" +
                "\tNom char not null,\n" +
                "\tPhoto blob not null,\n" +
                "\tprimary key (ID_user, nom),\n" +
                "\tunique(ID_user, nom),\n" +
                "\tforeign key (ID_user) references user\n" +
                ");");

        db.execSQL("create index INDEX_PHOTO on photos(ID_user);");

        db.execSQL("create table relations (\n" +
                "\tID_from  integer not null,\n" +
                "\tID_to  integer not null,\n" +
                "\tEtatReq integer default 0 not null,\n" +
                "\tprimary key (ID_from, ID_to),\n" +
                "\tunique (ID_from, ID_to),\n" +
                "\tforeign key (ID_from) references user,\n" +
                "\tforeign key (ID_to) references user\n" +
                ");");

        db.execSQL("create table dispo (\n" +
                "\tID_from  integer not null,\n" +
                "\tID_to  integer not null,\n" +
                "\tJour datetime not null,\n" +
                "\tforeign key (ID_from) references user,\n" +
                "\tforeign key (ID_to) references user\n" +
                ");");

        db.execSQL("create table rencontre (\n" +
                "\tID_user1  integer not null,\n" +
                "\tID_user2  integer not null,\n" +
                "\tJour datetime not null,\n" +
                "\tunique (ID_user1, ID_user2, jour),\n" +
                "\tforeign key (ID_user1) references user,\n" +
                "\tforeign key (ID_user2) references user\n" +
                ");");
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
        db.execSQL("DROP TABLE user");
        db.execSQL("DROP INDEX INDEX_ID");
        db.execSQL("DROP INDEX INDEX_PHOTO");
    }

    public boolean open(){
        try{
            getWritableDatabase();
        } catch(SQLiteException e){
            System.err.print("Failure when trying to open database:" + this.DB_NAME);
            return false;
        }
        return true;
    }
}
