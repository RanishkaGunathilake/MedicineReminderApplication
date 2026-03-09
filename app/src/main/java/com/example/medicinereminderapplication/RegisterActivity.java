package com.example.medicinereminderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.medicinereminderapplication.PasswordUtils;

public class RegisterActivity extends AppCompatActivity {

    EditText addUsername, addPassword, confirmPassword;
    Button btnRgsLogin, btnRgsRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        addUsername = findViewById(R.id.addUsername);
        addPassword = findViewById(R.id.addPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnRgsLogin = findViewById(R.id.btnRgsLogin);
        btnRgsRegister = findViewById(R.id.btnRgsRegister);

        btnRgsRegister.setOnClickListener(v -> {
            String username = addUsername.getText().toString().trim();
            String password = addPassword.getText().toString().trim();
            String confirm = confirmPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidUsername(username)) {
                addUsername.setError("Username can contain only letters and numbers");
                return;
            }


            if (!isValidPassword(password)) {
                addPassword.setError("Password must be at least 8 characters and include uppercase, lowercase, numbers and symbols");
                return;
            }

            if (!password.equals(confirm)) {
                confirmPassword.setError("Passwords do not match");
                return;
            }

            if (db.checkUserExists(username)) {
                addUsername.setError("Username already exists");
                return;
            }

            String hashedPassword = PasswordUtils.hasPassword(password);
            boolean inserted = db.addUser(username, hashedPassword);

            if (inserted) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnRgsLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private boolean isValidUsername(String username){
        return username.matches("^[a-zA-Z0-9]+$");
    }

    private boolean isValidPassword(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
