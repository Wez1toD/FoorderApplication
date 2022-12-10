package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
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

    public void NextActivityMenuToday(View v){
        AlertDialog occupedMessage = new AlertDialog
                .Builder(HomeActivity.this)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomeActivity.this, TodayMenuActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss())
                .setTitle("Aviso")
                .setMessage("Aún no está terminado")
                .create();
        occupedMessage.show();
    }

    public void NextActivityDishes(View v){
        AlertDialog occupedMessage = new AlertDialog
                .Builder(HomeActivity.this)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomeActivity.this, DishesActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss())
                .setTitle("Aviso")
                .setMessage("Aún no está terminado")
                .create();
        occupedMessage.show();

    }

    public void NextActivityTable(View v){
        Intent i = new Intent(this, TableActivity.class);
        startActivity(i);
    }
}