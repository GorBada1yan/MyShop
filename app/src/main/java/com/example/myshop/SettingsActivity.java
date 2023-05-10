package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;




import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private TextView add_settings;
    private CircleImageView account_image;
    private TextView fullname, close, semail, sphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        add_settings=findViewById(R.id.add_setting);
        fullname=findViewById(R.id.fullname_settings);
        close=findViewById(R.id.close_setting);
        semail = findViewById(R.id.email_settings);
        sphone=findViewById(R.id.phone_settings);
        account_image=findViewById(R.id.account_image_settings);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());


        userRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);

                // Устанавливаем имя пользователя в TextView
                fullname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });

// Получаем номер телефона пользователя из базы данных
        userRef.child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.getValue(String.class);

                // Устанавливаем номер телефона пользователя в TextView
                sphone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });

// Получаем email пользователя из базы данных
        userRef.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);

                // Устанавливаем email пользователя в TextView
                semail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });

// Получаем ссылку на фотографию пользователя из базы данных
        userRef.child("photoUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String photoUrl = dataSnapshot.getValue(String.class);

                // Загружаем фотографию пользователя с помощью Glide
                Glide.with(SettingsActivity.this)
                        .load(photoUrl)
                        .into(account_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent closeIntent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(closeIntent);
            }
        });

        add_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addSettingsIntent = new Intent(SettingsActivity.this, AddCategoryActivity.class);
                startActivity(addSettingsIntent);
            }
        });

        account_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            account_image.setImageURI(selectedImage);

            // Get the current user from the database
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

            // Save the image URI in the database
            userRef.child("imageUri").setValue(selectedImage.toString());

            // Save the image in Firebase Storage and update the download URL in the database
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("users").child(currentUser.getUid()).child("profile.jpg");
            storageRef.putFile(selectedImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL and update it in the database
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            userRef.child("photoUrl").setValue(uri.toString());

                            // Update the profile image in the app with the downloaded image
                            Glide.with(SettingsActivity.this)
                                    .load(uri.toString())
                                    .into(account_image);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                    });
        }
    }

}

