package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button nextActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinkingElements();
    }


    private void LinkingElements(){
        nextActivityButton = (Button) findViewById(R.id.NextActivityButton);
    }

    public void NextActivity(View v){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}