package com.example.myshop.Model;

import com.example.myshop.Interface.FirebaseCallback;
import com.example.myshop.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseHelper {
    private DatabaseReference productsRef;

    public FirebaseDatabaseHelper(FirebaseDatabase instance) {
        // Получаем ссылку на "products" в базе данных Firebase
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
    }

    public void getProductById(String productId, final FirebaseCallback callback) {
        // Получаем ссылку на продукт по его id
        DatabaseReference productRef = productsRef.child(productId);

        // Слушаем изменения данных продукта
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Преобразуем данные в объект Product
                    Products product = dataSnapshot.getValue(Products.class);
                    callback.onCallback(product);
                } else {
                    // Продукт не найден
                    callback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок при получении данных
                callback.onCallback(null);
            }
        });
    }
}
