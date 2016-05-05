package groupek.lsinf1225_projet;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
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
