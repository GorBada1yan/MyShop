package com.example.myshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myshop.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    

    private EditText loginEmailEditText, loginPasswordEditText;
    private Button loginButton;
    private CheckBox checkBoxRememberMe;
    private TextView forgetPasswordTextView, adminPanelTextView, notAdminPanelTextView;

    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);

        loginEmailEditText = findViewById(R.id.login_email_input);
        loginPasswordEditText = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.login_btn);
        checkBoxRememberMe = findViewById(R.id.login_checkbox);
        forgetPasswordTextView = findViewById(R.id.forget_password_link);
        Paper.init(this);

        mAuth = FirebaseAuth.getInstance();

        // Запоминаем состояние CheckBox при изменении его значения

        checkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoginActivity.this.isChecked = isChecked;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("rememberMe", isChecked);
                editor.apply();
            }
        });

        // Устанавливаем сохраненное значение в CheckBox при запуске приложения
        isChecked = preferences.getBoolean("rememberMe", false);
        checkBoxRememberMe.setChecked(isChecked);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement forget password functionality
            }
        });
    }

    private void loginUser() {
        String email = loginEmailEditText.getText().toString().trim();
        String password = loginPasswordEditText.getText().toString().trim();

        if (checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserEmailKey, email);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }




        if (email.isEmpty()) {
            loginEmailEditText.setError("Введите адрес электронной почты");
            loginEmailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loginPasswordEditText.setError("Введите пароль");
            loginPasswordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Вход выполнен", Toast.LENGTH_SHORT).show();

                            // Если пользователь выбрал "Запомнить меня", сохраняем его email и пароль в SharedPreferences
                            if (isChecked) {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.apply();
                            }

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Ошибка входа: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
