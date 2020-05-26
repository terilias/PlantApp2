package com.example.plantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataBaseActivity extends AppCompatActivity {
    EditText plantName;
    TextView info;
    Button buttonAdd,buttonSearch,buttonDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //αποκτώ αναφορά στα view objects
        plantName=findViewById(R.id.editTextPlantName);
        info=findViewById(R.id.textViewInform);
        buttonAdd=findViewById(R.id.buttonAdd);
        buttonSearch=findViewById(R.id.buttonSearch);
        buttonDel=findViewById(R.id.buttonDelete);
        //ανάκτηση δεδομένων εισόδου για την περίπτωση αλλαγής κατάστασης της συσκευής (π.χ.rotate)
//        if (savedInstanceState!=null){
//            //παίρνουμε από το Bundle το όνομα που έχει δώσει ο χρήστης στο πεδίο κειμένου
//              CharSequence userText=savedInstanceState.getCharSequence("plant's name");
//            //αλλάζουμε το πεδίο κειμένου για να περιέχει το όνομα που είχε τοποθετήσει ο χρήστης πριν από την αλλαγή κατάστασης της συσκευής
//            plantName.setText(userText);
//        }
//        else{
//            //αφού δεν έχει αρχικοποίηση το Bundle σημαίνει ότι τα πεδία εισαγωγής δεδομένων ξεκινάνε με κενό περιεχόμενο
//            plantName.setText("");
//        }

        setContentView(R.layout.activity_data_base);
    }

    public void addPlant(View view){
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        String name=plantName.getText().toString();//παίρνουμε το όνομα που έδωσε ο χρήστης
        //εάν το όνομα δεν είναι κενό, το ψάχνουμε στη βάση δεδομένων μας
        if(!name.equals("")){
            Plant found=dbHandler.findPlant(name);
            //εάν δεν υπάρχει στη βάση το προσθέτουμε και επαναφέρουμε το editText στην προηγούμενη κατάσταση, με το placeHolder
            if(found==null){
                Plant plant=new Plant(name,"date");
                dbHandler.addPlant(plant);
                plantName.setText(" ");
                Toast.makeText(getApplicationContext(), "sucess!", Toast.LENGTH_SHORT).show();
            }
            else{
                //ενημέρωσε ότι το φυτό υπάρχει ήδη
            }

        }
        else {
            //εμφάνιση μηνύματος ότι δεν μπορεί να δώσει κενή επιλογή
        }
    }

    public void searchPlant(View view){
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        String name=plantName.getText().toString();//παίρνουμε το όνομα που έδωσε ο χρήστης
        //εάν το όνομα δεν είναι κενό, το ψάχνουμε στη βάση δεδομένων μας
        if(!name.equals("") ){
            Plant found=dbHandler.findPlant(name);
            //εάν υπάρχει στη βάση τότε παίρνουμε τα στοιχεία και θα τα στείλουμε προς εμφάνιση στο ShowPlantFragment διαμέσου της Main Activity
            plantName.setText(" ");
            if(found!=null){
                String plantName=String.valueOf(found.getPlantName());
                String plantDate=String.valueOf(found.getplantingDate());
               //Εκεί θα εμφανίσει τα στοιχεία του φυτού, χρησιμοποιώντας ένα ShowPlantFragment
                info.setText("ok,vrethike!");

            }
            else{
                //ενημέρωσε ότι το φυτό δεν υπάρχει
            }
        }
        else {
            //εμφάνιση μηνύματος ότι δεν μπορεί να δώσει κενή επιλογή
        }

    }

    public void delPlant(View view){
        MyDBHandler dbHandler=new MyDBHandler(this,null,null,1);//το αντικείμενο για την επικοινωνία με τη βάση δεδομένων
        String name=plantName.getText().toString();//παίρνουμε το όνομα που έδωσε ο χρήστης
        //εάν το όνομα δεν είναι κενό, το ψάχνουμε στη βάση δεδομένων μας
        if(!name.equals("") ){
            boolean result=dbHandler.deletePlant(name);
            //εάν δεν υπάρχει στη βάση το προσθέτουμε και επαναφέρουμε το editText στην προηγούμενη κατάσταση, με το placeHolder
            if(result){
                //ενημέρωση ότι έγινε επιτυχής διαγραφή του φυτού
                plantName.setText(" ");

            }
            else{
                //ενημέρωσε ότι το φυτό δεν υπάρχει
            }

        }
        else {
            //εμφάνιση μηνύματος ότι δεν μπορεί να δώσει κενή επιλογή
        }

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//        //σώζω στο Bundle object το περιεχόμενο του πεδίου εισαγωγής κειμένου για το όνομα του φυτού
//        CharSequence userText=plantName.getText();
//        outState.putCharSequence("plant's name",userText);
//
//    }
}
