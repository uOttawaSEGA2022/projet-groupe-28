package com.example.mealer28;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Button toClientRegistrationButton;
    private Button toCookRegistrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toClientRegistrationButton = findViewById(R.id.toClientRegistrationButton);
        toCookRegistrationButton = findViewById(R.id.toCookRegistrationButton);

        toClientRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClientRegister();
            }
        });
        toCookRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCookRegister();
            }
        });
    }

    public void goToClientRegister(){
        Intent intent =  new Intent(this, CreateAccountClient.class );
        startActivity(intent);
    }

    public void goToCookRegister(){
        Intent intent =  new Intent(this, CreateAccountCook.class );
        startActivity(intent);
    }




}