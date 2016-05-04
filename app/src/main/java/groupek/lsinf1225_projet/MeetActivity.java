package groupek.lsinf1225_projet;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

@SuppressLint("SimpleDateFormat")
public class MeetActivity extends AppCompatActivity {
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;


    //the hashtables used to know who chose what when
    private Hashtable<Long, Boolean> daysUser = new Hashtable<>();
    private Hashtable<Long, Boolean> daysOther = new Hashtable<>();
    //TODO load here all the dates from the other one
    //TODO load here all the dates from the current user
    //TODO load the date sof the two user into the callendar

    //to color the day
    /*
    *   BlueUser: #4169E1 - Royal Blue - The color used when the user select a day
    *   BlueOther: #87CEEB - Sky Blue - The color used to show the user whitch dates have been picked by the other user
    *   Red: #B22222 - Fire Brick - The color used when they both agree on the same date
    */
    final ColorDrawable green = new ColorDrawable(Color.GREEN);
    final ColorDrawable blue = new ColorDrawable(Color.parseColor("#4169E1"));
    final ColorDrawable red = new ColorDrawable(Color.RED);

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);

        // Max date is next 7 days
        cal.add(Calendar.DATE, 7);

        /*
        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
        }
        */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();


        //seting the mindate 1 days before the current day
        Calendar calM = Calendar.getInstance();
        calM.setTime(new Date());
        calM.add(Calendar.DATE, 0);
        caldroidFragment.setMinDate(calM.getTime());

        // If Activity is created after rotation
        if (savedInstanceState != null) {caldroidFragment.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            /*
            * On actualise la vue and fonction des dates que l'utilisateur sélectionne
            */
            @Override
            public void onSelectDate(Date date, View view) {
                if(daysUser.get(date.getTime()) != null){
                    if(daysUser.get(date.getTime())==true){
                        caldroidFragment.clearBackgroundDrawableForDate(date);
                        daysUser.put(date.getTime(), false);
                    }
                    else {
                        caldroidFragment.setBackgroundDrawableForDate( blue, date);
                        daysUser.put(date.getTime(), false);
                    }
                }
                else {
                    daysUser.put(date.getTime(), true);
                    caldroidFragment.setBackgroundDrawableForDate( blue, date);
                }
                caldroidFragment.refreshView();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                //Quand on sappe d'un mois à un autre
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                //Quand on clique longtemps sur une date
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    //on sait que la view est crée et on en  a la confiormation
                }
            }

        };
        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

}