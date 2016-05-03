package groupek.lsinf1225_projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Contact extends AppCompatActivity {
    public String[] contact = {
            "Amis",
            "Favoris"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, contact);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent newActivity;
                switch(position){
                    case 0:  newActivity = new Intent(Contact.this, Amis.class);
                        startActivity(newActivity);
                        break;
                    case 1:  newActivity = new Intent(Contact.this, Favoris.class);
                        startActivity(newActivity);
                        break;
                }

            };
        });

    };
}
