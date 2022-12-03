package com.wezito.foorder;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wezito.foorder.model.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference rDatabase;
    private Intent intent;
    private TextInputLayout email, name, pass, confirmPass;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        rDatabase = FirebaseDatabase.getInstance().getReference();
        bindControls();
    }

    private void bindControls() {
        email = (TextInputLayout) findViewById(R.id.txtEmail);
        name = (TextInputLayout) findViewById(R.id.txtName);
        pass = (TextInputLayout) findViewById(R.id.txtPass);
        confirmPass = (TextInputLayout) findViewById(R.id.txtConfirmPass);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //FirebaseAuth.getInstance().signOut();
        if(currentUser != null) {
            intent = new Intent(this, MainActivity.class);
            redirect(intent);
        }
    }

    private void redirect(Intent intent) {
        startActivity(this.intent);
    }

    public void save(View view) {
        // TODO: Validar los campos
        boolean valid = validateRegisterForm();
        Log.i("INFOX", String.valueOf(valid));
        if (valid) {
            String uname = name.getEditText().getText().toString();
            String mail = email.getEditText().getText().toString();
            String password = pass.getEditText().getText().toString();
            mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Creación de usuario: Exitoso!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser(user, uname, mail);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Creación de usuario: Fallido!", task.getException());
                            Toast.makeText(RegisterActivity.this, "Autenticación fallida.",
                                    Toast.LENGTH_SHORT).show();
                            //createUser(null, uname, mail);
                        }
                    }
                });
        }
    }

    private boolean validateRegisterForm() {
        boolean valid = true;
        String uname = name.getEditText().getText().toString();
        String mail = email.getEditText().getText().toString();
        String password = pass.getEditText().getText().toString();
        String cPassword = confirmPass.getEditText().getText().toString();
        if(uname.isEmpty()){
            Toast.makeText(RegisterActivity.this,"El nombre es obligatorio",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            Toast.makeText(RegisterActivity.this,"El correo electrónico no es valido",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(password.isEmpty() || cPassword.isEmpty() || !password.equals(cPassword)){
            Toast.makeText(RegisterActivity.this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    /*
     * Create new user.
     */
    private void createUser(FirebaseUser user, String uname, String mail) {
        if (user != null) {
            String uid = user.getUid();
            User newUser = new User(uid, uname, mail, "eater");
            rDatabase.child("users").child(uid).setValue(newUser);
            FirebaseAuth.getInstance().signOut();
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, RegisterActivity.class);
        }
        redirect(intent);
    }
}