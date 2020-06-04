package com.example.plantapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Αυτή η δραστηριότητα αναλαμβάνει να παρέχει στον χρήστη την δυνατότητα να δηλώνει ότι ρίχνει λίπασμα στα φυτά του.
 * Αυτή η δυνατότητα της εφαρμογής είναι σημαντική γιατί με αυτό τον τρόπο ο χρήστης μπορεί να υπολογίζει και να διαχειρίζεται την λίπανση των φυτών του κήπου του.
 */
public class FertilizerActivity extends MainActivity {
    //δήλωση των αντικειμένων του layout που θα χρησιμοποιηθούν για την προσθήκη λειτουργικότητας της εφαρμογής.
    private EditText editTextFertilDate,editTextFertilName;
    private TextView textViewFertilStatus,textViewPlantName;
    private Button buttonFertil;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);
        //αποκτώ αναφορά στα view objects που θα μου χρειαστούν
        editTextFertilDate=findViewById(R.id.editTextFertilDate);
        editTextFertilName=findViewById(R.id.editTextFertilName);
        textViewFertilStatus=findViewById(R.id.textViewFertilStatus);
        textViewPlantName=findViewById(R.id.textView5);
        buttonFertil=findViewById(R.id.buttonFertil);
        aSwitch=findViewById(R.id.switch1);
        /*Στον παρακάτω listener για το Switch γίνεται προσαρμογή του layout ανάλογα με την κατάσταση του switch.
        Προεπιλογή: λειτουργία λίπανσης όλου του κήπου, άρα το πεδίο εισαγωγής ονόματος που απαιτείται στη λειτουργία λίπανσης μοναδικού φυτού πρέπει να είναι κρυφό.*/
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewFertilStatus.setText(R.string.textViewFertilStatus1);
                    textViewPlantName.setVisibility(View.INVISIBLE);
                    editTextFertilName.setVisibility(View.INVISIBLE);
                }
                else {
                    textViewPlantName.setVisibility(View.VISIBLE);
                    editTextFertilName.setVisibility(View.VISIBLE);
                    textViewFertilStatus.setText(R.string.textViewFertilStatus2);
                }
            }
        });
        //αλλαγή της μπάρας της εφαρμογής για να φαίνεται το όνομα της τρέχουσας δραστηριότητας
        getSupportActionBar().setTitle(R.string.menu_fertil);
        //ανάκτηση δεδομένων εισόδου για την περίπτωση αλλαγής κατάστασης της συσκευής (π.χ.rotate)
        if (savedInstanceState!=null){
            boolean state=savedInstanceState.getBoolean("switch state");
            aSwitch.setChecked(state);
            CharSequence pdate=savedInstanceState.getCharSequence("plant's fertilization date");
            editTextFertilDate.setText(pdate);
            /*Εάν είχε επιλέξει on στο switch σημαίνει ότι λιπαίνει όλα τα φυτά μαζί άρα τα πεδία συγκεκριμένου φυτού πρέπει να είναι αόρατα
            ενώ εάν ήταν στο off θέλουμε και τα πεδία αυτά όπως ήταν πριν συμπληρωμένα*/
            if (state) {
                textViewPlantName.setVisibility(View.INVISIBLE);
                editTextFertilName.setVisibility(View.INVISIBLE);
                textViewFertilStatus.setText(R.string.textViewFertilStatus1);
            }
            else {
                textViewPlantName.setVisibility(View.VISIBLE);
                editTextFertilName.setVisibility(View.VISIBLE);
                CharSequence name=savedInstanceState.getCharSequence("plant's name");
                editTextFertilName.setText(name);
                textViewFertilStatus.setText(R.string.textViewFertilStatus2);
            }
        }
        else{
            /*Αφού δεν έχει αρχικοποίηση το Bundle σημαίνει ότι τα πεδία εισαγωγής δεδομένων ξεκινάνε με κενό περιεχόμενο άρα φορτώνονται αυτόματα οι έτοιμες τιμές στα πεδία (hints).
            Στη συνέχεια θα πάρουμε την τρέχουσα ημερομηνία και θα την τοποθετήσουμε στο
            πεδίο εισόδου ημερομηνίας σαν default τιμή. Μπορεί φυσικά ο χρήστης να αλλάξει
            την τιμή του για να δηλώσει λίπανση που έγινε παλαιότερα.*/
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            editTextFertilDate.setText(date);
            //αρχικά είναι κρυφά τα πεδία του συγκεκριμένου φυτού αφού η προεπιλογή είναι η λίπανση κήπου
            textViewPlantName.setVisibility(View.INVISIBLE);
            editTextFertilName.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * OnClick Listener για το button λίπανσης. Χρησιμοποιεί τις μεθόδους ενημέρωσης των δεδομένων της βάσης από την κλάση MyDBHandler.
     * @param view το button
     */
    public void fertilize(View view) {
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        boolean state=aSwitch.isChecked();//η κατάσταση του switch
        if(state){
            //εάν βρισκόμαστε σε mode μαζικού λιπάσματος καλείται η μέθοδος fertilAllPlants από την myDBHandler
            dbHandler.fertilAllPlants(editTextFertilDate.getText().toString());
            Toast.makeText(getApplicationContext(), R.string.successFertilGarden, Toast.LENGTH_LONG).show();
        }
        else{
            //εάν βρισκόμαστε σε mode λιπάσματος συγκεκριμένου φυτού καλείται η μέθοδος fertilPlant από την myDBHandler
            //δεν ξεχνώ να περάσω την είσοδο του χρήστη από το "κόσκινο" της μεθόδου trim γιατί έχω εξασφαλίσει με την χρήση της ίδιας μεθόδου, ότι δεν θα μπει στη βάση φυτό με κενά πριν και μετά την ονομασία του
            if(!editTextFertilName.getText().toString().trim().equals("")) {
                //πρώτα αναζήτηση του φυτού στη βάση δεδομένων
                Plant found=dbHandler.findPlant(editTextFertilName.getText().toString());
                if(found==null){
                    //εάν το φυτό δεν υπάρχει, εμφανίζεται σχετικό μήνυμα
                    Toast.makeText(getApplicationContext(), R.string.noPlantFound, Toast.LENGTH_LONG).show();
                    editTextFertilName.setText("");
                }
                else {
                    //εάν το φυτό υπάρχει, το λιπαίνουμε καλώντας την αντίστοιχη μέθοο ενημέρωσης της βάσης
                    dbHandler.fertilPlant(editTextFertilName.getText().toString(), editTextFertilDate.getText().toString());
                    Toast.makeText(getApplicationContext(), R.string.successFertilPlant, Toast.LENGTH_LONG).show();
                    editTextFertilName.setText("");
                }
            }
            else{
                //εμφάνιση μηνύματος στην περίπτωση που δεν έχει εισαχθεί ονομασία
                Toast.makeText(getApplicationContext(), R.string.pleaseInsertName, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //σώζω στο Bundle object το περιεχόμενο του πεδίου εισαγωγής κειμένου και ημερομηνίας καθώς και την κατάσταση του switch
        CharSequence name=editTextFertilName.getText();
        outState.putCharSequence("plant's name",name);
        CharSequence date=editTextFertilDate.getText();
        outState.putCharSequence("plant's fertilization date",date);
        boolean state=aSwitch.isChecked();
        outState.putBoolean("switch state",state);

    }
    /**
     * H μέθοδος αυτή εκτελείται για την τροποποίηση του μενού κατά το χρόνο εκτέλεσης της Activity.
     * Αλλάζει την εμφάνιση του menuItem που αντιστοιχεί στην τρέχουσα δραστηριότητα, μειώνοντας το alpha του για να φαίνεται μη επιλέξιμο και γίνεται μη επιλέξιμο.
     * @param menu το αντικείμενο του μενού
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.menu_fertil);

        if (this.getClass()==FertilizerActivity.class) {
            // γίνεται μη επιλέξιμο και αλλάζει η εμφάνισή του για να το επικοινωνεί αυτό στο χρήστη
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }


}
