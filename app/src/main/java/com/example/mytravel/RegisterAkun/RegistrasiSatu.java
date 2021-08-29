package com.example.mytravel.RegisterAkun;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrasiSatu extends AppCompatActivity
{

    Button lanjut;
    EditText et_Username, et_Email, et_Password;

    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.registrasi_satu);

        lanjut      = findViewById(R.id.lanjut);
        et_Username = findViewById(R.id.et_Username);
        et_Email    = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);

        lanjut.setOnClickListener(v -> {
            lanjut.setEnabled(false);
            lanjut.setText("Loading..");

            reference_username = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(et_Username.getText().toString());
            reference_username.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if (snapshot.exists())
                    {
                        lanjut.setEnabled(true);
                        lanjut.setText("LANJUTKAN");

                        if (et_Username.getText().toString().isEmpty())
                        {
                            Toast.makeText(RegistrasiSatu.this, "Mohon Data Disi Secara Lengkap", Toast.LENGTH_SHORT).show();
                        }

                        else if (et_Email.getText().toString().isEmpty())
                        {
                            Toast.makeText(RegistrasiSatu.this, "Mohon Data Disi Secara Lengkap", Toast.LENGTH_SHORT).show();
                        }

                        else if (et_Password.getText().toString().isEmpty())
                        {
                            Toast.makeText(RegistrasiSatu.this, "Mohon Data Disi Secara Lengkap", Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                                Toast.makeText(RegistrasiSatu.this, "Maaf Username Sudah Ada!", Toast.LENGTH_SHORT).show();
                            }
                    }

                    else
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(username_key, et_Username.getText().toString());
                        editor.apply();

                        reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(et_Username.getText().toString());
                        reference.addListenerForSingleValueEvent(new ValueEventListener()

                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                if (et_Username.getText().toString().isEmpty())
                                {
                                    Toast.makeText(RegistrasiSatu.this, "Mohon Data Disi Secara Lengkap", Toast.LENGTH_SHORT).show();
                                }

                                else if (et_Email.getText().toString().isEmpty())
                                {
                                    Toast.makeText(RegistrasiSatu.this, "Mohon Data Disi Secara Lengkap", Toast.LENGTH_SHORT).show();
                                }

                                else if (et_Password.getText().toString().isEmpty())
                                {
                                    Toast.makeText(RegistrasiSatu.this, "Mohon Data Disi Secara Lengkap", Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    snapshot.getRef().child("username").setValue(et_Username.getText().toString());
                                    snapshot.getRef().child("email_pengguna").setValue(et_Email.getText().toString());
                                    snapshot.getRef().child("password_pengguna").setValue(et_Password.getText().toString());
                                    snapshot.getRef().child("saldo_pengguna").setValue(560000);
                                    startActivity(new Intent(RegistrasiSatu.this, RegistrasiDua.class));
                                    finish();
                                }

                                lanjut.setEnabled(true);
                                lanjut.setText("LANJUTKAN");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegistrasiSatu.this, GetStarted.class));
        finish();
    }
}