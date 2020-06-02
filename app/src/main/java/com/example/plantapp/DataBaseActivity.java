package com.example.plantapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *Αυτή η δραστηριότητα αναλαμβάνει την αλληλεπίδραση του χρήστη με την βάση δεδομένων καλώντας τις απαραίτητες μεθόδους από την MyDBHandler για
 * να υλοποιήσει τις λειτουργίες δημιουργίας, ανάγνωσης και διαγραφής από τη βάση .
 */
public class DataBaseActivity extends MainActivity {
    //δήλωση των αντικειμένων του layout που θα χρησιμοποιηθούν για την προσθήκη λειτουργικότητας της εφαρμογής.
    EditText plantName,dateInput;
    TextView textViewInfo;
    Button buttonAdd,buttonSearch,buttonDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        //αποκτώ αναφορά στα view objects που θα μου χρειαστούν
        plantName=findViewById(R.id.editTextPlantName);
        dateInput=findViewById(R.id.dateInput);
        textViewInfo=findViewById(R.id.textViewInfo);
        textViewInfo.setVisibility(View.INVISIBLE);//το πεδίο στο οποίο θα εμφανιστούν οι πληροφορίες φυτού αρχικά τίθεται σε αόρατο
        buttonAdd=findViewById(R.id.buttonAdd);
        buttonSearch=findViewById(R.id.buttonSearch);
        buttonDel=findViewById(R.id.buttonDelete);
        //αλλαγή της μπάρας της εφαρμογής για να φαίνεται το όνομα της τρέχουσας δραστηριότητας
        getSupportActionBar().setTitle(R.string.second_menu);
        //ανάκτηση δεδομένων εισόδου για την περίπτωση αλλαγής κατάστασης της συσκευής (π.χ.rotate)
        if (savedInstanceState!=null){
            //παίρνουμε από το Bundle το όνομα και την ημερομηνία που έχει δώσει ο χρήστης στα πεδία εισόδου
            CharSequence name=savedInstanceState.getCharSequence("plant's name");
            CharSequence pdate=savedInstanceState.getCharSequence("plant's date");
            //αλλάζουμε το πεδίο κειμένου για να περιέχει το όνομα και την ημερομηνία που είχε τοποθετήσει ο χρήστης πριν από την αλλαγή κατάστασης της συσκευής
            plantName.setText(name);
            dateInput.setText(pdate);
        }
        else{
            //αφού δεν έχει αρχικοποίηση το Bundle σημαίνει ότι τα πεδία εισαγωγής δεδομένων ξεκινάνε με κενό περιεχόμενο άρα φορτώνονται αυτόματα οι έτοιμες τιμές στα πεδία (hints)
            /*Στη συνέχεια θα πάρουμε την τρέχουσα ημερομηνία και θα την τοποθετήσουμε στο
        πεδίο εισόδου ημερομηνίας σαν default τιμή. Μπορεί φυσικά ο χρήστης να αλλάξει
        την τιμή του για να προσθέσει ένα φυτό που φύτευσε παλαιότερα.*/
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            dateInput.setText(date);
        }

    }

    /**
     * H μέθοδος onClick για το addButton.
     */
    public void addPlant(View view){
        textViewInfo.setVisibility(View.INVISIBLE);//το πεδίο στο οποίο θα εμφανιστούν οι πληροφορίες φυτού τίθεται σε αόρατο σε περίπτωση που είναι ορατό
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        String name=plantName.getText().toString();//παίρνουμε το όνομα που έδωσε ο χρήστης
        //εάν το όνομα δεν είναι κενό, το ψάχνουμε στη βάση δεδομένων μας
        if(!name.equals("")){
            Plant found=dbHandler.findPlant(name);
            //εάν δεν υπάρχει στη βάση το προσθέτουμε και κάνουμε το editText να έχει κενό
            if(found==null){
                Plant plant=new Plant(plantName.getText().toString(),dateInput.getText().toString(),"no fertilizer");//αρχικοποίηση της στήλης ημερομηνίας λιπάσματος είναι no fertilizer
                dbHandler.addPlant(plant);
                Toast.makeText(getApplicationContext(), R.string.congrats, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.plantAlready, Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), R.string.pleaseInsertName, Toast.LENGTH_LONG).show();
        }
        plantName.setText("");
    }
    /**
     * H μέθοδος onClick για το searchButton.
     */
    public void searchPlant(View view){
        textViewInfo.setVisibility(View.INVISIBLE);//το πεδίο στο οποίο θα εμφανιστούν οι πληροφορίες φυτού τίθεται σε αόρατο σε περίπτωση που είναι ορατό
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        String name=plantName.getText().toString();//παίρνουμε το όνομα που έδωσε ο χρήστης
        //εάν το όνομα δεν είναι κενό, το ψάχνουμε στη βάση δεδομένων μας
        if(!name.equals("") ){
            Plant found=dbHandler.findPlant(name);
            //εάν υπάρχει στη βάση τότε παίρνουμε τα στοιχεία και θα τα στείλουμε προς εμφάνιση στο ShowPlantFragment διαμέσου της Main Activity
            if(found!=null){
                String plantName=String.valueOf(found.getPlantName());
                String plantDate=String.valueOf(found.getplantingDate());
                String fertilDate=String.valueOf(found.getFertilDate());
               //εμφάνιση των στοιχείων του φυτού σε ένα textView που by default είναι unvisible.
                textViewInfo.setVisibility(View.VISIBLE);
                if(fertilDate.equals("no fertilizer")){
                    //για κάποιο λόγο δεν καταφέραμε να κάνουμε setText με μήνυμα από το R.string γιατί εμφανίζει τους αριθμούς του id
                    // όταν το προσθέτουμε σε υπόλοιπα strings. Οπότε εδώ το μήνυμα είναι hardcoded
                    textViewInfo.setText("Το φυτό "+plantName+" φυτεύθηκε στις "+plantDate+" και δεν έχει δεχθεί ακόμη λίπασμα. Μπορείτε να διαχειριστείτε την λίπανση από την ειδική επιλογή του μενού.");
                }
                else{
                    textViewInfo.setText("Το φυτό "+plantName+" φυτεύθηκε στις "+plantDate+" και δέχθηκε τελευταία φορά λίπασμα στις "+fertilDate);
                }

            }
            else{
                Toast.makeText(getApplicationContext(), R.string.noPlantFound, Toast.LENGTH_LONG).show();

            }
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.pleaseInsertName, Toast.LENGTH_LONG).show();
        }
        plantName.setText("");

    }
    /**
     * H μέθοδος onClick για το delButton.
     */
    public void delPlant(View view){
        textViewInfo.setVisibility(View.INVISIBLE);//το πεδίο στο οποίο θα εμφανιστούν οι πληροφορίες φυτού τίθεται σε αόρατο σε περίπτωση που είναι ορατό
        final MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        final String name=plantName.getText().toString();//παίρνουμε το όνομα που έδωσε ο χρήστης
        //εάν το όνομα δεν είναι κενό και υπάρχει στη βάση δεδομένων, τότε εμφανίζεται μήνυμα προειδοποίησης για την επιβεβαίωση του χρήστη
        if(!name.equals("") ) {
            Plant found=dbHandler.findPlant(name);//αναζήτηση του φυτού στη βάση δεδομένων
            if(found!=null) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage(R.string.confirmMessage);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), R.string.noUnrooting, Toast.LENGTH_LONG).show();
                            }
                        });

                builder1.setNegativeButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                boolean result = dbHandler.deletePlant(name);
                                //εάν δεν υπάρχει στη βάση το προσθέτουμε και επαναφέρουμε το editText στην προηγούμενη κατάσταση, με το placeHolder
                                if (result) {
                                    //ενημέρωση ότι έγινε επιτυχής διαγραφή του φυτού
                                    Toast.makeText(getApplicationContext(), R.string.correctUnrooting, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.inCorrectUnrooting, Toast.LENGTH_LONG).show();
                                }

                            }


                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.noThisPlantFound, Toast.LENGTH_LONG).show();

            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.pleaseInsertName, Toast.LENGTH_LONG).show();
        }
        plantName.setText("");

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //σώζω στο Bundle object το περιεχόμενο του πεδίου εισαγωγής κειμένου για το όνομα του φυτού
        CharSequence name=plantName.getText();
        outState.putCharSequence("plant's name",name);
        CharSequence pdate=plantName.getText();
        outState.putCharSequence("plant's date",pdate);

    }
    /**
     * H μέθοδος αυτή εκτελείται για την τροποποίηση του μενού κατά το χρόνο εκτέλεσης της Activity.
     * Αλλάζει την εμφάνιση του menuItem που αντιστοιχεί στην τρέχουσα δραστηριότητα, μειώνοντας το alpha του για να φαίνεται μη επιλέξιμο και γίνεται μη επιλέξιμο.
     * @param menu το αντικείμενο του μενού
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.menu_add);

        if (this.getClass()==DataBaseActivity.class) {
            // γίνεται μη επιλέξιμο και αλλάζει η εμφάνισή του για να το επικοινωνεί αυτό στο χρήστη
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }
}
