package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        Button butnLogin = findViewById(R.id.btnLogin);
        butnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        TextView TVSwitchToSignup = findViewById(R.id.TVSignUp);
        TVSwitchToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSignup();
            }
        });
    }

    private void authenticateUser(){
        EditText ETLoginEmail = findViewById(R.id.loginEmail);
        EditText ETLoginPw = findViewById(R.id.loginPassword);

        String mail = ETLoginEmail.getText().toString();
        String passwd = ETLoginPw.getText().toString();

        if(mail.isEmpty()  || passwd.isEmpty()){
            Toast.makeText(this, "please fill all fields", Toast.LENGTH_LONG).show( );
            return;
        }

        mAuth.signInWithEmailAndPassword(mail, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToSignup(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}