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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wezito.foorder.Adapters.FoodAdapter;
import com.wezito.foorder.Model.Food;
import com.wezito.foorder.View.FoodView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayMenuActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private FoodAdapter fa;
    private FirebaseDatabase db;
    private DatabaseReference todaymenu;
    private FirebaseRecyclerAdapter<Food, FoodView> adapter;
    private DateFormat dateFormat;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_menu);
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = new Date();
        String currentDate = dateFormat.format(date);
        db = FirebaseDatabase.getInstance();
        todaymenu = db.getReference("todaymenu").child(currentDate).child("mains");
        rv = (RecyclerView) findViewById(R.id.rwFoods);
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        loadTodaymenu();
    }

    private void loadTodaymenu() {
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
            .setQuery(todaymenu, Food.class)
            .build();
        fa = new FoodAdapter(options, TodayMenuActivity.this);
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

                break;
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
}