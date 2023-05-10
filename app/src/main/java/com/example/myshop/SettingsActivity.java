package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private TextView add_settings;
    private CircleImageView account_image;
    private TextView fullname,  email, phone;
    private TextView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        add_settings=findViewById(R.id.add_setting);
        fullname=findViewById(R.id.fullname_settings);
        close=findViewById(R.id.close_setting);
        email = findViewById(R.id.email_settings);
        phone=findViewById(R.id.phone_settings);
        account_image=findViewById(R.id.account_image_settings);



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
    }
}