package com.example.mealer28;

//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emaillogin, passwordlogin;
    private ProgressBar progressbarlogin;

    // Radio Button
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

    // Not sure yet
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealer28-8f37d-default-rtdb.firebaseio.com");



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //...

        getSupportActionBar().setTitle("Login");

        emaillogin = findViewById(R.id.emaillogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        progressbarlogin = findViewById(R.id.progressbarlogin);

        // Radio...
        radioGroup = findViewById(R.id.radioGroup);

        authProfile = FirebaseAuth.getInstance();

        // Login User
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pour la radio Button
                int checkedId = radioGroup.getCheckedRadioButtonId();

                String textmail = emaillogin.getText().toString();
                String textpassword = passwordlogin.getText().toString();


                if (TextUtils.isEmpty(textmail)){
                    Toast.makeText(LoginActivity.this, "Please, enter your email", Toast.LENGTH_SHORT).show();
                    emaillogin.setError("Email is required");
                    emaillogin.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textmail).matches()){
                    Toast.makeText(LoginActivity.this,  "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    emaillogin.setError("Valid email is required");
                    emaillogin.requestFocus();
                } else if (TextUtils.isEmpty(textpassword)) {
                    Toast.makeText(LoginActivity.this,  "Please, enter your password", Toast.LENGTH_SHORT).show();
                    passwordlogin.setError("Password is required");
                    passwordlogin.requestFocus();
                } else {

                    if (checkedId == -1)
                        Toast.makeText(LoginActivity.this,  "Please, select who you are login in as", Toast.LENGTH_SHORT).show();
                    else {
                        progressbarlogin.setVisibility(View.VISIBLE);
                        loginUser(textmail, textpassword, checkedId);
                    }
                }
            }
        });
    }


    private void loginUser(String email, String password, int checkedId){
        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "You are logged in now ", Toast.LENGTH_SHORT).show();
                    //Test
                    switch(checkedId) {
                        case R.id.typeClient :
                            Intent intent =  new Intent(LoginActivity.this, ClientProfileActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "You are logged in as Client ", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.typeCook:
                            Intent intent1 =  new Intent(LoginActivity.this, CookProfileActivity.class);
                            startActivity(intent1);
                            finish();
                            Toast.makeText(LoginActivity.this, "You are logged in as Cook ", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.typeAdministrator:
                            Intent intent2 =  new Intent(LoginActivity.this, AdminProfileActivity.class);
                            startActivity(intent2);
                            finish();
                            Toast.makeText(LoginActivity.this, "You are logged in as Administrator ", Toast.LENGTH_SHORT).show();
                            break;

                    }

                }  else {
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidUserException e){
                        emaillogin.setError("User does not exists or is no longer valid. Please register again !");
                        emaillogin.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e){
                        emaillogin.setError("Invalid credentials. Kindly, check and re-enter");
                        emaillogin.requestFocus();
                    } catch(Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this, "Something went wrong !", Toast.LENGTH_LONG).show();
                    }
                }
                progressbarlogin.setVisibility(View.GONE);
            }
        });
    }

}