package groupek.lsinf1225_projet;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private String [] caracs = new String [21]; // tableau qui contient les données profils d'un user
    private int id;
    private DatabaseHelper dh = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new ExAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (listDataHeader.get(groupPosition) == "Nom"){
                    Dialog dial = new Dialog(ProfilActivity.this);

                }
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();

                return false;
            }
        });

        fill(this.id); //remplis le tableau carac avec les valeurs deja presentes en bdd

        Button clickButton = (Button) findViewById(R.id.button);
        clickButton.setOnClickListener( new View.OnClickListener() { // quand l'user clique sur le bouton enregistrer et quitter
            @Override
            public void onClick(View v) {
                dh.updateUser(id,caracs);
                finish();
            }
        });

    }

    public void fill(int id){
        UserTable user = dh.getUser(this.id);
        caracs[0] = Integer.toString(user.getId());
        caracs[1] = user.getLogin();
        caracs[2] = user.getPass();
        caracs[3] = user.getNom();
        caracs[4] = user.getPrenom();
        caracs[5] = user.getGenre();
        caracs[6] = user.getAge();
        caracs[7] = user.getCheveux();
        caracs[8] = user.getYeux();
        caracs[9] = user.getRue();
        caracs[10] = Integer.toString(user.getCodePost());
        caracs[11] = user.getLocalite();
        caracs[12] = user.getPays();
        caracs[13] = user.getTelephone();
        caracs[14] = user.getInclinaison();
        caracs[15] = user.getFacebook();
        caracs[16] = user.getLangue();
        caracs[17] = Boolean.toString(user.getCacherNom());
        caracs[18] = Boolean.toString(user.getCacherAdresse());
        caracs[19] = Boolean.toString(user.getCacherTelephone());
        caracs[20] = Boolean.toString(user.getCacherFacebook());

    }

    /*
     * @pre -
     * @post: génération de la liste d'options pour la personnalisation de profil
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // sections
        listDataHeader.add("Genre");
        listDataHeader.add("Cheveux");
        listDataHeader.add("Yeux");
        listDataHeader.add("Pays");
        listDataHeader.add("Langue");


        //sous-sections section 3
        List<String> genre = new ArrayList<String>();
        genre.add("Homme");
        genre.add("Femme");

        List<String> cheveux = new ArrayList<String>();
        cheveux.add("Blonds");
        cheveux.add("Bruns");
        cheveux.add("Noirs");
        cheveux.add("Roux");
        cheveux.add("Autre (teinture)");

        List<String> yeux = new ArrayList<String>();
        yeux.add("Bleus");
        yeux.add("Bruns");
        yeux.add("Verts");
        yeux.add("Gris");
        yeux.add("Autre");

        List<String> pays = new ArrayList<String>();
        pays.add("Belgique");
        pays.add("France");
        pays.add("Luxembourg");
        pays.add("Angleterre");

        List<String> langue = new ArrayList<String>();
        langue.add("Français");
        langue.add("Anglais");
        langue.add("Néerlandais");


        listDataChild.put(listDataHeader.get(0), genre);
        listDataChild.put(listDataHeader.get(1), cheveux);
        listDataChild.put(listDataHeader.get(2), yeux);
        listDataChild.put(listDataHeader.get(3), pays);
        listDataChild.put(listDataHeader.get(4), langue);

    }






}
