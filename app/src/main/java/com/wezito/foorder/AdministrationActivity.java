package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdministrationActivity extends AppCompatActivity {

    private TextView textMesa1,textMesa2,textMesa3,textMesa4,textMesa5,textMesa6,textMesa7,textMesa8,textMesa9,textMesa10,textMesa11,textMesa12;
    private Button btnMesa1,btnMesa2,btnMesa3,btnMesa4,btnMesa5,btnMesa6,btnMesa7,btnMesa8,btnMesa9,btnMesa10,btnMesa11,btnMesa12;
    private TextView[] textTables;
    private Button[] btnTables;

    private HashMap<TextView, int[]> statusTables = new HashMap<>();

    private DatabaseReference dbTables, dbOrders;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        LinkingControls();

        dbTables = FirebaseDatabase.getInstance().getReference().child("Mesas");
        dbOrders = FirebaseDatabase.getInstance().getReference().child("orders");



        dbOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        int t = Integer.parseInt(ds.child("table_num").getValue().toString());
                        int s = Integer.parseInt(ds.child("status").getValue().toString());
                        TextView textTableToChange = null;
                        for(TextView tv : statusTables.keySet()){
                            int[] k = statusTables.get(tv);
                            if(k[0] == t){
                                textTableToChange = tv;
                                break;
                            }
                        }
                        if(s == 1){
                            if(textTableToChange != null) TextStatusChange(textTableToChange, 1);
                        }else if(s == 2){
                            dbOrders.child(ds.getKey()).removeValue();
                            if(textTableToChange != null) TextStatusChange(textTableToChange, 0);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void LinkingControls(){
        //region Buttons tables link
        btnMesa1 = findViewById(R.id.btnMesaOrder1);
        btnMesa2 = findViewById(R.id.btnMesaOrder2);
        btnMesa3 = findViewById(R.id.btnMesaOrder3);
        btnMesa4 = findViewById(R.id.btnMesaOrder4);
        btnMesa5 = findViewById(R.id.btnMesaOrder5);
        btnMesa6 = findViewById(R.id.btnMesaOrder6);
        btnMesa7 = findViewById(R.id.btnMesaOrder7);
        btnMesa8 = findViewById(R.id.btnMesaOrder8);
        btnMesa9 = findViewById(R.id.btnMesaOrder9);
        btnMesa10 = findViewById(R.id.btnMesaOrder10);
        btnMesa11 = findViewById(R.id.btnMesaOrder11);
        btnMesa12 = findViewById(R.id.btnMesaOrder12);
        btnTables = new Button[]{btnMesa1,btnMesa2,btnMesa3,btnMesa4,btnMesa5,btnMesa6,btnMesa7,btnMesa8,btnMesa9,btnMesa10,btnMesa11,btnMesa12};
        //endregion
        //region TextView tables status link
        textMesa1 = findViewById(R.id.btnMesaStatus1);
        textMesa2 = findViewById(R.id.btnMesaStatus2);
        textMesa3 = findViewById(R.id.btnMesaStatus3);
        textMesa4 = findViewById(R.id.btnMesaStatus4);
        textMesa5 = findViewById(R.id.btnMesaStatus5);
        textMesa6 = findViewById(R.id.btnMesaStatus6);
        textMesa7 = findViewById(R.id.btnMesaStatus7);
        textMesa8 = findViewById(R.id.btnMesaStatus8);
        textMesa9 = findViewById(R.id.btnMesaStatus9);
        textMesa10 = findViewById(R.id.btnMesaStatus10);
        textMesa11 = findViewById(R.id.btnMesaStatus11);
        textMesa12 = findViewById(R.id.btnMesaStatus12);
        textTables = new TextView[]{textMesa1,textMesa2,textMesa3,textMesa4,textMesa5,textMesa6,textMesa7,textMesa8,textMesa9,textMesa10,textMesa11,textMesa12};
        for(int i = 0; i < textTables.length; i++){
            statusTables.put(textTables[i], new int[]{i + 1 , 0});
        }
        for(TextView t : textTables){
            TextStatusChange(t, 0);
        }
        //endregion
    }
    public void ReleaseTables(View v){
        dbTables.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        dbTables.child(ds.getKey()).child("estado").setValue(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void OccupyTables(View v){
        dbTables.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        dbTables.child(ds.getKey()).child("estado").setValue(2);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void TextStatusChange(TextView t, int s){
        switch (s) {
            case 0:
                t.setTextColor(getResources().getColor(R.color.unoccupied));
                t.setTypeface(null, Typeface.NORMAL);
                t.setText("Sin ordenes");
                break;
            case 1:
                t.setTextColor(getResources().getColor(R.color.selected));
                t.setTypeface(null, Typeface.BOLD);
                t.setText("Pendiente");
                break;
        }
    }
}