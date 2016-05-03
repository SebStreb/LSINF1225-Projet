package groupek.lsinf1225_projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * TODO: add the functions that set the min date to yesteday of the current day
        */


        CalendarView calendarView=(CalendarView)findViewById(R.id.calendarF);
        Log.wtf("Calendar", String.valueOf(calendarView == null));

        setContentView(R.layout.activity_calendar);
    }
}
