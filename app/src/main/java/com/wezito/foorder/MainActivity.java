package com.wezito.foorder;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Intent intent;
    private TextInputLayout email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        bindControls();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void bindControls() {
        email = (TextInputLayout) findViewById(R.id.txtEmail);
        pass = (TextInputLayout) findViewById(R.id.txtPass);
    }

    public void login(View view) {
        if (view.getId() == R.id.btnLogin) {
            boolean valid = validateLoginForm();
            if (valid) {
                String lMail = email.getEditText().getText().toString();
                String lPass = pass.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(lMail, lPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "Inicio de sesión exitoso!");
                                FirebaseUser user = mAuth.getCurrentUser();
                                loginAction(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "Inicio de sesión fallido!", task.getException());
                                Toast.makeText(MainActivity.this, "Error al iniciar sesión, intentelo nuevamente.",
                                        Toast.LENGTH_SHORT).show();
                                loginAction(null);
                            }
                        }
                    });
            }
        }
    }

    public void redirect(View view) {
        if (view.getId() == R.id.btnRegister) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private void loginAction(FirebaseUser user) {
        if (user != null) {
            intent = new Intent(this, HomeActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }

    private boolean validateLoginForm() {
        boolean valid = true;
        String lMail = email.getEditText().getText().toString();
        String lPass = pass.getEditText().getText().toString();
        if(lMail.isEmpty() || lPass.isEmpty()){
            Toast.makeText(MainActivity.this,"El correo electrónico y contraseña son obligatorios",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}