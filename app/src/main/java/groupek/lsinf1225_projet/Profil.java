package groupek.lsinf1225_projet;
import java.util.*;

/**
 * Created by alexandre on 4/30/16.
 */

//TODO : implementer Profil en cohesion avec la base de donnees !!!

public class Profil {

    private LoginActivity.UserLoginTask session;
    private int id;
    private Hashtable<String,Object> caracteristiques;
    private final String[] carac = {"Nom","Prenom","Genre","Age","Cheveux","Yeux","Rue","Code Postal","Localite","Pays","Telephone","Inclinaison","Facebook","Langue(s)"};


    public Profil(LoginActivity.UserLoginTask l,int id){
        this.id = id;
        this.session = l;
        caracteristiques = new Hashtable<String,Object>();
        setDefault(caracteristiques); // TODO : aller chercher les carac dans la BDD SQLite
    }

    /*
    * @pre : hash est une hashtable deja initialisee
    * @post : insere les champs dans la hashtable, sans attribuer une valeur aux champs !!!
     */
    private void setDefault(Hashtable<String,Object> hash){
        for(int i=0; i<carac.length; i++){
            hash.put(carac[i],null);
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





}
