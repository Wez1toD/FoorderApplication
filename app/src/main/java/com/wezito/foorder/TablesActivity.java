package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class TablesActivity extends AppCompatActivity {

    private HashMap<Integer, Integer> tableStatus = new HashMap<>();

    private TextView subtitleText;
    private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10, mesa11, mesa12;
    private Button[] tables;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        db = FirebaseDatabase.getInstance().getReference();

        LinkedElements();

        subtitleText.setText(Html.fromHtml("<u>Mesas</u>"));

        db.child("Mesas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(Button table : tables){
                        for(DataSnapshot ds : snapshot.getChildren()){
                            if(String.valueOf(table.getId()).equals(ds.getKey())){
                                int status = Integer.parseInt(ds.child("estado").getValue().toString());
                                tableStatus.put(table.getId(), status);
                                ChangeStateTable(table, status);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.mnHome:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.mnCart:
            case R.id.mnOrders:

                break;
            case R.id.mnLogout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, MainActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

    private void LinkedElements(){
        subtitleText = findViewById(R.id.Subtitle);

        LinkingTables();

        tables = new Button[]{mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10, mesa11, mesa12};

        for (Button table : tables) {
            tableStatus.put(table.getId(), 0);
        }
    }

    private void LinkingTables(){
        mesa1 = findViewById(R.id.mesa1);
        mesa2 = findViewById(R.id.mesa2);
        mesa3 = findViewById(R.id.mesa3);
        mesa4 = findViewById(R.id.mesa4);
        mesa5 = findViewById(R.id.mesa5);
        mesa6 = findViewById(R.id.mesa6);
        mesa7 = findViewById(R.id.mesa7);
        mesa8 = findViewById(R.id.mesa8);
        mesa9 = findViewById(R.id.mesa9);
        mesa10 = findViewById(R.id.mesa10);
        mesa11 = findViewById(R.id.mesa11);
        mesa12 = findViewById(R.id.mesa12);
    }

    public void CheckStateTable(View v){
        db.child("Mesas").child(String.valueOf(v.getId())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int status = Integer.parseInt(snapshot.child("estado").getValue().toString());
                    switch (status){
                        case 2:
                            AlertDialog occupedMessage = new AlertDialog
                                    .Builder(TablesActivity.this)
                                    .setNegativeButton("Aceptar", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .setTitle("Error")
                                    .setMessage("La mesa esta ocupada.")
                                    .create();
                            occupedMessage.show();
                            break;
                        case 0:
                            for(int clave : tableStatus.keySet()){
                                int valor = tableStatus.get(clave);
                                if(valor == 1){
                                    tableStatus.put(clave, 0);
                                    ChangeStateTable(findViewById(clave), 0);
                                }
                            }
                            tableStatus.put(v.getId(), 1);
                            ChangeStateTable(findViewById(v.getId()), 1);
                            break;
                        default:
                            Log.i("INFOX", "No encontrado");
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ChangeStateTable(Button b, int state){
        switch (state){
            case 0:
                b.setBackgroundColor(ContextCompat.getColor(this, R.color.unoccupied));
                break;
            case 1:
                b.setBackgroundColor(ContextCompat.getColor(this,R.color.selected));
                break;
            case 2:
                b.setBackgroundColor(ContextCompat.getColor(this,R.color.occupied));
        }
    }

    public void ConfirmarMesa(View v){
        int mesa_elegida = 0;
        for(int clave : tableStatus.keySet()){
            int valor = tableStatus.get(clave);
            if(valor == 1){
                mesa_elegida = clave;
            }
        }
        if(mesa_elegida != 0){
            db.child("Mesas").child(String.valueOf(mesa_elegida)).child("estado").setValue(2);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            AlertDialog occupedMessage = new AlertDialog
                    .Builder(TablesActivity.this)
                    .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setTitle("Error")
                    .setMessage("Elija una mesa para continuar.")
                    .create();
            occupedMessage.show();
        }
    }

}