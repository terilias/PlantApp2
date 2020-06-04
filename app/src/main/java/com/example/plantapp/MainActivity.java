package com.example.plantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Η κύρια δραστηριότητα περιλαμβάνει το μενού και διαχειρίζεται την αλληλεπίδραση του χρήστη με αυτό. Την επεκτείνουν όλες οι υπόλοιπες δραστηριότητες που
 * θέλουμε να περιλαμβάνουν το μενού και την λειτουργικότητά του.
 */
public class MainActivity extends AppCompatActivity {
    TextView howMany;
    @Override
     /**
     * Η onCreate της MainActivity εμφανίζει μήνυμα με το πλήθος των εγγραφών του χρήστη στον πίνακα της βάσης δεδομένων.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        howMany=findViewById(R.id.textViewHowMany);
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        long numOfPlants=dbHandler.getPlantsCount();//ο αριθμός των φυτών
        //ανάλογα με τον αριθμό των φυτών εμφανίζεται κι ένα προσαρμοσμένο μήνυμα
        if(numOfPlants==0){
            howMany.setText(R.string.noPlants);
        }
        else if (numOfPlants==1){
            howMany.setText(R.string.onePlant);
        }
        else if (numOfPlants<10) {
                //για κάποιο λόγο δεν καταφέραμε να κάνουμε setText με μήνυμα από το R.string γιατί εμφανίζει τους αριθμούς του id όταν το προσθέτουμε σε υπόλοιπα strings. Οπότε εδώ το μήνυμα είναι hardcoded
                howMany.setText("Έχετε φυτέψει επιτυχώς " + numOfPlants + " φυτά στον κήπο σας! Συγχαρητήρια! Συνεχίστε έτσι!");
        }
        else{
            howMany.setText("Αυτή τη στιγμή έχετε "+numOfPlants+" φυτά στον κήπο σας.");
        }

    }

    /**
     * Απαραίτητη μέθοδος για την εμφάνιση του μενού στo activity
     * @param menu το μενού που έχουμε δημιουργήσει
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_appbar,menu);
        return true;
    }

    /**
     * Η μέθοδος αυτή διαχειρίζεται τα clicks του χρήστη στις επιλογές του μενού.
     * Ανάλογα με την επιλογή του, εκτελεί και διαφορετικό block κώδικα (ανοίγει νέα δραστηριότητα της εφαρμογής με explicit intent)
     * @param item η επιλογή του χρήστη
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ConstraintLayout mainLayout = findViewById(R.id.MainLayoutView);
        switch (item.getItemId()) {

            case R.id.menu_home:
                Intent i1=new Intent(this,MainActivity.class);
                startActivity(i1);
                return true;


            case R.id.menu_add:
                Intent i2=new Intent(this,DataBaseActivity.class);
                startActivity(i2);
                return true;

            case R.id.menu_fertil:
                Intent i3=new Intent(this,FertilizerActivity.class);
                startActivity(i3);
                return true;

            case R.id.menu_gallery:
                Intent i4=new Intent(this,GalleryActivity.class);
                startActivity(i4);
                return true;

            case R.id.menu_info:
                Intent i5=new Intent(this,AboutActivity.class);
                startActivity(i5);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * H μέθοδος αυτή εκτελείται για την τροποποίηση του μενού κατά το χρόνο εκτέλεσης της Activity.
     * Αλλάζει την εμφάνιση του menuItem που αντιστοιχεί στην τρέχουσα δραστηριότητα, μειώνοντας το alpha του για να φαίνεται μη επιλέξιμο και γίνεται μη επιλέξιμο.
     * @param menu το αντικείμενο του μενού
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.menu_home);

        if (this.getClass()==MainActivity.class) {
            // γίνεται μη επιλέξιμο και αλλάζει η εμφάνισή του για να το επικοινωνεί αυτό στο χρήστη
            item.setEnabled(false);
            item.getIcon().setAlpha(130);

        }
        return true;
    }

}
