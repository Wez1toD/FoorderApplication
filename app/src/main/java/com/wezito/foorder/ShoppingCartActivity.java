package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wezito.foorder.Adapters.OrderAdapter;
import com.wezito.foorder.Db.DbPedido;
import com.wezito.foorder.Model.Order;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView listaOrder;
    private DbPedido dbPedido;
    private DatabaseReference db;

    private boolean disponibilty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        listaOrder = findViewById(R.id.listaOrder);
        listaOrder.setLayoutManager(new LinearLayoutManager(this));

        dbPedido = new DbPedido(ShoppingCartActivity.this);

        OrderAdapter orderAdapter = new OrderAdapter(dbPedido.showOrders());
        listaOrder.setAdapter(orderAdapter);

        db = FirebaseDatabase.getInstance().getReference();
        db.child("Mesas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        if(Integer.parseInt(ds.child("estado").getValue().toString()) == 0){
                            disponibilty = true;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ConfirmOrder(View v){
        if(dbPedido.EmptyTable()){
            if(disponibilty == true){
                Intent intent = new Intent(this, TablesActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Lo sentimos, no hay mesas disponibles", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Su carrito está vacío. No se puede procesar su orden", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.mnBack:
                intent = new Intent(this, HomeActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }
}