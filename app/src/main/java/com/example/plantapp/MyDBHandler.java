package com.example.plantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper {
    //σταθερές για τα SQL queries
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="plantDB.db";
    private static final String TABLE_PLANTS="plants";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_PLANTNAME="plantName";
    private static final String COLUMN_PLANTINGDATE="plantingDate";//πότε φυτεύθηκε
    private static final String COLUMN_FERTILDATE="fertilDate";//πότε ήταν η τελευταία φορά που λιπάνθηκε

    public MyDBHandler( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    //Δημιουργία του σχήματος της Βάσης Δεδομένων
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLANT_TABLE="CREATE TABLE "+
                TABLE_PLANTS+"(" +
                COLUMN_ID+" INTEGER PRIMARY KEY,"+
                COLUMN_PLANTNAME+ " TEXT,"+
                COLUMN_PLANTINGDATE+ " TEXT,"+
                COLUMN_FERTILDATE+" TEXT"+ ")";
        db.execSQL(CREATE_PLANT_TABLE);
    }

    /**
     * SOS: σε περίπτωση αλλαγής του σχήματος της βάσης θα πρέπει να ενημερώνεται η σταθερά DATABASE_VERSION (ουσιαστικά να αυξάνεται κατά ένα)!!!
     * Τα παρακάτω αποτελούν σημειώσεις-παράδειγμα προς αποφυγή από την εμπειρία:
     *   Όταν πρόσθεσα το πεδίο για την ημερομηνία λιπάσματος και είχα αφήσει τον αριθμό αυτόν ίσο με 1 δεν έτρεχε η λειτουργία της βάσης!
     *   Μέσα από τον Debugger ανακάλυψα ότι ο πίνακας είχε 3 στήλες και όχι 4.
     *   Όταν όμως δήλωσα το DATABASE_VERSION να είναι 2 λειτούργησε κανονικά!!!
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLANTS);
        onCreate(db);
    }

    /**
     * Μέθοδος για προσθήκη φυτού στην βάση δεδομένων.
     * @param plant οι τιμές στα πεδία του αντικειμένου δημιουργούν μια εγγραφή στη βάση
     */
    public void addPlant(Plant plant){
        ContentValues values=new ContentValues();
        values.put(COLUMN_PLANTNAME,plant.getPlantName());
        values.put(COLUMN_PLANTINGDATE,plant.getplantingDate());
        values.put(COLUMN_FERTILDATE,plant.getFertilDate());
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(TABLE_PLANTS,null,values);
        db.close();
    }

    /**
     * Mέθοδος για αναζήτηση ενός φυτού με την ονομασία του.
     * @param plantName η ονομασία του φυτού
     * @return plant το φυτό που ψάχνουμε
     */
    public Plant findPlant(String plantName){
        String query = "SELECT * FROM " + TABLE_PLANTS + " WHERE " +
                COLUMN_PLANTNAME + " = '" + plantName + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        Plant plant=new Plant();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            plant.setId(Integer.parseInt(cursor.getString(0)));
            plant.setPlantName(cursor.getString(1));
            plant.setplantingDate(cursor.getString(2));
            plant.setFertilDate(cursor.getString(3));
            cursor.close();
        }else{
            plant=null;
        }
        db.close();
        return plant;
    }

    /**
     * Mέθοδος για διαγραφή φυτού βάσει της ονομασίας του.
     * @param plantName
     * @return
     */
    public boolean deletePlant(String plantName){
        boolean result=false;
        String query = "SELECT * FROM " + TABLE_PLANTS + " WHERE " +
               COLUMN_PLANTNAME + " = '" + plantName + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        Plant plant=new Plant();
        if(cursor.moveToFirst()){
            plant.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PLANTS,COLUMN_ID+" = ?",new String[] {String.valueOf(plant.getId())});
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    /**
     * Υλοποιεί την λειουργία update για την τιμή της στήλης COLUMN_FERTILDATE ώστε να κρατάει την τελευταία ημερομηνία λίπανσης.
     * Η λίπανση γίνεται για ένα συγκεκριμένο φυτό.
     * @param name το όνομα για το φυτό που δέχεται το λίπασμα
     * @param date  η ημερομηνία που δέχθηκε το λίπασμα
     */
    public void fertilPlant(String name,String date){
        String query = "UPDATE "+ TABLE_PLANTS+" SET "+ COLUMN_FERTILDATE+" = "+date+ " WHERE "+COLUMN_PLANTNAME+ " = '"+ name+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
//        Plant plant=findPlant(name);
//        ContentValues values=new ContentValues();
//        values.put(COLUMN_PLANTNAME,plant.getPlantName());
//        values.put(COLUMN_PLANTINGDATE,plant.getplantingDate());
//        values.put(COLUMN_FERTILDATE,date);
//        SQLiteDatabase db=this.getWritableDatabase();
//        db.update(TABLE_PLANTS,values,COLUMN_ID+" = ?",new String[] {String.valueOf(plant.getId())});


    }

    /**
     * Υλοποιεί την λειουργία update για την τιμή της στήλης COLUMN_FERTILDATE ώστε να κρατάει την τελευταία ημερομηνία λίπανσης.
     * Η λίπανση γίνεται για όλα τα φυτά.
     * @param date  η ημερομηνία λίπανσης
     */
    public void fertilAllPlants(String date){
        ContentValues values=new ContentValues();
        values.put(COLUMN_FERTILDATE,date);
        SQLiteDatabase db=this.getWritableDatabase();
        db.update(TABLE_PLANTS,values,null,null);
        db.close();
    }
}
