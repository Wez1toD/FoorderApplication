package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ClosedSubscriberGroupInfo;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void access(View view) {
        if (view.getId() == R.id.btnSignin) {
            intent = new Intent(this, LoginActivity.class);
        }
        else if (view.getId() == R.id.btnRegister) {
            intent = new Intent(this, RegisterActivity.class);
        }
        startActivity(intent);
    }
}