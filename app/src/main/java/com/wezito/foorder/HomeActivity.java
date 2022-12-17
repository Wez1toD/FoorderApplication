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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wezito.foorder.Db.DbHelper;
import com.wezito.foorder.Db.DbPedido;

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
                startActivity(intent);
                break;
            case R.id.mnCart:
                intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                break;
            case R.id.mnLogout:
                mAuth.signOut();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.mnAdmin:
                AlertDialog.Builder dialogBuilder;
                AlertDialog dialog;

                EditText user, pass;
                Button btnLoginAdmin;
                ImageView imgAdminProfile;

                dialogBuilder = new AlertDialog.Builder(this);
                final View loginAdminPopUp = getLayoutInflater().inflate(R.layout.popup_login_admin, null);
                user = loginAdminPopUp.findViewById(R.id.inputUserAdmin);
                pass = loginAdminPopUp.findViewById(R.id.inputUserPassword);
                btnLoginAdmin = loginAdminPopUp.findViewById(R.id.btnLoginAdmin);
                imgAdminProfile = loginAdminPopUp.findViewById(R.id.imgAdminProfile);
                Picasso.get().load("https://www.pngmart.com/files/21/Admin-Profile-PNG.png").into(imgAdminProfile);

                dialogBuilder.setView(loginAdminPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                DatabaseReference dbAdmin = FirebaseDatabase.getInstance().getReference().child("admins");
                btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot ds : snapshot.getChildren()){
                                        if(user.getText().toString().equals(ds.getKey())){
                                            if(pass.getText().toString().equals(ds.getValue())){
                                                Toast.makeText(HomeActivity.this, "Ingresando...", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(HomeActivity.this, AdministrationActivity.class);
                                                startActivity(i);
                                            }else{
                                                Toast.makeText(HomeActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
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
                });
                break;
        }
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