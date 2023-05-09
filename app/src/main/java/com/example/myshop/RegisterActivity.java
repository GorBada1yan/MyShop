package com.example.myshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myshop.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPhone;
    private Button mRegisterButton;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUsername = findViewById(R.id.register_username_input);
        mPhone = findViewById(R.id.register_phone_input);
        mEmail = findViewById(R.id.register_email_input);
        mPassword = findViewById(R.id.register_password_input);
        mRegisterButton = findViewById(R.id.register_btn);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();


                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("Имя пользователя обязательно");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email обязателен");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Пароль обязателен");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    mUsername.setError("Номер телефона обязателен");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Create a new User object with the user's details
                                        Users newUser = new Users(username, email,password, phone);

                                        // Save the user's data to the "users" node in the database
                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(user.getUid()).setValue(newUser)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Send verification email
                                                            user.sendEmailVerification()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText(RegisterActivity.this, "Проверьте свой электронный адрес, чтобы подтвердить свою учетную запись", Toast.LENGTH_LONG).show();
                                                                                mAuth.signOut();
                                                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                                                finish();
                                                                            } else {
                                                                                Toast.makeText(RegisterActivity.this, "Не удалось отправить письмо с подтверждением по электронной почте", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "Не удалось сохранить данные пользователя в базе данных", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Регистрация не удалась: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }



}