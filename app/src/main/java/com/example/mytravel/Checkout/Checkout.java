package com.example.mytravel.Checkout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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
import java.util.Random;

public class Checkout extends AppCompatActivity
{
    Button btn_kembali, btn_bayar, btnplus, btnminus;
    TextView nama_tiket, lokasi, totaltiket, total_harga, saldo_pengguna;
    Integer valuejumlahtiket = 1;
    Integer mybalance        = 0;
    Integer valuetotalharga  = 0;
    Integer valuehargatiket  = 0;
    ImageView notice_uang;
    Integer sisa_balance= 0;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";

    String jam_buka = "";
    String deskripsi = "";
    String festival = "";
    String photo_spot = "";

    Integer nomor_transaksi = new Random().nextInt();

    DatabaseReference reference, reference2, reference3, reference4;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_kembali    = findViewById(R.id.btn_kembali);
        btnplus        = findViewById(R.id.btnplus);
        btnminus       = findViewById(R.id.btnminus);
        btn_bayar      = findViewById(R.id.btn_bayar);

        nama_tiket     = findViewById(R.id.nama_tiket);
        lokasi         = findViewById(R.id.lokasi);

        totaltiket     = findViewById(R.id.totaltiket);
        total_harga    = findViewById(R.id.total_harga);
        saldo_pengguna = findViewById(R.id.saldo_pengguna);
        notice_uang    = findViewById(R.id.notice_uang);

        totaltiket.setText(valuejumlahtiket.toString());

        btnminus.animate().alpha(0).setDuration(300).start();
        btnminus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        reference2 = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mybalance = Integer.valueOf(Objects.requireNonNull(snapshot.child("saldo_pengguna").getValue()).toString());
                saldo_pengguna.setText(""+mybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("TUJUAN WISATA").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                nama_tiket.setText(Objects.requireNonNull(snapshot.child("nama_wisata").getValue()).toString());
                lokasi.setText(Objects.requireNonNull(snapshot.child("lokasi").getValue()).toString());
                jam_buka    = Objects.requireNonNull(snapshot.child("jam_buka").getValue()).toString();
                deskripsi   = Objects.requireNonNull(snapshot.child("deskripsi").getValue()).toString();
                festival    = Objects.requireNonNull(snapshot.child("festival").getValue()).toString();
                photo_spot  = Objects.requireNonNull(snapshot.child("photo_spot").getValue()).toString();

                valuehargatiket = Integer.valueOf(Objects.requireNonNull(snapshot.child("harga_tiket").getValue()).toString());

                valuetotalharga = valuehargatiket * valuejumlahtiket;
                total_harga.setText(""+valuetotalharga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnplus.setOnClickListener(v ->
        {
            valuejumlahtiket+=1;
            totaltiket.setText(valuejumlahtiket.toString());
            if (valuejumlahtiket > 1)
            {
                btnminus.animate().alpha(1).setDuration(300).start();
                btnminus.setEnabled(true);
            }

            valuetotalharga = valuehargatiket * valuejumlahtiket;
            total_harga.setText(""+valuetotalharga+"");

            if (valuetotalharga > mybalance)
            {
                btn_bayar.animate().translationY(250).alpha(0).setDuration(350).start();
                btn_bayar.setEnabled(false);
                saldo_pengguna.setTextColor(Color.parseColor("#D1206b"));
                notice_uang.setVisibility(View.VISIBLE);
            }
        });

        btnminus.setOnClickListener(v ->
        {
            valuejumlahtiket-=1;
            totaltiket.setText(valuejumlahtiket.toString());
            if (valuejumlahtiket < 2)
            {
                btnminus.animate().alpha(0).setDuration(300).start();
                btnminus.setEnabled(false);

            }

            valuetotalharga = valuehargatiket * valuejumlahtiket;
            total_harga.setText(""+valuetotalharga+"");

            if (valuetotalharga < mybalance)
            {
                btn_bayar.animate().translationY(0).alpha(1).setDuration(350).start();
                btn_bayar.setEnabled(true);
                saldo_pengguna.setTextColor(Color.parseColor("#203dd1"));
                notice_uang.setVisibility(View.GONE);
            }
        });


        btn_kembali.setOnClickListener(v ->
        {
            startActivity(new Intent(Checkout.this, Dashboard.class));
            finish();
        });

        btn_bayar.setOnClickListener(v ->
        {

            reference3 = FirebaseDatabase.getInstance().getReference().child("MyTikets").child(username_key_new).child(nama_tiket.getText().toString() + nomor_transaksi);
            reference3.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    reference3.getRef().child("nama_wisata").setValue(nama_tiket.getText().toString());
                    reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                    reference3.getRef().child("jumlah_tiket").setValue(valuejumlahtiket.toString());
                    reference3.getRef().child("jam_buka").setValue(jam_buka);
                    reference3.getRef().child("deskripsi").setValue(deskripsi);
                    reference3.getRef().child("festival").setValue(festival);
                    reference3.getRef().child("photo_spot").setValue(photo_spot);

                    new Handler().postDelayed(() ->
                    {
                        startActivity(new Intent(Checkout.this, SuccsesTiketCheckout.class));
                        finish();
                    }, 1200);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference4 = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
            reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    sisa_balance = mybalance - valuetotalharga;
                    reference4.getRef().child("saldo_pengguna").setValue(sisa_balance);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Checkout.this, Dashboard.class));
        finish();
    }
}