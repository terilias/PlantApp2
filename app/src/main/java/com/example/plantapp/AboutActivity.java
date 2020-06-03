package com.example.plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends MainActivity {
    TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        welcomeText=findViewById(R.id.textViewWelcome);
        welcomeText.setMovementMethod(LinkMovementMethod.getInstance());//μέθοδος για να δουλεύουν οι σύνδεσμοι του textView
        welcomeText.setMovementMethod(new ScrollingMovementMethod());/*μέθοδος για να μπορεί το textView να είναι scrollable. Μπορεί σε Portrait View να χωράει ακόμη περισσότερο κείμενο, αλλά σε Landscape υπάρχει
        πρόβλημα και το κείμενο κόβεται. Γιαυτό τον λόγο αναζητήσαμε στο διαδίκτυο και εντοπίσαμε ότι προσθέτοντας στο xml του textView την ιδιότητα android:scrollbars="vertical"
        και εφαρμόζωντας εδώ αυτή τη μέθοδο, μπορούμε να κάνουμε scrollable το κείμενο ώστε να εμφανίζεται χωρίς πρόβλημα.*/
    }

    /**
     * H μέθοδος αυτή εκτελείται για την τροποποίηση του μενού κατά το χρόνο εκτέλεσης της Activity.
     * Αλλάζει την εμφάνιση του menuItem που αντιστοιχεί στην τρέχουσα δραστηριότητα, μειώνοντας το alpha του για να φαίνεται μη επιλέξιμο και γίνεται μη επιλέξιμο.
     * @param menu το αντικείμενο του μενού
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.menu_info);

        if (this.getClass()==AboutActivity.class) {
            // γίνεται μη επιλέξιμο και αλλάζει η εμφάνισή του για να το επικοινωνεί αυτό στο χρήστη
            item.setEnabled(false);
            item.getIcon().setAlpha(130);

        }
        return true;
    }
}
