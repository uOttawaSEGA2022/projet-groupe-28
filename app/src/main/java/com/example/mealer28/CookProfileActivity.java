package com.example.mealer28;


import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CookProfileActivity extends AppCompatActivity {

    private TextView welcomeTextCook;
    private Button logoutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);

        getSupportActionBar().setTitle("Cook Home");

        welcomeTextCook = findViewById(R.id.welcomeTextCook);
        welcomeTextCook.setText("Welcome");

        logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CookProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}