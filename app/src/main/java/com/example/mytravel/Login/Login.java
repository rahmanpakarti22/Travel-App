package com.example.mytravel.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.GetStarted.GetStarted;
import com.example.mytravel.R;
import com.example.mytravel.RegisterAkun.RegistrasiSatu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity
{

    Button btn_regis, btn_login;
    EditText inputPassword, inputUsername;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.login);

        btn_login     = findViewById(R.id.btn_login);
        btn_regis     = findViewById(R.id.btn_regis);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);

        btn_regis.setOnClickListener(v -> {
           startActivity(new Intent(Login.this, RegistrasiSatu.class));
           finish();
        });

        btn_login.setOnClickListener(v -> {
            btn_login.setEnabled(false);
            btn_login.setText("Loading..");

            final String username = inputUsername.getText().toString();
            final String password = inputPassword.getText().toString();

            reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username);
            reference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {

                        String passwordFromFirebase = Objects.requireNonNull(snapshot.child("password_pengguna").getValue()).toString();
                        if (password.equals(passwordFromFirebase))
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, inputUsername.getText().toString());
                            editor.apply();

                            startActivity(new Intent(Login.this, SuksesLogAct.class));
                            finish();
                        }

                        else
                        {
                            Toast.makeText(Login.this, "Password Salah", Toast.LENGTH_SHORT).show();
                            btn_login.setEnabled(true);
                            btn_login.setText("Loading..");

                        }
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Maaf Username Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                        btn_login.setEnabled(true);
                        btn_login.setText("login");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(Login.this, "Database Eror", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this, GetStarted.class));
        finish();
    }
}