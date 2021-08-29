package com.example.mytravel.MenuWisata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.Dashboard.Dashboard;
import com.example.mytravel.R;
import com.example.mytravel.TiketDetail.TiketDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class JatimMenu extends AppCompatActivity
{
    ImageView jatim_image, Pulau_Gili_Iyang, Air_terjun_Madakaripura, Pantai_Banyu_Tibo;
    TextView judul_jatim, desc_jatim, nama_wisata, nama_wissata_kedua, nama_wissata_ketiga;
    Button kembali;

    DatabaseReference reference;

    LinearLayout menu_4, menu_5, menu_6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jatim_menu);


        menu_4              = findViewById(R.id.menu_4);
        menu_5              = findViewById(R.id.menu_5);
        menu_6              = findViewById(R.id.menu_6);

        kembali             = findViewById(R.id.kembali);

        jatim_image             = findViewById(R.id.jatim_image);
        Pulau_Gili_Iyang        = findViewById(R.id.Pulau_Gili_Iyang);
        Air_terjun_Madakaripura = findViewById(R.id.Air_terjun_Madakaripura);
        Pantai_Banyu_Tibo       = findViewById(R.id.Pantai_Banyu_Tibo);
        judul_jatim             = findViewById(R.id.judul_jatim);
        jatim_image             = findViewById(R.id.jatim_image);
        desc_jatim              = findViewById(R.id.desc_jatim);
        nama_wisata             = findViewById(R.id.nama_wisata);
        nama_wissata_kedua      = findViewById(R.id.nama_wissata_kedua);
        nama_wissata_ketiga     = findViewById(R.id.nama_wissata_ketiga);

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Thumbnail_Jatim");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                judul_jatim.setText(Objects.requireNonNull(snapshot.child("Julukan").getValue()).toString());
                desc_jatim.setText(Objects.requireNonNull(snapshot.child("Moto").getValue()).toString());
                Picasso.with(JatimMenu.this).load(Objects.requireNonNull(snapshot.child("uri_photo_jatim2").getValue()).toString()).centerCrop().fit().into(jatim_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Pulau Gili Iyang");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.with(JatimMenu.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(Pulau_Gili_Iyang);
                nama_wisata.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Air terjun Madakaripura");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Picasso.with(JatimMenu.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(Air_terjun_Madakaripura);
                nama_wissata_kedua.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Pantai Banyu Tibo");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Picasso.with(JatimMenu.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(Pantai_Banyu_Tibo);
                nama_wissata_ketiga.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        menu_4.setOnClickListener(v ->
        {
            Intent gotoPulau_Gili_Iyang = new Intent(JatimMenu.this, TiketDetail.class);
            gotoPulau_Gili_Iyang.putExtra("jenis_tiket", "Pulau Gili Iyang");
            startActivity(gotoPulau_Gili_Iyang);
        });

        menu_5.setOnClickListener(v ->
        {
            Intent gotoAir_terjun_Madakaripura = new Intent(JatimMenu.this, TiketDetail.class);
            gotoAir_terjun_Madakaripura.putExtra("jenis_tiket", "Air terjun Madakaripura");
            startActivity(gotoAir_terjun_Madakaripura);
        });

        menu_6.setOnClickListener(v ->
        {
            Intent gotoPantai_Banyu_Tibo = new Intent(JatimMenu.this, TiketDetail.class);
            gotoPantai_Banyu_Tibo.putExtra("jenis_tiket", "Pantai Banyu Tibo");
            startActivity(gotoPantai_Banyu_Tibo);
        });

        kembali.setOnClickListener(v ->
        {
            startActivity(new Intent(JatimMenu.this, Dashboard.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(JatimMenu.this, Dashboard.class));
        finish();
    }
}