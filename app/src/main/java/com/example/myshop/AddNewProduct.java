package com.example.myshop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewProduct extends AppCompatActivity {

    private ImageView productImage;
    private EditText productName, productDescription, productPrice;
    private Button addNewProductButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_product);


        init();
    }
        private void init(){
        productImage = findViewById(R.id.select_product_image);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        addNewProductButton = findViewById(R.id.btn_add_new_product);
        }
}
