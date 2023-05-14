package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Spinner;

public class ProductAddActivity extends AppCompatActivity {
    private TextView back_add, add_add;
    private ImageView product_image_add;
    private EditText product_name_add, product_description_add, product_price_add, product_contact_add, product_location_add;
    private Spinner category_spinner1, category_spinner2,  category_spinner_comerch , category_spinner_lich;
    private Spinner category_spinner_bus, category_spinner_gruz, category_spinner_selxoz, category_spinner_stroi;
    private Spinner category_spinner_auto, category_spinner_moped, category_spinner_velosiped, category_spinner_moto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        back_add = findViewById(R.id.back_add);
        add_add = findViewById(R.id.add_add);
        product_name_add = findViewById(R.id.product_name_add);
        product_description_add = findViewById(R.id.product_description_add);
        product_price_add = findViewById(R.id.product_price_add);
        product_contact_add = findViewById(R.id.product_contact_add);
        product_location_add = findViewById(R.id.product_location_add);
        category_spinner1 = findViewById(R.id.category_spinner1);
        category_spinner2 = findViewById(R.id.category_spinner2);
        category_spinner_comerch = findViewById(R.id.category_spinner_comerch);
        category_spinner_lich = findViewById(R.id.category_spinner_lich);
        product_image_add = findViewById(R.id.product_image_add);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.category_spinner1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.category_spinner2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner2.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adaptercomerch = ArrayAdapter.createFromResource(this, R.array.category_spinner_comerch, android.R.layout.simple_spinner_item);
        adaptercomerch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_comerch.setAdapter(adaptercomerch);

        ArrayAdapter<CharSequence> adapterlich = ArrayAdapter.createFromResource(this, R.array.category_spinner_lich, android.R.layout.simple_spinner_item);
        adapterlich.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_lich.setAdapter(adapterlich);

        category_spinner_bus = findViewById(R.id.category_spinner_bus);
        ArrayAdapter<CharSequence> adapterbus = ArrayAdapter.createFromResource(this, R.array.category_spinner_bus, android.R.layout.simple_spinner_item);
        adapterbus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_bus.setAdapter(adapterbus);

        category_spinner_gruz = findViewById(R.id.category_spinner_gruz);
        ArrayAdapter<CharSequence> adaptergruz = ArrayAdapter.createFromResource(this, R.array.category_spinner_gruz, android.R.layout.simple_spinner_item);
        adaptergruz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_gruz.setAdapter(adaptergruz);

        category_spinner_selxoz = findViewById(R.id.category_spinner_selxoz);
        ArrayAdapter<CharSequence> adapterselxoz = ArrayAdapter.createFromResource(this, R.array.category_spinner_selxoz, android.R.layout.simple_spinner_item);
        adapterselxoz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_selxoz.setAdapter(adapterselxoz);

        category_spinner_stroi = findViewById(R.id.category_spinner_stroi);
        ArrayAdapter<CharSequence> adapterstroi = ArrayAdapter.createFromResource(this, R.array.category_spinner_stroi, android.R.layout.simple_spinner_item);
        adapterstroi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_stroi.setAdapter(adapterstroi);



        back_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backfromadd = new Intent(ProductAddActivity.this , HomeActivity.class);
                startActivity(backfromadd);
            }
        });

        category_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Камерческий транспорт")){
                    category_spinner_comerch.setVisibility(View.VISIBLE);
                }else  category_spinner_comerch.setVisibility(View.INVISIBLE);
                    if (selectedItem.equals( "Личный транспорт")){
                    category_spinner_lich.setVisibility(View.VISIBLE);
                }else category_spinner_lich.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        category_spinner_comerch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Автобус")){
                    category_spinner_bus.setVisibility(View.VISIBLE);
                }else category_spinner_bus.setVisibility(View.INVISIBLE);
                if (selectedItem.equals("Грузовик")){
                    category_spinner_gruz.setVisibility(View.VISIBLE);
                }else category_spinner_gruz.setVisibility(View.INVISIBLE);
                if (selectedItem.equals("Сельскохозяйственный транспорт")){
                    category_spinner_selxoz.setVisibility(View.VISIBLE);
                }else category_spinner_selxoz.setVisibility(View.INVISIBLE);
                if (selectedItem.equals("Строительская и тяжолая техника")){
                    category_spinner_stroi.setVisibility(View.VISIBLE);
                }else category_spinner_stroi.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });




    }
}