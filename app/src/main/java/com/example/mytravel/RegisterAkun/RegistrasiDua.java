package com.example.mytravel.RegisterAkun;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytravel.Dashboard.SuksesAct;
import com.example.mytravel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RegistrasiDua extends AppCompatActivity
{
    Button button, pilih_gambar;
    EditText nama_lengkap, no_telp, domisili_pengguna, status;
    ImageView photo_profile;
    Uri photo_location;
    Integer photo_max=1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi_dua);

        getUsernameLocal();

        button            = findViewById(R.id.button);
        nama_lengkap      = findViewById(R.id.nama_lengkap);
        no_telp           = findViewById(R.id.no_telp);
        domisili_pengguna = findViewById(R.id.domisili_pengguna);
        status            = findViewById(R.id.status);
        photo_profile     = findViewById(R.id.photo_profile);
        pilih_gambar      = findViewById(R.id.pilih_gambar);

        button.setOnClickListener(v -> {
            button.setEnabled(false);
            button.setText("Loading..");
            reference = FirebaseDatabase.getInstance().getReference().child("PENGGUNA").child(username_key_new);
            storage = FirebaseStorage.getInstance().getReference().child("PhotoUser").child(username_key_new);

            if (photo_location != null)
            {
                StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));

                storageReference1.putFile(photo_location).
                        addOnSuccessListener(taskSnapshot -> storageReference1.getDownloadUrl().addOnSuccessListener(uri -> {
                            String uri_photo = uri.toString();
                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                            reference.getRef().child("nama_lengkap_pengguna").setValue(nama_lengkap.getText().toString());
                            reference.getRef().child("no_telp_pengguna").setValue(no_telp.getText().toString());
                            reference.getRef().child("domisili_pengguna").setValue(domisili_pengguna.getText().toString());
                            reference.getRef().child("status_pengguna").setValue(status.getText().toString());
                            Toast.makeText(this, "Data Disimpan", Toast.LENGTH_SHORT).show();
                        }).addOnCompleteListener(task -> {
                            startActivity(new Intent(RegistrasiDua.this, SuksesAct.class));
                            finish();
                        })).addOnCompleteListener(task -> {

                });
            }
        });

        pilih_gambar.setOnClickListener(v -> find_photo());
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_profile);
        }
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}