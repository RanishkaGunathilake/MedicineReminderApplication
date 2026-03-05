package com.example.medicinereminderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    Button btnLogin;
    Button btnRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize DatabaseHelper
        db = new DatabaseHelper(this);

        // Connect UI components
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Login Button Click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                // Check empty fields
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this,
                            "Please enter username and password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate username
                if (!isValidUsername(username)) {
                    Toast.makeText(LoginActivity.this,
                            "Username can contain only letters and numbers",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate password
                if (!isValidPassword(password)) {
                    Toast.makeText(LoginActivity.this,
                            "Password must be at least 8 characters and include capital letters, simple letters, numbers and symbols",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Database validation
                boolean isValid = db.validateUser(username, password);

                if (isValid) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register Button Click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Username validation
    private boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9]+$");
    }

    // Password validation
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$");
    }
}