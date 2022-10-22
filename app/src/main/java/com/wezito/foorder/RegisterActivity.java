package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void save(View view) {
        // TODO: Validar los campos.
        //  Guardar al usuario y retonar
        if (view.getId() == R.id.btnSave) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}