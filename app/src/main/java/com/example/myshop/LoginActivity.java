package com.example.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailEditText, loginPasswordEditText;
    private Button loginButton;
    private CheckBox rememberMeCheckBox;
    private TextView forgetPasswordTextView, adminPanelTextView, notAdminPanelTextView;

    private FirebaseAuth mAuth;

    private TextView AdminLink, NotAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginEmailEditText = findViewById(R.id.login_email_input);
        loginPasswordEditText = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.login_btn);
        rememberMeCheckBox = findViewById(R.id.login_checkbox);
        forgetPasswordTextView = findViewById(R.id.forget_password_link);
        adminPanelTextView = findViewById(R.id.admin_panel_link);
        notAdminPanelTextView = findViewById(R.id.not_admin_panel_link);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        adminPanelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Войти как Администратор");
                adminPanelTextView.setVisibility(View.INVISIBLE);
                notAdminPanelTextView.setVisibility(View.VISIBLE);
                
            }
        });

        notAdminPanelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Войти");
                notAdminPanelTextView.setVisibility(View.INVISIBLE);
                adminPanelTextView.setVisibility(View.VISIBLE);
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
