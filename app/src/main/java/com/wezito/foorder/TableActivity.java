package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TableActivity extends AppCompatActivity {

    private Map<Integer, Integer> tablesStatus = new HashMap<>();

    private TextView subtitleText;
    private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10, mesa11, mesa12;
    private Button[] tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        LinkedElements();
        tablesStatus.put(mesa2.getId(), 2);

        for (Button table : tables) {
            ChangeStateTable(table);
        }
        subtitleText.setText(Html.fromHtml("<u>Mesas</u>"));
    }

    private void LinkedElements(){
        subtitleText = (TextView) findViewById(R.id.Subtitle);

        LinkingTables();

        tables = new Button[]{mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10, mesa11, mesa12};

        for(int i = 0; i < tables.length; i++){
            tablesStatus.put(tables[i].getId(), 0);
        }
    }

    private void LinkingTables(){
        mesa1 = (Button) findViewById(R.id.mesa1);
        mesa2 = (Button) findViewById(R.id.mesa2);
        mesa3 = (Button) findViewById(R.id.mesa3);
        mesa4 = (Button) findViewById(R.id.mesa4);
        mesa5 = (Button) findViewById(R.id.mesa5);
        mesa6 = (Button) findViewById(R.id.mesa6);
        mesa7 = (Button) findViewById(R.id.mesa7);
        mesa8 = (Button) findViewById(R.id.mesa8);
        mesa9 = (Button) findViewById(R.id.mesa9);
        mesa10 = (Button) findViewById(R.id.mesa10);
        mesa11 = (Button) findViewById(R.id.mesa11);
        mesa12 = (Button) findViewById(R.id.mesa12);
    }

    public void CheckStateTable(View v){
        if(tablesStatus.get(v.getId()) == 2){
            Toast.makeText(getApplicationContext(), "Mesa ocupada", Toast.LENGTH_LONG).show();
        }
        else if(tablesStatus.get(v.getId()) == 0){

            for(Integer i : tablesStatus.keySet()){
                if(tablesStatus.get(i) == 1){
                    tablesStatus.put(i, 0);
                    ChangeStateTable((Button) findViewById(i));
                }
            }

            tablesStatus.put(v.getId(), 1);
            ChangeStateTable((Button) findViewById(v.getId()));
        }
    }

    private void ChangeStateTable(Button b){
        if(tablesStatus.get(b.getId()) == 0){
            b.setBackgroundColor(Color.parseColor("#197A07"));
        }else if(tablesStatus.get(b.getId()) == 1){
            b.setBackgroundColor(Color.parseColor("#B58E05"));
        }else if(tablesStatus.get(b.getId()) == 2){
            b.setBackgroundColor(Color.parseColor("#931705"));
        }
    }

    public void showPopup(View v){

    }
}