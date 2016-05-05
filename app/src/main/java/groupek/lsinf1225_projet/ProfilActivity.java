package groupek.lsinf1225_projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

    }

    /*
     * @pre -
     * @post: génération de la liste d'options pour la personnalisation de profil
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // sections
        listDataHeader.add("Nom");
        listDataHeader.add("Prenom");
        listDataHeader.add("Genre");
        listDataHeader.add("Age");
        listDataHeader.add("Cheveux");
        listDataHeader.add("Yeux");
        listDataHeader.add("Rue");
        listDataHeader.add("CodePost");
        listDataHeader.add("Localité");
        listDataHeader.add("Pays");
        listDataHeader.add("Téléphone");
        listDataHeader.add("Facebook");
        listDataHeader.add("Langue");

        // sous-sections section 1
        List<String> nom = new ArrayList<String>();
        nom.add("Jean");
        nom.add("Michel");

        //sous-sections section 2
        List<String> prenom = new ArrayList<String>();
        prenom.add("Jean");
        prenom.add("Michel");

        //sous-sections section 3
        List<String> genre = new ArrayList<String>();
        genre.add("Homme");
        genre.add("Femme");

        List<String> age = new ArrayList<String>();
        age.add("01/01/1970");

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

        List<String> rue = new ArrayList<String>();
        rue.add("Rue des 3 potiers");

        List<String> codePost = new ArrayList<String>();
        codePost.add("1000");

        List<String> localite = new ArrayList<String>();
        localite.add("Louvain-La-Neuve");

        List<String> pays = new ArrayList<String>();
        pays.add("Belgique");
        pays.add("France");
        pays.add("Luxembourg");
        pays.add("Angleterre");

        List<String> telephone = new ArrayList<String>();
        telephone.add("010/00.00.00");

        List<String> facebook = new ArrayList<String>();
        facebook.add("Collez votre lien Facebook ici");

        List<String> langue = new ArrayList<String>();
        langue.add("Français");
        langue.add("Anglais");
        langue.add("Néerlandais");


        listDataChild.put(listDataHeader.get(0), nom);
        listDataChild.put(listDataHeader.get(1), prenom);
        listDataChild.put(listDataHeader.get(2), genre);
        listDataChild.put(listDataHeader.get(3), age);
        listDataChild.put(listDataHeader.get(4), cheveux);
        listDataChild.put(listDataHeader.get(5), yeux);
        listDataChild.put(listDataHeader.get(6), rue);
        listDataChild.put(listDataHeader.get(7), codePost);
        listDataChild.put(listDataHeader.get(8), localite);
        listDataChild.put(listDataHeader.get(9), pays);
        listDataChild.put(listDataHeader.get(10), telephone);
        listDataChild.put(listDataHeader.get(11), facebook);
        listDataChild.put(listDataHeader.get(12), langue);

    }






}
