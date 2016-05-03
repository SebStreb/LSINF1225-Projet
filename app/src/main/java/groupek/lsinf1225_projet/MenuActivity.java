package groupek.lsinf1225_projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class MenuActivity extends AppCompatActivity {

    public String[] menu = {
            "Profil",
            "Préférences",
            "Browser",
            "Contacts",
            "Demandes",
            "Messages",
            "Rencontres"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, menu);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
}
