package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wezito.foorder.Adapters.OrderAdapter;
import com.wezito.foorder.Db.DbPedido;
import com.wezito.foorder.Model.Order;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {

    RecyclerView listaOrder;
    ArrayList<Order> orderArrayList;
    DbPedido dbPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        listaOrder = findViewById(R.id.listaOrder);
        listaOrder.setLayoutManager(new LinearLayoutManager(this));

        dbPedido = new DbPedido(ShoppingCartActivity.this);

        orderArrayList = new ArrayList<>();

        OrderAdapter orderAdapter = new OrderAdapter(dbPedido.showOrders());

        listaOrder.setAdapter(orderAdapter);
    }

    public void ConfirmOrder(View v){
        dbPedido.DeleteOrders();
        Toast.makeText(this, "Se ha confirmado su orden", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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