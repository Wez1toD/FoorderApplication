package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wezito.foorder.Adapters.FoodAdapter;
import com.wezito.foorder.Model.Food;
import com.wezito.foorder.View.FoodView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DishesActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private FoodAdapter fa;
    private FirebaseDatabase db;
    private DatabaseReference dishes;
    private FirebaseRecyclerAdapter<Food, FoodView> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);
        db = FirebaseDatabase.getInstance();
        dishes = db.getReference("foods").child("carte_dishes");
        rv = (RecyclerView) findViewById(R.id.rwDishes);
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        loadCarteDishes();
    }

    private void loadCarteDishes() {
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
            .setQuery(dishes, Food.class)
            .build();
        fa = new FoodAdapter(options, DishesActivity.this);
        rv.setAdapter(fa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fa.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fa.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.mnHome:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.mnCart:
                intent = new Intent(this, ShoppingCartActivity.class);
                break;
            case R.id.mnLogout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, MainActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }
}