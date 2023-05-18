package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {
    private EditText fullname_edit, phone_edit;
    private TextView close_edit, save_edit;
    private CircleImageView account_image_edit;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        init();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        userRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);

                // Устанавливаем имя пользователя в TextView
                fullname_edit.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });

        userRef.child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.getValue(String.class);

                // Устанавливаем номер телефона пользователя в TextView
                phone_edit.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });

        userRef.child("photoUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String photoUrl = dataSnapshot.getValue(String.class);

                // Загружаем фотографию пользователя с помощью Glide
                Glide.with(ProfileEditActivity.this)
                        .load(photoUrl)
                        .into(account_image_edit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обрабатываем ошибку
            }
        });
        account_image_edit.setOnClickListener(new View.OnClickListener() {
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
            account_image_edit.setImageURI(selectedImage);

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
                            Glide.with(ProfileEditActivity.this)
                                    .load(uri.toString())
                                    .into(account_image_edit);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                    });


        }

        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = fullname_edit.getText().toString();
                String newPhone = phone_edit.getText().toString();

                // Получаем ссылку на узел текущего пользователя
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());

                // Обновляем имя и номер телефона пользователя в Firebase Database
                userRef.child("name").setValue(newName);
                userRef.child("phone").setValue(newPhone);

                // Оповещаем пользователя об успешном обновлении данных
                Toast.makeText(ProfileEditActivity.this, "Профиль обнавлен", Toast.LENGTH_SHORT).show();
                Intent saveEditIntent = new Intent(ProfileEditActivity.this, SettingsActivity.class);
                startActivity(saveEditIntent);
            }
        });
        close_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ProfileEditActivity.this, SettingsActivity.class);
                startActivity(backIntent);
            }
        });




    }
    private void init(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        mStorage = FirebaseStorage.getInstance().getReference().child("photoUrl").child(mAuth.getCurrentUser().getUid() + ".jpg");

        fullname_edit = findViewById(R.id.fullname_edit);
        phone_edit = findViewById(R.id.phone_edit);
        close_edit = findViewById(R.id.close_edit);
        save_edit = findViewById(R.id.save_edit);
        account_image_edit = findViewById(R.id.account_image_edit);
    }

}