package com.example.plantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Η κύρια δραστηριότητα περιλαμβάνει το μενού και διαχειρίζεται την αλληλεπίδραση του χρήστη με αυτό. Την επεκτείνουν όλες οι υπόλοιπες δραστηριότητες που
 * θέλουμε να περιλαμβάνουν το μενού και την λειτουργικότητά του.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
     * Ανάλογα με την επιλογή του, εκτελεί και διαφορετικό block κώδικα (π.χ. ανοίγει νέα δραστηριότητα της εφαρμογής με explicit intent)
     * @param item
     * @return
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
