package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.Interface.FirebaseCallback;
import com.example.myshop.Model.Products;
import com.google.firebase.database.FirebaseDatabase;

public class InfoProdActivity extends AppCompatActivity {

    private TextView info_back, info_add_favorite, info_description, info_name ,info_price,info_contacts;
    private ImageView info_photo;
    private FirebaseDatabaseHelper firebaseDatabaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_prod);
        info_back = findViewById(R.id.info_back);
        info_add_favorite = findViewById(R.id.info_add_favorite);
        info_description = findViewById(R.id.info_description);
        info_name = findViewById(R.id.info_name);
        info_price = findViewById(R.id.info_price);
        info_contacts = findViewById(R.id.info_contacts);
        info_photo = findViewById(R.id.info_photo)  ;

        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        FirebaseDatabaseHelper firebaseDatabaseHelper = (FirebaseDatabaseHelper) intent.getSerializableExtra("firebaseDatabaseHelper"); // Получите FirebaseDatabaseHelper из интента

        // Получение информации о продукте из Firebase по id
        firebaseDatabaseHelper.getProductById(productId, new FirebaseCallback() {
            @Override
            public void onCallback(Products product) {
                if (product != null) {
                    // Установка полученных значений в элементы интерфейса
                    info_name.setText(product.getPname());
                    info_price.setText(product.getPrice());
                    info_description.setText(product.getDescription());

                    // Загрузка изображения по URI
                    String imageUri = product.getImage();
                    if (imageUri != null) {
                        Uri uri = Uri.parse(imageUri);
                        info_photo.setImageURI(uri);
                    }
                }
            }
        });

        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoback = new Intent(InfoProdActivity.this, HomeActivity.class);
                startActivity(infoback);
            }
        });

        // TODO info_add_favorite button functional
    }

}
