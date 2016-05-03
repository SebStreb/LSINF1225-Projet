package groupek.lsinf1225_projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilAmiFav extends AppCompatActivity {
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ami_fav);
        Bundle b = getIntent().getExtras();
        String value = b.getString("nom");
        txt =(TextView)this.findViewById(R.id.textView3);
        txt.setText(value);
    }
}
