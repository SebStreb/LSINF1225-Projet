package groupek.lsinf1225_projet;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MenuActivity extends AppCompatActivity {

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        ID = b.getInt("id");
        DatabaseHelper db = new DatabaseHelper(this);
        UserTable user = db.getUser(ID);
        Log.wtf("User", user.getLogin());

        Resources res = getResources();
        String[] menu = {
                res.getString(R.string.profil),
                res.getString(R.string.pref),
                res.getString(R.string.browser),
                res.getString(R.string.contacts),
                res.getString(R.string.demandes),
                res.getString(R.string.message),
                res.getString(R.string.meet)
        };

        setContentView(R.layout.activity_menu);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, menu);
        ListView list = (ListView) findViewById(R.id.listView);
        Button button = (Button) findViewById(R.id.button2);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent newActivity;
                Bundle b = new Bundle();
                switch(position){
                    case 0:  newActivity = new Intent(MenuActivity.this, ProfilActivity.class);
                        b.putInt("id", ID);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                    case 1:  newActivity = new Intent(MenuActivity.this, LoginActivity.class); //TODO: Add settings
                        b.putInt("id", ID);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                    case 2:  newActivity = new Intent(MenuActivity.this, Browser.class);
                        b.putInt("id", ID);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                    case 3:  newActivity = new Intent(MenuActivity.this, Contact.class);
                        b.putInt("id", ID);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                    case 4:  newActivity = new Intent(MenuActivity.this, LoginActivity.class); //TODO: Add demandes
                        b.putInt("id", ID);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                    case 5:  newActivity = new Intent(MenuActivity.this, MessagerieActivity.class);
                        b.putInt("id", ID);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                    case 6:  newActivity = new Intent(MenuActivity.this, MeetActivity.class);
                        b.putInt("myID", 1);
                        b.putInt("hisID", 2);
                        newActivity.putExtras(b);
                        startActivity(newActivity);
                        break;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
