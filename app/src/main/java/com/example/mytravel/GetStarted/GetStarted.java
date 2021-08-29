package com.example.mytravel.GetStarted;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.Login.Login;
import com.example.mytravel.R;
import com.example.mytravel.RegisterAkun.RegistrasiSatu;

public class GetStarted extends AppCompatActivity
{

    Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started);

        btn_login    = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (null != networkInfo)
        {
            if (networkInfo.getType()== ConnectivityManager.TYPE_WIFI)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GetStarted.this);
                builder.setIcon(R.drawable.warning)
                        .setMessage("TIDAK ADA JARINGAN")
                        .setPositiveButton("Connect", (dialog, which) -> startActivity(new Intent(Settings.ACTION_SETTINGS)))
                        .setNegativeButton("Batal", (dialog, which) -> onBackPressed());

            }

            else if (networkInfo.getType()== ConnectivityManager.TYPE_MOBILE)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GetStarted.this);
                builder.setIcon(R.drawable.warning)
                        .setMessage("TIDAK ADA JARINGAN")
                        .setPositiveButton("Connect", (dialog, which) -> startActivity(new Intent(Settings.ACTION_SETTINGS)))
                        .setNegativeButton("Batal", (dialog, which) -> onBackPressed());
            }

            else
            {
                Toast.makeText(this, "TIDAK KONEKSI", Toast.LENGTH_SHORT).show();
            }
        }

        btn_login.setOnClickListener(v -> {
            startActivity(new Intent(GetStarted.this, Login.class));
            finish();
        });

        btn_register.setOnClickListener(v -> {
            startActivity(new Intent(GetStarted.this, RegistrasiSatu.class));
            finish();
        });

    }
}