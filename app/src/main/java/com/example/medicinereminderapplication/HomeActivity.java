package com.example.medicinereminderapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MedicineAdapter adapter;
    DatabaseHelper dbHelper;
    Button btnLogout;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = getIntent().getStringExtra("username");

        recyclerView = findViewById(R.id.recyclerMedicines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        List<Medicine> medicines = dbHelper.getMedicinesForUser(username);
        if (medicines == null) medicines = new ArrayList<>();

        adapter = new MedicineAdapter(medicines, this, medicine -> {
            Intent intent = new Intent(HomeActivity.this, AddEditMedicineActivity.class);
            intent.putExtra("medicineId", medicine.getId());
            intent.putExtra("username", username);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAddMedicine).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddEditMedicineActivity.class);
            Intent.putExtra("username", username);
            startActivity(intent);
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.updateList(dbHelper.getMedicinesForUser(username));//Refresh medicine list after adding/editing medicine
    }
}

//100% Completed