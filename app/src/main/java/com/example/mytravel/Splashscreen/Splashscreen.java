package com.example.mytravel.Splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.mytravel.Dashboard.Dashboard;
import com.example.mytravel.GetStarted.GetStarted;
import com.example.mytravel.R;

public class Splashscreen extends AppCompatActivity
{

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        getUsernameLocal();

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

        if (username_key_new.isEmpty())
        {
            new Handler().postDelayed(() -> {
                Intent next = new Intent(Splashscreen.this, GetStarted.class);
                startActivity(next);
                finish();
            }, 1000);
        }

        else
        {
            new Handler().postDelayed(() -> {
                Intent next = new Intent(Splashscreen.this, Dashboard.class);
                startActivity(next);
                finish();
            }, 1300);
        }
    }
}