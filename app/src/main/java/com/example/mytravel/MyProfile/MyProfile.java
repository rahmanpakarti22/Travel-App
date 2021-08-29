package com.example.mytravel.MyProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.Dashboard.Dashboard;
import com.example.mytravel.GetStarted.GetStarted;
import com.example.mytravel.R;
import com.example.mytravel.ShowData.MyTicket;
import com.example.mytravel.ShowData.TicketAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class MyProfile extends AppCompatActivity
{

    ImageView photo_profile;
    TextView nama_lengkap, status, saldo_user;
    Button btn_back, Logout, edit_profile;

    DatabaseReference reference, reference2;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";

    RecyclerView myticket_place;
    ArrayList<MyTicket> list;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        getUsernameLocal();

        photo_profile   = findViewById(R.id.photo_profile);
        nama_lengkap    = findViewById(R.id.nama_lengkap);
        status          = findViewById(R.id.status);
        btn_back        = findViewById(R.id.btn_back);
        Logout          = findViewById(R.id.Logout);
        edit_profile    = findViewById(R.id.edit_profile);
        saldo_user      = findViewById(R.id.saldo_user);

        myticket_place  = findViewById(R.id.myticket_place);
        myticket_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                nama_lengkap.setText(Objects.requireNonNull(snapshot.child("nama_lengkap_pengguna").getValue()).toString());
                status.setText(Objects.requireNonNull(snapshot.child("status_pengguna").getValue()).toString());
                saldo_user.setText(Objects.requireNonNull(snapshot.child("saldo_pengguna").getValue()).toString());
                Picasso.with(MyProfile.this).load(Objects.requireNonNull(snapshot.child("url_photo_profile").getValue()).toString()).centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTikets").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    MyTicket p = snapshot1.getValue(MyTicket.class);
                    list.add(p);
                }
                ticketAdapter = new TicketAdapter(MyProfile.this, list);
                myticket_place.setAdapter(ticketAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(MyProfile.this, Dashboard.class));
            finish();
        });

        Logout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username_key, null);
            editor.apply();

            Toast.makeText(this, "Anda Berhasil Keluar", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MyProfile.this, GetStarted.class));
            finish();
        });

        edit_profile.setOnClickListener(v -> {
            startActivity(new Intent(MyProfile.this, EditProfile.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MyProfile.this, Dashboard.class));
        finish();
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}