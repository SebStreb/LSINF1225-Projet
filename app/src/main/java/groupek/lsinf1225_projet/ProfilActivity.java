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

public class ProfilActivity extends AppCompatActivity {
 /*
    public String[] menu = {
            "Compte",
            "Informations",
            "Confidentialité"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, menu);
        ListView list = (ListView) findViewById(R.id.expandableListView);
        list.setAdapter(adapter);
    } */

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

        // preparing list data
        prepareListData();

        listAdapter = new ExAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Compte");
        listDataHeader.add("Détails");
        listDataHeader.add("Confidentialité");

        // Adding child data
        List<String> compte = new ArrayList<String>();
        compte.add("Login");
        compte.add("Mot de passe");

        List<String> details = new ArrayList<String>();
        details.add("Nom");
        details.add("Prénom");
        details.add("Genre");
        details.add("Age");
        details.add("Cheveux");
        details.add("Yeux");
        details.add("Rue");
        details.add("CodePost");
        details.add("Localité");
        details.add("Pays");
        details.add("Téléphone");
        details.add("Facebook");
        details.add("Langue");


        List<String> privacy = new ArrayList<String>();
        privacy.add("Cacher votre nom");
        privacy.add("Cacher adresse");
        privacy.add("Cacher téléphone");
        privacy.add("Cacher Facebook");

        listDataChild.put(listDataHeader.get(0), compte); // Header, Child data
        listDataChild.put(listDataHeader.get(1), details);
        listDataChild.put(listDataHeader.get(2), privacy);
    }
}
