package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wezito.foorder.Db.DbHelper;
import com.wezito.foorder.Db.DbPedido;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        bundle = getIntent().getExtras();
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
        if(item.getItemId() != R.id.mnHome){startActivity(intent);}
        return true;
    }

    public void redirect(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnTodayMenu:
                intent = new Intent(this, TodayMenuActivity.class);
                break;
            case R.id.btnDishes:
                intent = new Intent(this, DishesActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}