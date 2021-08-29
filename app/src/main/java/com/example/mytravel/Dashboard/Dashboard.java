package com.example.mytravel.Dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mytravel.MenuWisata.JatimMenu;
import com.example.mytravel.MenuWisata.JogjaMenu;
import com.example.mytravel.MyProfile.MyProfile;
import com.example.mytravel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Dashboard extends AppCompatActivity
{
    ImageView photo_profile, jateng_image, jatim_image;
    TextView nama_lengkap, status, saldo_user, judul_jateng, desc_jateng, judul_jatim, desc_jatim;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";

    LinearLayout linearLayout2;
    CardView jateng_btn, jatim_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        getUsernameLocal();

        photo_profile         = findViewById(R.id.photo_profile);
        nama_lengkap          = findViewById(R.id.nama_lengkap);
        status                = findViewById(R.id.status);
        saldo_user            = findViewById(R.id.saldo_user);
        jateng_image          = findViewById(R.id.jateng_image);
        judul_jateng          = findViewById(R.id.judul_jateng);
        desc_jateng           = findViewById(R.id.desc_jateng);
        linearLayout2         = findViewById(R.id.linearLayout2);

        jatim_image           = findViewById(R.id.jatim_image);
        judul_jatim           = findViewById(R.id.judul_jatim);
        desc_jatim            = findViewById(R.id.desc_jatim);

        jateng_btn            = findViewById(R.id.jateng_btn);
        jatim_btn             = findViewById(R.id.jatim_btn);

        jatim_btn.setOnClickListener(v ->
        {
            startActivity(new Intent(Dashboard.this, JatimMenu.class));
            finish();
        });
        jateng_btn.setOnClickListener(v ->
        {
            startActivity(new Intent(Dashboard.this, JogjaMenu.class));
            finish();
        });

        reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                nama_lengkap.setText(Objects.requireNonNull(snapshot.child("nama_lengkap_pengguna").getValue()).toString());
                status.setText(Objects.requireNonNull(snapshot.child("status_pengguna").getValue()).toString());
                saldo_user.setText(Objects.requireNonNull(snapshot.child("saldo_pengguna").getValue()).toString());
                Picasso.with(Dashboard.this).load(Objects.requireNonNull(snapshot.child("url_photo_profile").getValue()).toString()).centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Thumbnail_Jateng");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                judul_jateng.setText(Objects.requireNonNull(snapshot.child("Julukan").getValue()).toString());
                desc_jateng.setText(Objects.requireNonNull(snapshot.child("Moto").getValue()).toString());
                Picasso.with(Dashboard.this).load(Objects.requireNonNull(snapshot.child("uri_photo_jateng1").getValue()).toString()).centerCrop().fit().into(jateng_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Thumbnail_Jatim");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                judul_jatim.setText(Objects.requireNonNull(snapshot.child("Julukan").getValue()).toString());
                desc_jatim.setText(Objects.requireNonNull(snapshot.child("Moto").getValue()).toString());
                Picasso.with(Dashboard.this).load(Objects.requireNonNull(snapshot.child("uri_photo_jatim1").getValue()).toString()).centerCrop().fit().into(jatim_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        linearLayout2.setOnClickListener(v ->
        {
            startActivity(new Intent(Dashboard.this, MyProfile.class));
            finish();
        });


    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}