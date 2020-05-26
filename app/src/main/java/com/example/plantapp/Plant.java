package com.example.plantapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Αυτή η κλάση είναι η Data Model Class, δηλαδή αναπαριστά τα αντικείμενα των φυτών για να αποθηκευθούν στη βάση δεδομένων.
 */
public class Plant {
    private int id;
    private String plantName;
    private String plantingDate;

    public Plant(){

    }
    /**
     * Όταν δημιουργείται ένα αντικείμενο, η ημερομηνία τίθεται στην τρέχουσα ημέρα.
     */
    public Plant(String name,String date) {
        this.plantName=name;
        this.plantingDate=date;
    //ο σκοπός είναι να κρατάται η τρέχουσα ημερομηνία ως εξής:
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c);
//        this.plantingDate = formattedDate;
    }

    public String getplantingDate() {
        return plantingDate;
    }

    /**
     * Θέτει την ημερομηνία φύτευσης στην τρέχουσα ημερομηνία.
     */
    public void setplantingDate(String date) {
       this.plantingDate=date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
