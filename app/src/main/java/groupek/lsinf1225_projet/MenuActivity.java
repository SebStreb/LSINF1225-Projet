package groupek.lsinf1225_projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent newActivity;
                switch(position){
                    case 0:  newActivity = new Intent(MenuActivity.this, ProfilActivity.class);
                        startActivity(newActivity);
                        break;
                    case 1:  newActivity = new Intent(MenuActivity.this, LoginActivity.class); //TODO: Add preferences
                        startActivity(newActivity);
                        break;
                    case 2:  newActivity = new Intent(MenuActivity.this, LoginActivity.class); //TODO: Add broswer
                        startActivity(newActivity);
                        break;
                    case 3:  newActivity = new Intent(MenuActivity.this, Contact.class);
                        startActivity(newActivity);
                        break;
                    case 4:  newActivity = new Intent(MenuActivity.this, LoginActivity.class); //TODO: Add demandes
                        startActivity(newActivity);
                        break;
                    case 5:  newActivity = new Intent(MenuActivity.this, MessagerieActivity.class);
                        startActivity(newActivity);
                        break;
                    case 6:  newActivity = new Intent(MenuActivity.this, MeetActivity.class);
                        startActivity(newActivity);
                        break;
                }

            };
        });
    }
}
