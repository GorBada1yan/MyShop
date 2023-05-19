package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoProdActivity extends AppCompatActivity {

    private TextView info_back, info_add_favorite, info_description, info_name ,info_price,info_contacts;
    private ImageView info_photo;

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
        String productName = intent.getStringExtra("productName");
        String productDescription = intent.getStringExtra("productDescription");
        String productPrice = intent.getStringExtra("productPrice");
        String productContacts = intent.getStringExtra("contacts");


        info_name.setText(productName);
        info_description.setText(productDescription);
        info_contacts.setText(productContacts);
        info_price.setText(productPrice);
        String imageUriString = intent.getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageUriString);
        info_photo.setImageURI(imageUri);

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