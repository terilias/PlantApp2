package com.example.plantapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Σε αυτή τη δραστηριότητα ο χρήστης μπορεί να δει τα περιεχόμενα του κήπου του, δηλαδή της βάσης δεδομένων.
 * Για αυτό το λόγο χρησιμοποιείται ένα RecyclerView με κάρτες, και σε κάθε μία από αυτές εμφανίζονται τα δεδομένα κάθε φυτού.
 */
public class GalleryActivity extends MainActivity {

    private  MyDBHandler dbHandler;//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
    private RecyclerView recyclerView;//το recyclerView που θα εμφανίζει τα περιεχόμενα της βάσης σε αυτήν την activity
    private RecyclerView.LayoutManager layoutManager;//layoutManager για την εμφαιση των καρτών στο recyclerView
    private RecyclerView.Adapter adapter;//το αντικείμενο adapter για την μεταφορά των δεδομένων στο RecyclerView μέσω του ViewHolder
    private TextView textView;//το πεδίο κειμένου όπου εμφανίζεται το σχετικό μήνυμα εάν δεν υπάρχουν προς το παρόν φυτά.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        //αλλαγή της μπάρας της εφαρμογής για να φαίνεται το όνομα της τρέχουσας δραστηριότητας
        getSupportActionBar().setTitle(R.string.third_menu);
        //δημιουργία του αντικειμένου για την σύνδεση με τη βάση
        dbHandler=new MyDBHandler(this,null,null,1);
        //αναφορά στο textView
        textView=findViewById(R.id.textViewInfoGallery);
        textView.setVisibility(View.INVISIBLE);
        if(dbHandler.getPlantsCount()==0){
            //εάν δεν υπάρχουν αντικείμενα στη βάση δεδομένων εμφανίζουμε το textView για την ενημέρωση του χρήστη
            textView.setVisibility(View.VISIBLE);
        }
        //αναφορά στο recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        //θέτουμε το layout των items στο RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //θέτουμε τον Adapter για το RecyclerView. Είναι το αντικείμενο της κλάσης RecyclerAdapter.
        adapter = new RecyclerAdapter(this,dbHandler.getPlants());
        recyclerView.setAdapter(adapter);
    }

    /**
     * H μέθοδος αυτή εκτελείται για την τροποποίηση του μενού κατά το χρόνο εκτέλεσης της Activity.
     * Αλλάζει την εμφάνιση του menuItem που αντιστοιχεί στην τρέχουσα δραστηριότητα, μειώνοντας το alpha του για να φαίνεται μη επιλέξιμο και γίνεται μη επιλέξιμο.
     * @param menu το αντικείμενο του μενού
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.menu_gallery);

        if (this.getClass()==GalleryActivity.class) {
            // γίνεται μη επιλέξιμο και αλλάζει η εμφάνισή του για να το επικοινωνεί αυτό στο χρήστη
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }


}