package com.example.mytravel.Checkout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.Dashboard.Dashboard;
import com.example.mytravel.R;

public class SuccsesTiketCheckout extends AppCompatActivity
{
    Button Dashboard_btn;
    Animation sukses;
    ImageView logotransaksisukses;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.succses_tiket_checkout);

        logotransaksisukses = findViewById(R.id.logotransaksisukses);
        textView            = findViewById(R.id.textView);

        sukses = AnimationUtils.loadAnimation(this, R.anim.sukses);

        logotransaksisukses.setAnimation(sukses);
        textView.setAnimation(sukses);

        Dashboard_btn = findViewById(R.id.Dashboard_btn);
        Dashboard_btn.setOnClickListener(v -> {
            startActivity(new Intent(SuccsesTiketCheckout.this, Dashboard.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SuccsesTiketCheckout.this, Dashboard.class));
        finish();
    }
}