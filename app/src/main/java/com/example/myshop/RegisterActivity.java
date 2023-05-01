package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText usernameInput, phoneInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn= (Button)findViewById(R.id.register_btn);
        usernameInput = (EditText) findViewById(R.id.register_username_input);
        passwordInput = (EditText) findViewById(R.id.register_password_input);
        phoneInput = (EditText) findViewById(R.id.register_phone_input);

    }
}