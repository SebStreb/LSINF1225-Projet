package groupek.lsinf1225_projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

@SuppressLint("SimpleDateFormat")
public class MeetActivity extends AppCompatActivity {
    //to color the day
    /*
    *   BlueUser: #4169E1 - Royal Blue - The color used when the user select a day
    *   BlueOther: #87CEEB - Sky Blue - The color used to show the user whitch dates have been picked by the other user
    *   Red: #B22222 - Fire Brick - The color used when they both agree on the same date
    */
    final ColorDrawable blueOther = new ColorDrawable(Color.parseColor("#87CEEB"));
    final ColorDrawable blueUser = new ColorDrawable(Color.parseColor("#4169E1"));
    final ColorDrawable red = new ColorDrawable(Color.parseColor("#B22222"));
    //internal var to handle the parameters and get the correct dates from the db
    int idFrom, idTo;
    Context context;
    //TODO load here all the dates from the other one
    //TODO load here all the dates from the current user
    //TODO load the date sof the two user into the callendar
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    //the hashtables used to know who chose what when
    private Hashtable<Long, Boolean> daysUser;
    private Hashtable<Long, Boolean> daysOther;

    //this is a default handler, it only purpose is to handle the bad request
    public MeetActivity() {
        Log.wtf("Meetacticty", "Bad request to MeetActuvity, 0 args");
    }

    //this is a default handler, it only purpose is to handle the bad request
    public MeetActivity(int idFrom) {
        this.idFrom = idFrom;
        Log.wtf("Meetacticty", "Bad request to MeetActuvity, 1 arg");
    }

    public MeetActivity(int idFrom, int idTo, Context context) {
        this.context = context;
    }


    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);

        // Max date is next 7 days
        cal.add(Calendar.DATE, 7);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        caldroidFragment = new CaldroidFragment();


        //seting the mindate 1 days before the current day
        Calendar calM = Calendar.getInstance();
        calM.setTime(new Date());
        calM.add(Calendar.DATE, 0);
        caldroidFragment.setMinDate(calM.getTime());

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE");
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

        if(savedInstanceState!=null) {
            this.idFrom = (int) savedInstanceState.get("myID");
            this.idTo = (int) savedInstanceState.get("thisID");
        }
        else{
            this.idFrom = -1;
            this.idTo = -1;
        }

/*
        //initialising the hashtable and the colors of the calendar
        daysUser = new Hashtable<>();
        daysOther = new Hashtable<>();
        Date userD[] = searchDatabase(this.idFrom, this.idTo);
        for (Date d : userD) {
            daysUser.put(d.getTime(), true);
            caldroidFragment.setBackgroundDrawableForDate(blueUser, new Date(d.getTime()));
        }
        Date userO[] = searchDatabase(this.idTo, this.idFrom);
        for (Date d : userO) {
            daysOther.put(d.getTime(), true);
            if (daysUser.get(t) != null) {
                caldroidFragment.setBackgroundDrawableForDate(red, new Date(d.getTime()));
            } else {
                caldroidFragment.setBackgroundDrawableForDate(blueOther, new Date(d.getTime()));
            }
        }
*/
        caldroidFragment.refreshView();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            /*
            * On actualise la vue and fonction des dates que l'utilisateur sélectionne
            */
            @Override
            public void onSelectDate(Date date, View view) {
                if (daysUser.get(date.getTime()) != null) {//the user has already click on this date
                    if (daysUser.get(date.getTime()) == true) {
                        if (daysOther.get(date.getTime()) != null && daysOther.get(date.getTime()) == true) {
                            caldroidFragment.setBackgroundDrawableForDate(blueOther, date);
                        } else {
                            caldroidFragment.clearBackgroundDrawableForDate(date);
                        }
                        daysUser.put(date.getTime(), false);
                    } else {
                        if (daysOther.get(date.getTime()) != null && daysOther.get(date.getTime()) == true) {
                            caldroidFragment.setBackgroundDrawableForDate(blueOther, date);
                        } else {
                            caldroidFragment.setBackgroundDrawableForDate(blueUser, date);
                        }
                        daysUser.put(date.getTime(), true);
                    }
                } else {//the user has never click on this date
                    daysUser.put(date.getTime(), true);
                    if (daysOther.get(date.getTime()) != null && daysOther.get(date.getTime()) == true) {
                        caldroidFragment.setBackgroundDrawableForDate(blueOther, date);
                    } else {
                        caldroidFragment.setBackgroundDrawableForDate(blueUser, date);
                    }
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

    public Date[] searchDatabase(int from, int to) {
        DatabaseHelper dbh = new DatabaseHelper(MeetActivity.this);
        SQLiteDatabase db = dbh.open();
        String[] param = {""+from, "" +to};
        Cursor cursor = db.rawQuery("SELECT strftime('%s', d.Jour) FROM dispo d WHERE d.ID_from = ? AND d.ID_to = ?\n", param);
        Date[] donnees = new Date[cursor.getCount()];
        for (int i = 0; i < donnees.length; i++) {
            donnees[i] = new Date(cursor.getLong(i));
        }
        cursor.close();
        return donnees;
    }

}