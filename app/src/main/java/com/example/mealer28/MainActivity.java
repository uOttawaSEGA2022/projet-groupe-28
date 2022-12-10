package com.example.mealer28;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button toLoginButton;
    private Button toRegistrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toLoginButton = findViewById(R.id.toLoginButton);
        toRegistrationButton = findViewById(R.id.toRegistrationButton);

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });
        toRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });

    }

    public void goToLoginActivity(){
        Intent intent =  new Intent(this, LoginActivity.class );
        startActivity(intent);
    }

    public void goToRegisterActivity(){
        Intent intent =  new Intent(this, RegisterActivity.class );
        startActivity(intent);
    }


}