package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myshop.Adapters.PhotoAdapter;
import com.example.myshop.Interface.FirebaseCallback;
import com.example.myshop.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private TextView info_back, info_edit, info_description, info_mark, info_price, info_contacts, info_model;

    private FirebaseDatabaseHelper firebaseDatabaseHelper;
    private RecyclerView photoRecyclerView;
    private PhotoAdapter photoAdapter;
    private DatabaseReference productReference;
    private String userContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        info_back = findViewById(R.id.info_back);
        info_edit = findViewById(R.id.info_edit);
        info_description = findViewById(R.id.info_description);
        info_mark = findViewById(R.id.info_name);
        info_price = findViewById(R.id.info_price);
        info_contacts = findViewById(R.id.info_contacts);
        info_model = findViewById(R.id.info_model);

        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(FirebaseDatabase.getInstance());
        photoRecyclerView = findViewById(R.id.info_photo_rec_view);
        photoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        productReference = FirebaseDatabase.getInstance().getReference().child("Products");
        if (productId != null) {
            productReference.child(productId).child("image*").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> photoUrls = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String photoUrl = childSnapshot.getValue(String.class);
                        if (photoUrl != null) {
                            photoUrls.add(photoUrl);
                        }
                    }
                    photoAdapter = new PhotoAdapter(photoUrls);
                    photoRecyclerView.setAdapter(photoAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        if (productId != null && firebaseDatabaseHelper != null) {
            firebaseDatabaseHelper.getProductById(productId, new FirebaseCallback() {
                @Override
                public void onCallback(Products product) {
                    if (product != null) {
                        info_mark.setText(product.getCar_mark());
                        info_model.setText(product.getModel());
                        info_price.setText("$" + product.getPrice());
                        info_description.setText("Информация о машине" + "\n" + "\n" +
                                "Марка:" + product.getCar_mark() + "\n" +
                                "Модель: " + product.getModel() + "\n" +
                                "Кузов: " + product.getCar_kuzov() + "\n" +
                                "Год выпуска: " + product.getCar_year() + "\n" +
                                "Руль: " + product.getCar_bublik() + "\n" +
                                "Мотор: " + product.getCar_motor() + "\n" +
                                "Дополнительная информация: " + "\n" + "\n" +
                                product.getDescription()
                        );
                        info_contacts.setText(
                                "Информация для связи" + "\n" + "\n" +
                                        product.getContacts() + "\n" +
                                        product.getDop_contacts());
                    }
                }
            });
        }
        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoback = new Intent(EditActivity.this, SettingsActivity.class);
                startActivity(infoback);
            }
        });
        info_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoedit = new Intent(EditActivity.this, EditActivity.class);
                startActivity(infoedit);
            }
        });

    }
}
