package com.example.plantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_appbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ConstraintLayout mainLayout = findViewById(R.id.MainLayoutView);

        switch (item.getItemId()) {

            case R.id.menu_home:
                Toast.makeText(getApplicationContext(), R.string.first_menu, Toast.LENGTH_SHORT).show();
                return true;


            case R.id.menu_add:
                Toast.makeText(getApplicationContext(), R.string.second_menu, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,DataBaseActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_gallery:
                Toast.makeText(getApplicationContext(), R.string.third_menu, Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
