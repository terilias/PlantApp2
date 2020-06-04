package com.example.plantapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.plantapp.MyDBHandler.COLUMN_FERTILDATE;
import static com.example.plantapp.MyDBHandler.COLUMN_PLANTINGDATE;
import static com.example.plantapp.MyDBHandler.COLUMN_PLANTNAME;

/**
 * Η κλάση Adapter για την εμφάνιση των αντικειμένων στο RecyclerView.
 * Υποχρεωτικά επεκτείνει την RecyclerView.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;//το context της δραστηριότητας GalleryActivity
    private List<Plant> mPlants;//λίστα με τα φυτά, η οποία θα γεμίσει με τα αντικείμενα της βάσης
    private LayoutInflater inflater;//LayoutInflater από την δραστηριότητα GalleryActivity

    /**
     * Ο κατασκευαστής του RecyclerAdapter. Δέχεται ως είσοδο:
     * @param context το context της "μητέρας" activity
     * @param plants //λίστα με τα φυτά που υπάρχουν στην βάση δεδομένων
     */
    public RecyclerAdapter(Context context, List<Plant> plants){
        mContext=context;
        mPlants=plants;
        inflater = LayoutInflater.from(context);
    }
    /**
     * Το αντικείμενο ViewHolder περιέχει κάθε αντικείμενο προς εμφάνιση στο RecyclerView. Εδώ αναφέρεται στα item για εμφάνιση στην κάρτα (ως Views στο CardLayout).
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        //τα view items που υπάρχουν στην κάρτα
        TextView textViewCardName;
        TextView textViewCardDate;
        TextView textViewCardFertilDate;


        /**
         * Ο κατασκευαστής του αντικειμένου ViewHolder.
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //δημιουργούμε αναφορές στα στοιχεία της κάρτας
            textViewCardName=itemView.findViewById(R.id.textView8);
            textViewCardDate=itemView.findViewById(R.id.textView9);
            textViewCardFertilDate=itemView.findViewById(R.id.textView10);
        }
    }

    /**
     * Στην συνέχεια υπάρχουν οι μέθοδοι που απαιτούνται να υλοποιηθούν από την RecyclerView.Adapter
     */

    @NonNull
    @Override
     /**
     * Γίνεται  inflate στο ViewHolder με το layout της κάρτας.
     */
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Η μέθοδος που καλείται κατά την εμφάνιση του ViewHolder στο RecyclerVew.
     * @param holder ο ViewHolder
     * @param position θέση στο RecycleView
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        //από κάθε στοιχείο της λίστας με τα φυτά, πέρνουμε τα δεδομένα του φυτού για εμφάνιση στην κάρτα, αφού κάθε κάρτα αντιστοιχεί και σε ένα αντικείμενο Plant
        String name=mPlants.get(position).getPlantName();
        String date=mPlants.get(position).getplantingDate();
        String fertilDate=mPlants.get(position).getFertilDate();
        //αφού πήραμε τα δεδομένα του φυτού, τα τοποθετοπύμε στα items του viewHolder
        holder.textViewCardName.setText(name);
        holder.textViewCardDate.setText(date);
        holder.textViewCardFertilDate.setText(fertilDate);
    }

    /**
     * Επιστρέφει τον αριθμό των αντικειμένων Plant στη λίστα.
     * @return το μέγεθος της λίστας με τα φυτά
     */
    @Override
    public int getItemCount() {
        return mPlants.size();
    }
}
