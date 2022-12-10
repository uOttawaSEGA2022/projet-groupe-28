package com.example.mealer28;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdminProfileActivity extends AppCompatActivity {

    private TextView welcomeTextAdmin;
    private Button logoutBtn;
    private ImageButton complaintManagement;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        getSupportActionBar().setTitle("Admin Home");

        welcomeTextAdmin = findViewById(R.id.welcomeTextAdmin);
        welcomeTextAdmin.setText("administrator@admin.com");



        complaintManagement = findViewById(R.id.complaintsManagement);
        complaintManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(AdminProfileActivity.this, ComplaintManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(AdminProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}