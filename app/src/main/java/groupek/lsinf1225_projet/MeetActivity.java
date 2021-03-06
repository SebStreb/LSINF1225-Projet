package groupek.lsinf1225_projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
        this.idFrom = idFrom;
        this.idTo = idTo;
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

        DatabaseHelper db = new DatabaseHelper(MeetActivity.this);
        //here came the loading of the profile pics------//TODO allow to uncomment this
        /*
        ImageView img1 = (ImageView)findViewById(R.id.imgRdv1);
        ImageView img2 = (ImageView)findViewById(R.id.imgRdv2);
        PhotoTable pic1 [] = db.getAllPhotos(this.idFrom);
        PhotoTable pic2 [] = db.getAllPhotos(this.idTo);
        img1.setImageBitmap(pic1[0].getImage());
        img2.setImageBitmap(pic2[0].getImage());
        */
        //end of the profile pics here-------------------



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

        Bundle b = getIntent().getExtras();

        this.idFrom = b.getInt("myID");
        this.idTo = b.getInt("hisID");

        Log.wtf("Ids recus: ", "FROM: "+this.idFrom+", TO: "+this.idTo);

        //initialising the hashtable and the colors of the calendar
        daysUser = new Hashtable<>();
        daysOther = new Hashtable<>();

        Date userD[] = db.getDispo(this.idFrom, this.idTo);
        for (Date d : userD) {
            daysUser.put(d.getTime(), true);
            caldroidFragment.setBackgroundDrawableForDate(blueUser, new Date(d.getTime()));
        }

        Date userO[] = db.getDispo(this.idTo, this.idFrom);
        for (Date d : userO) {
            daysOther.put(d.getTime(), true);
            if (into(d,userD)) {
                caldroidFragment.setBackgroundDrawableForDate(red, new Date(d.getTime()));
            } else {
                caldroidFragment.setBackgroundDrawableForDate(blueOther, new Date(d.getTime()));
            }

        }

        caldroidFragment.refreshView();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {
            /*
            * On actualise la vue and fonction des dates que l'utilisateur sélectionne
            */
            @Override
            public void onSelectDate(Date date, View view) {
                ColorDrawable col = null;
                try {
                     col = (ColorDrawable)view.getBackground();
                }
                catch(ClassCastException e){
                    daysUser.put(date.getTime(), true);
                    caldroidFragment.setBackgroundDrawableForDate(blueUser, date);
                }

                if(blueUser.equals(col)){
                    daysUser.put(date.getTime(), false);
                    caldroidFragment.clearBackgroundDrawableForDate(date);
                }
                else if (blueOther.equals(col)){
                    daysUser.put(date.getTime(), true);
                    caldroidFragment.setBackgroundDrawableForDate(red, date);
                }
                else if(red.equals(col)){
                    daysUser.put(date.getTime(), false);
                    caldroidFragment.setBackgroundDrawableForDate(blueOther, date);
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
                    //on sait que la view est crée et on en  a la confirmation
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

    @Override
    protected void onPause(){
        super.onPause();
        DatabaseHelper db = new DatabaseHelper(MeetActivity.this);
        db.saveDispo(this.idFrom, this.idTo, daysUser, daysOther);
    }

    private boolean into(Date d, Date[] l){
        for(Date t : l)
            if(t.equals(d))
                return true;
        return false;
    }
}