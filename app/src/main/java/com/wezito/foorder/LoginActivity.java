package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        //TODO: Validar que los se haya ingresado teléfono y contraseña.
        if (view.getId() == R.id.btnLogin) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}