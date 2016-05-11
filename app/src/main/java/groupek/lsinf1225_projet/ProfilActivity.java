package groupek.lsinf1225_projet;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

/**
 * created by alexandre. Cette classe represente l'activité profile, qui donne a un utilisateur une
 * vue générale sur son profile.
 **/
public class ProfilActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String [] caracs; // tableau qui contient les données profils d'un user
    private int id;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        dh = new DatabaseHelper(this);
        Bundle b = getIntent().getExtras();
        this.id = b.getInt("id"); //recuperer l'integer qu'on me passe vie le menu
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        prepareListData();


        listAdapter = new ExAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);
        List<String> list = new ArrayList<String>(); //ira recuperer toutes les nouvelles valeurs de l'user

        caracs = new String[21];
        fill(this.id); //remplis le tableau caracs avec les valeurs deja presentes en bdd

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();


                String child= listDataHeader.get(groupPosition);
                switch (child){ // sur quoi l'utilisateur a clique sur quelle classe ? (Cheveux, Yeux...)
                    case "Genre" : String genre = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[2] = genre;
                        break;
                    case "Cheveux" : String cheveux = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[4] = cheveux;
                        break;
                    case "Yeux" : String yeux = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[5] = yeux;
                        break;
                    case "Pays" : String pays = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[9] = pays;
                        break;
                    case "Langue" : String langue = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[13] = langue;
                        break;
                    case "Cacher nom ?" : String cacherNom = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[14] = cacherNom;
                        break;
                    case "Cacher adresse ?" : String cacherAdresse = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[15] = cacherAdresse;
                        break;
                    case "Cacher téléphone ?" : String cacherTelephone = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[16] = cacherTelephone;
                        break;
                    case "Cacher Facebook ?" : String cacherFacebook = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        caracs[17] = cacherFacebook;
                        break;
                } //on recupere la valeur de la sous-classe (Blond, Bleus...)

                return false;
            }
        });


        Button clickButton = (Button) findViewById(R.id.button);
        clickButton.setOnClickListener( new View.OnClickListener() { // quand l'user clique sur le bouton enregistrer et quitter
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();

                EditText textNom = (EditText) findViewById(R.id.editTextNom);
                String valueNom = textNom.getText().toString();
                if (!valueNom.isEmpty()) { //on verifie que l'user a modifie la valeur, sinon on ne l'ajoute pas
                    caracs[0] = valueNom;
                }


                EditText textPrenom = (EditText)findViewById(R.id.editTextPrenom);
                String valuePrenom = textPrenom.getText().toString();
                if(!valuePrenom.isEmpty()) {
                    caracs[1] = valuePrenom;
                }

                EditText textAge = (EditText) findViewById(R.id.editTextDate);
                String valueAge = textAge.getText().toString();
                if (!valueAge.isEmpty()) {
                    caracs[3] = valueAge;
                }

                /*EditText textLogin = (EditText) findViewById(R.id.editTextLogin);
                String valueLogin = textLogin.getText().toString();
                if(valueLogin != null || !valueLogin.isEmpty()){

                }*/

                /*EditText textPass = (EditText) findViewById(R.id.editText3);
                String valuePass = textPass.getText().toString();
                if(valuePass != null || !valuePass.isEmpty()){

                }*/

                EditText textCP = (EditText) findViewById(R.id.editTextCP);
                String valueCP = textCP.getText().toString();
                if(!valueCP.isEmpty()){
                    caracs[7] = valueCP;
                }

                EditText textAdress = (EditText) findViewById(R.id.editText2);
                String valueAdress = textAdress.getText().toString();
                if(!valueAdress.isEmpty()){
                    caracs[6] = valueAdress;
                }

                EditText textLocalite = (EditText) findViewById(R.id.editText);
                String valueLocalite = textLocalite.getText().toString();
                if(!valueLocalite.isEmpty()){
                    caracs[8] = valueLocalite;
                }

                EditText textPhone = (EditText) findViewById(R.id.editTextPhone);
                String valuePhone = textPhone.getText().toString();
                if(!valuePhone.isEmpty()){
                    caracs[10] = valuePhone;
                }

                EditText textFacebook = (EditText) findViewById(R.id.editTextFacebook);
                String valueFacebook = textFacebook.getText().toString();
                if(!valueFacebook.isEmpty()){
                    caracs[12] = valueFacebook;
                }

                dh.updateUser(id,caracs);
                finish();
            }
        });







    }

    public void fill(int id){
        UserTable user = dh.getUser(this.id);

        EditText nom = (EditText) findViewById(R.id.editTextNom);
        caracs[0] = user.getNom();
        nom.setText(caracs[0]);

        EditText prenom = (EditText) findViewById(R.id.editTextPrenom);
        caracs[1] = user.getPrenom();
        prenom.setText(caracs[1]);

        caracs[2] = user.getGenre();

        EditText age = (EditText) findViewById(R.id.editTextDate);
        caracs[3] = user.getAge();
        age.setText(caracs[3]);

        caracs[4] = user.getCheveux();
        caracs[5] = user.getYeux();

        EditText rue = (EditText) findViewById(R.id.editText2);
        caracs[6] = user.getRue();
        rue.setText(caracs[6]);

        EditText codePost = (EditText) findViewById(R.id.editTextCP);
        caracs[7] = Integer.toString(user.getCodePost());
        codePost.setText(caracs[7]);

        EditText localite = (EditText) findViewById(R.id.editText);
        caracs[8] = user.getLocalite();
        localite.setText(caracs[8]);

        caracs[9] = user.getPays();

        EditText phone = (EditText) findViewById(R.id.editTextPhone) ;
        caracs[10] = user.getTelephone();
        phone.setText(caracs[10]);

        caracs[11] = user.getInclinaison();

        EditText face = (EditText) findViewById(R.id.editTextFacebook);
        caracs[12] = user.getFacebook();
        face.setText(caracs[12]);

        caracs[13] = user.getLangue();
        caracs[14] = Boolean.toString(user.getCacherNom());
        caracs[15] = Boolean.toString(user.getCacherAdresse());
        caracs[16] = Boolean.toString(user.getCacherTelephone());
        caracs[17] = Boolean.toString(user.getCacherFacebook());
    }

    /*
     * @pre -
     * @post: génération de la liste d'options pour la personnalisation de profil
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // sections
        listDataHeader.add(getString(R.string.gender));
        listDataHeader.add(getString(R.string.hair));
        listDataHeader.add(getString(R.string.eyes));
        listDataHeader.add(getString(R.string.country));
        listDataHeader.add(getString(R.string.language));
        listDataHeader.add(getString(R.string.hidename));
        listDataHeader.add(getString(R.string.hidepost));
        listDataHeader.add(getString(R.string.hidephone));
        listDataHeader.add(getString(R.string.hideface));


        //sous-sections section 3
        List<String> genre = new ArrayList<String>();
        genre.add(getString(R.string.men));
        genre.add(getString(R.string.women));

        List<String> cheveux = new ArrayList<String>();
        cheveux.add(getString(R.string.blond));
        cheveux.add(getString(R.string.brown));
        cheveux.add(getString(R.string.black));
        cheveux.add(getString(R.string.redhead));
        cheveux.add(getString(R.string.other));

        List<String> yeux = new ArrayList<String>();
        yeux.add(getString(R.string.blue));
        yeux.add(getString(R.string.brown));
        yeux.add(getString(R.string.green));
        yeux.add(getString(R.string.grey));
        yeux.add(getString(R.string.other));

        List<String> pays = new ArrayList<String>();
        pays.add(getString(R.string.belgium));
        pays.add(getString(R.string.france));
        pays.add(getString(R.string.luxo));
        pays.add(getString(R.string.england));

        List<String> langue = new ArrayList<String>();
        langue.add(getString(R.string.french));
        langue.add(getString(R.string.english));
        langue.add(getString(R.string.dutch));

        List<String> cacherNom = new ArrayList<String>();
        cacherNom.add(getString(R.string.yes));
        cacherNom.add(getString(R.string.no));

        List<String> cacherAdresse = new ArrayList<String>();
        cacherAdresse.add(getString(R.string.yes));
        cacherAdresse.add(getString(R.string.no));

        List<String> cacherTelephone = new ArrayList<String>();
        cacherTelephone.add(getString(R.string.yes));
        cacherTelephone.add(getString(R.string.no));

        List<String> cacherFacebook = new ArrayList<String>();
        cacherFacebook.add(getString(R.string.yes));
        cacherFacebook.add(getString(R.string.no));


        listDataChild.put(listDataHeader.get(0), genre);
        listDataChild.put(listDataHeader.get(1), cheveux);
        listDataChild.put(listDataHeader.get(2), yeux);
        listDataChild.put(listDataHeader.get(3), pays);
        listDataChild.put(listDataHeader.get(4), langue);
        listDataChild.put(listDataHeader.get(5), cacherNom);
        listDataChild.put(listDataHeader.get(6), cacherAdresse);
        listDataChild.put(listDataHeader.get(7), cacherTelephone);
        listDataChild.put(listDataHeader.get(8), cacherFacebook);

    }






}
