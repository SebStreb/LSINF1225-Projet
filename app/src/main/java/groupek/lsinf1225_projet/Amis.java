package groupek.lsinf1225_projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Amis extends AppCompatActivity {
    public String[] list = {
            "Bob",
            "Geralt",
            "truc"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                 String test = (String) list.getItemAtPosition(position);
                Intent intent = new Intent(Amis.this, ProfilAmiFav.class);
                Bundle b = new Bundle();
                b.putString("nom", test); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            };

        });

    }
}
