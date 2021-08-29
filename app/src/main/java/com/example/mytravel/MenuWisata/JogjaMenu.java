package com.example.mytravel.MenuWisata;

import android.content.Intent;
import android.graphics.Color;
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

public class JogjaMenu extends AppCompatActivity
{

    ImageView jateng_image, Benteng_Vredeburg_Yogyakarta, Goa_jomblang, Pantai_Nglambor;
    TextView judul_jateng, desc_jateng, nama_wisata, nama_wissata_kedua, nama_wissata_ketiga;
    DatabaseReference reference;
    LinearLayout menu_1, menu_2, menu_3;

    Button kembali;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.jogja_menu);


        jateng_image          = findViewById(R.id.jateng_image);
        judul_jateng          = findViewById(R.id.judul_jateng);
        desc_jateng           = findViewById(R.id.desc_jateng);
        nama_wisata           = findViewById(R.id.nama_wisata);

        menu_1                = findViewById(R.id.menu_1);
        menu_2                = findViewById(R.id.menu_2);
        menu_3                = findViewById(R.id.menu_3);

        kembali               = findViewById(R.id.kembali);



        nama_wissata_kedua                     = findViewById(R.id.nama_wissata_kedua);
        nama_wissata_ketiga                    = findViewById(R.id.nama_wissata_ketiga);
        Benteng_Vredeburg_Yogyakarta           = findViewById(R.id.Benteng_Vredeburg_Yogyakarta);
        Goa_jomblang                           = findViewById(R.id.Goa_jomblang);
        Pantai_Nglambor                        = findViewById(R.id.Pantai_Nglambor);


        menu_1.setOnClickListener(v ->
        {
            Intent gotoBenteng_Vredeburg_Yogyakarta = new Intent(JogjaMenu.this, TiketDetail.class);
            gotoBenteng_Vredeburg_Yogyakarta.putExtra("jenis_tiket", "Benteng Vredeburg Yogyakarta");
            startActivity(gotoBenteng_Vredeburg_Yogyakarta);
        });

        menu_2.setOnClickListener(v ->
        {
            Intent gotoGoa_jomblang = new Intent(JogjaMenu.this, TiketDetail.class);
            gotoGoa_jomblang.putExtra("jenis_tiket", "Goa Jomblang");
            startActivity(gotoGoa_jomblang);
        });

        menu_3.setOnClickListener(v ->
        {
            Intent gotoPantai_Nglambor = new Intent(JogjaMenu.this, TiketDetail.class);
            gotoPantai_Nglambor.putExtra("jenis_tiket", "Pantai Nglambor");
            startActivity(gotoPantai_Nglambor);
        });


        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Thumbnail_Jateng");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                judul_jateng.setText(Objects.requireNonNull(snapshot.child("Julukan").getValue()).toString());
                desc_jateng.setText(Objects.requireNonNull(snapshot.child("Moto").getValue()).toString());
                Picasso.with(JogjaMenu.this).load(Objects.requireNonNull(snapshot.child("uri_photo_jateng2").getValue()).toString()).centerCrop().fit().into(jateng_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Benteng Vredeburg Yogyakarta");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Picasso.with(JogjaMenu.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(Benteng_Vredeburg_Yogyakarta);
                nama_wisata.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Goa Jomblang");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Picasso.with(JogjaMenu.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(Goa_jomblang);
                nama_wissata_kedua.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child("Pantai Nglambor");
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Picasso.with(JogjaMenu.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(Pantai_Nglambor);
                nama_wissata_ketiga.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        kembali.setOnClickListener(v -> {
            startActivity(new Intent(JogjaMenu.this, Dashboard.class));
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(JogjaMenu.this, Dashboard.class));
        finish();
    }
}