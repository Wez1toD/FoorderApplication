package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /*
    public void NextActivityMenuToday(View v){
        Intent i = new Intent(this, TableActivity.class);
        startActivity(i);
    }
    public void NextActivityDishes(View v){
        Intent i = new Intent(this, TableActivity.class);
        startActivity(i);
    }
    */
    public void NextActivityTable(View v){
        Intent i = new Intent(this, TableActivity.class);
        startActivity(i);
    }
}