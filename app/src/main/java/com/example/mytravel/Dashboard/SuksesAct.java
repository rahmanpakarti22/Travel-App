package com.example.mytravel.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.R;

public class SuksesAct extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.sukses_act);

        new Handler().postDelayed(() -> {
            Intent next = new Intent(SuksesAct.this, Dashboard.class);
            startActivity(next);
            finish();
        },1400);
    }
}