package com.example.mytravel.TiketDetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.Checkout.Checkout;
import com.example.mytravel.Dashboard.Dashboard;
import com.example.mytravel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TiketDetail extends AppCompatActivity
{
    ImageView gambar_thumbnail;
    TextView nama_wisata, lokasi, deskripsi, jam_buka, festival, wifi, harga_tiket, photo_spot;
    Button beli_tiket, kembali;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiket_detail);

        gambar_thumbnail = findViewById(R.id.gambar_thumbnail);
        nama_wisata      = findViewById(R.id.nama_wisata);
        lokasi           = findViewById(R.id.lokasi);
        deskripsi        = findViewById(R.id.deskripsi);
        jam_buka         = findViewById(R.id.jam_buka);
        festival         = findViewById(R.id.festival);
        wifi             = findViewById(R.id.wifi);
        photo_spot       = findViewById(R.id.photo_spot);
        harga_tiket      = findViewById(R.id.harga_tiket);
        beli_tiket       = findViewById(R.id.beli_tiket);
        kembali          = findViewById(R.id.kembali);

        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                nama_wisata.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
                lokasi.setText(Objects.requireNonNull(snapshot.child("lokasi").getValue()).toString());
                deskripsi.setText(Objects.requireNonNull(snapshot.child("deskripsi").getValue()).toString());
                jam_buka.setText(Objects.requireNonNull(snapshot.child("jam_buka").getValue()).toString());
                festival.setText(Objects.requireNonNull(snapshot.child("festival").getValue()).toString());
                wifi.setText(Objects.requireNonNull(snapshot.child("wifi").getValue()).toString());
                photo_spot.setText(Objects.requireNonNull(snapshot.child("photo_spot").getValue()).toString());
                harga_tiket.setText(Objects.requireNonNull(snapshot.child("harga_tiket").getValue()).toString());
                Picasso.with(TiketDetail.this).load(Objects.requireNonNull(snapshot.child("uri_thumbnail_wisata").getValue()).toString()).centerCrop().fit().into(gambar_thumbnail);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        beli_tiket.setOnClickListener(v ->
        {
            Intent gotoChechOut = new Intent(TiketDetail.this, Checkout.class);
            gotoChechOut.putExtra("jenis_tiket", jenis_tiket_baru);
            startActivity(gotoChechOut);
        });

        kembali.setOnClickListener(v ->
        {
            startActivity(new Intent(TiketDetail.this, Dashboard.class));
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TiketDetail.this, Dashboard.class));
        finish();
    }
}