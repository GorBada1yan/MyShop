package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddCategoryActivity extends AppCompatActivity {

    private ImageView car, boat, bicycle, service;
    private ImageView dress, shoes, clothes, accessories;
    private ImageView phone, pc, headphones, fridge;
    private ImageView books, music, sport, guitar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        init();

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "car");
                startActivity(intent);
            }
        });

        bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "bicycle");
                startActivity(intent);
            }
        });

        boat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "boat");
                startActivity(intent);
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "service");
                startActivity(intent);
            }
        });

        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "dress");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "shoes");
                startActivity(intent);
            }
        });

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "clothes");
                startActivity(intent);
            }
        });

        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "accessories");
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "phone");
                startActivity(intent);
            }
        });

        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "pc");
                startActivity(intent);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "headphones");
                startActivity(intent);
            }
        });

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "fridge");
                startActivity(intent);
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "sport");
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "books");
                startActivity(intent);
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "music");
                startActivity(intent);
            }
        });

        guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, AddProductActivity.class);
                intent.putExtra("category", "music instruments");
                startActivity(intent);
            }
        });

    }

    private void init() {
        car = findViewById(R.id.car);
        bicycle = findViewById(R.id.bicycle);
        boat = findViewById(R.id.boat);
        service = findViewById(R.id.service);

        dress = findViewById(R.id.dress);
        shoes = findViewById(R.id.shoes);
        clothes = findViewById(R.id.clothes);
         accessories= findViewById(R.id.accessories);

        phone = findViewById(R.id.phone);
        pc = findViewById(R.id.pc);
        headphones = findViewById(R.id.headphones);
        fridge = findViewById(R.id.fridge);

        sport = findViewById(R.id.sport);
        books = findViewById(R.id.books);
        music = findViewById(R.id.music);
        guitar = findViewById(R.id.guitar);
    }
}