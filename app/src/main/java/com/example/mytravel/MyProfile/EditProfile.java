package com.example.mytravel.MyProfile;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditProfile extends AppCompatActivity
{
    ImageView photo_edit_profile;
    EditText nama_lengkap_edit, no_telp_edit, domisili_pengguna_edit, status_edit;
    Button button_simpan, pilih_gambar_edit, btn_back;
    Uri photo_location;
    Integer photo_max=1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.edit_profile);

        getUsernameLocal();

        photo_edit_profile      = findViewById(R.id.photo_edit_profile);
        nama_lengkap_edit       = findViewById(R.id.nama_lengkap_edit);
        no_telp_edit            = findViewById(R.id.no_telp_edit);
        domisili_pengguna_edit  = findViewById(R.id.domisili_pengguna_edit);
        status_edit             = findViewById(R.id.status_edit);
        button_simpan           = findViewById(R.id.button_simpan);
        pilih_gambar_edit       = findViewById(R.id.pilih_gambar_edit);
        btn_back                = findViewById(R.id.btn_back);


        reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
        storage   = FirebaseStorage.getInstance().getReference().child("PhotoUser").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                nama_lengkap_edit.setText(Objects.requireNonNull(snapshot.child("nama_lengkap_pengguna").getValue()).toString());
                no_telp_edit.setText(Objects.requireNonNull(snapshot.child("no_telp_pengguna").getValue()).toString());
                domisili_pengguna_edit.setText(Objects.requireNonNull(snapshot.child("domisili_pengguna").getValue()).toString());
                status_edit.setText(Objects.requireNonNull(snapshot.child("status_pengguna").getValue()).toString());
                Picasso.with(EditProfile.this).load(Objects.requireNonNull(snapshot.child("url_photo_profile").getValue()).toString()).centerCrop().fit().into(photo_edit_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        button_simpan.setOnClickListener(v -> {
            reference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    snapshot.getRef().child("nama_lengkap_pengguna").setValue(nama_lengkap_edit.getText().toString());
                    snapshot.getRef().child("no_telp_pengguna").setValue(no_telp_edit.getText().toString());
                    snapshot.getRef().child("domisili_pengguna").setValue(domisili_pengguna_edit.getText().toString());
                    snapshot.getRef().child("status_pengguna").setValue(status_edit.getText().toString());
                    Toast.makeText(EditProfile.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });

            if (photo_location != null)
            {
                StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));

                storageReference1.putFile(photo_location).
                        addOnSuccessListener(taskSnapshot -> storageReference1.getDownloadUrl().addOnSuccessListener(uri -> {
                            String uri_photo = uri.toString();
                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                        }).addOnCompleteListener(task -> {

                        })).addOnCompleteListener(task -> {

                });
            }
        });

        pilih_gambar_edit.setOnClickListener(v -> find_photo());

        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(EditProfile.this, MyProfile.class));
            finish();
        });

    }

    String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void find_photo()
    {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_edit_profile);
        }
    }


    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfile.this, MyProfile.class));
        finish();
    }
}