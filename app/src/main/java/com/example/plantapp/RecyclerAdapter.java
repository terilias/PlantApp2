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

    private Context mContext;
    private List<Plant> mPlants;
    private LayoutInflater inflater;
    public RecyclerAdapter(Context context, List<Plant> plants){
        mContext=context;
        mPlants=plants;
        inflater = LayoutInflater.from(context);
    }
    /**
     * Το αντικείμενο ViewHolder περιέχει κάθε αντικείμενο προς εμφάνιση από το RecyclerView.Εδώ αναφέρεται στα item για εμφάνιση στην κάρτα (ως Views στο CardLayout).
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewCardName, textViewCardDate,textViewCardFertilDate;//τα view items που υπάρχουν στην κάρτα


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

        String name=mPlants.get(position).getPlantName();
        String date=mPlants.get(position).getplantingDate();
        String fertilDate=mPlants.get(position).getFertilDate();
        holder.textViewCardName.setText(name);
        holder.textViewCardDate.setText(date);
        holder.textViewCardFertilDate.setText(fertilDate);
    }

    @Override
    public int getItemCount() {
        return mPlants.size();
    }
}
