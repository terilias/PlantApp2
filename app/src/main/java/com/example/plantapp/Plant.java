package com.example.plantapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Αυτή η κλάση είναι η Data Model Class, δηλαδή αναπαριστά τα αντικείμενα των φυτών για να αποθηκευθούν στη βάση δεδομένων.
 */
public class Plant {
    private int id;//id φυτού
    private String plantName;//όνομα φυτού
    private String plantingDate;//ημερομηνία φύτευσης
    private String fertilDate;//ημερομηνία που το φυτό δέχθηκε λίπασμα για τελευταία φορά

    public Plant(){

    }

    public Plant(String name,String plantingDate,String fertilDate) {
        this.plantName=name;
        this.plantingDate=plantingDate;
        this.fertilDate=fertilDate;
    }

    public String getFertilDate() {
        return fertilDate;
    }

    public void setFertilDate(String fertilDate) {
        this.fertilDate = fertilDate;
    }

    public String getplantingDate() {
        return plantingDate;
    }

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
