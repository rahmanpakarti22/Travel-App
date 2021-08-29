package com.example.mytravel.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.Dashboard.Dashboard;
import com.example.mytravel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SuksesLogAct extends AppCompatActivity
{
    TextView selamat_datang, textlogo;
    ImageView logotransaksisukses;
    Animation sukses;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.sukses_log);

        getUsernameLocal();

        selamat_datang = findViewById(R.id.selamat_datang);
        textlogo       = findViewById(R.id.textlogo);
        logotransaksisukses = findViewById(R.id.logotransaksisukses);
        sukses = AnimationUtils.loadAnimation(this, R.anim.sukses);

        selamat_datang.setAnimation(sukses);
        textlogo.setAnimation(sukses);
        logotransaksisukses.setAnimation(sukses);

        reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                selamat_datang.setText(Objects.requireNonNull(snapshot.child("nama_lengkap_pengguna").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new Handler().postDelayed(() -> {
            Intent next = new Intent(SuksesLogAct.this, Dashboard.class);
            startActivity(next);
            finish();
        },1500);
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}