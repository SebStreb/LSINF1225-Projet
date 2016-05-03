package groupek.lsinf1225_projet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.*;

/**
 * Created by alexandre on 4/30/16.
 */

//TODO : implementer Profil en cohesion avec la base de donnees !!!

public class Profil {

    private LoginActivity.UserLoginTask session;
    private Context context;
    private int id;
    private Hashtable<String,Object> caracteristiques;
    private static final String[] carac = {"Nom","Prenom","Genre","Age","Cheveux","Yeux","Rue","Code Postal","Localite","Pays","Telephone","Inclinaison","Facebook","Langue(s)"};


    public Profil(LoginActivity.UserLoginTask l,int id, Context ctxt){
        this.id = id;
        this.context = ctxt;
        this.session = l;
        caracteristiques = new Hashtable<String,Object>();
        String [] donnees;
        donnees = searchDatabase(this.context);
        //setDefault(caracteristiques); // TODO : aller chercher les carac dans la BDD SQLite
    }

    public Profil(int id){
        this.id =id;
        this.session = null;
    }

    /*
    * @pre : hash est une hashtable deja initialisee
    * @post : insere les champs dans la hashtable, sans attribuer une valeur aux champs !!!
     */
    private void setDefault(Hashtable<String,Object> hash, String[] data){
        for(int i=0; i<carac.length; i++){
            hash.put(carac[i],data[i]);
        }
    }

    /*
    * @pre : field and value must NOT be null
    * @post : set value associated to field in caracteristiques
     */
    private void setOpt(String field, Object value){
        if(this.caracteristiques.containsKey(field)){
            caracteristiques.put(field,value);
        }
        return;
    }

    public String[] searchDatabase(Context context){
        DatabaseHelper myHelper = new DatabaseHelper(this.context);
        SQLiteDatabase database =  myHelper.open();
        String [] param = new String [1];
        param[0] = String.valueOf(this.id);
        Cursor cursor = database.rawQuery("SELECT * FROM user WHERE ID = ? ", param);
        String [] donnees = new String [21];
        for(int i=0; i< donnees.length; i++){
            donnees[i]=cursor.getString(i);
        }
        return donnees;
    }




}
