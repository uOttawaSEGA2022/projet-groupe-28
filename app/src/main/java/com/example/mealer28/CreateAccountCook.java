package com.example.mealer28;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountCook<addOn> extends AppCompatActivity {

    private EditText fullnameCook, emailCook, passwordCook, confirmpasswordCook, addressCook, voidcheckCook, descriptionCook;
    private ProgressBar progressBarCook;
    private static final String TAG = "CreateAccountCook";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_cook);


        getSupportActionBar().setTitle("Register as Cook");

        Toast.makeText(CreateAccountCook.this, "You can register now", Toast.LENGTH_LONG).show();

        progressBarCook = findViewById(R.id.progressbarCook);
        fullnameCook = findViewById(R.id.fullnameCook);
        emailCook = findViewById(R.id.emailCook);
        passwordCook = findViewById(R.id.passwordCook);
        confirmpasswordCook = findViewById(R.id.confirmpasswordCook);
        addressCook = findViewById(R.id.addressCook);
        voidcheckCook = findViewById(R.id.voidcheckCook);
        descriptionCook = findViewById(R.id.descriptionCook);


        Button registerCookButton = findViewById(R.id.registerCookButton);
        registerCookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenir les valeurs d'entrées éditées par l'utilisateur
                String textfullname = fullnameCook.getText().toString();
                String textmail = emailCook.getText().toString();
                String textpassword = passwordCook.getText().toString();
                String textconfirmpassword= confirmpasswordCook.getText().toString();
                String textaddress  = addressCook.getText().toString();
                String textvoidcheck = voidcheckCook.getText().toString();
                String textdescription = descriptionCook.getText().toString();

                // On vérifie si toutes les entrées sont correctement entrées et on affiche les messages d'erreur pertinents

                if (TextUtils.isEmpty(textfullname)){
                    Toast.makeText(CreateAccountCook.this, "Please, enter your full name", Toast.LENGTH_LONG).show();
                    fullnameCook.setError("Full name is required");
                    fullnameCook.requestFocus();
                } else if (TextUtils.isEmpty(textmail)){
                    Toast.makeText(CreateAccountCook.this, "Please, enter your email address", Toast.LENGTH_LONG).show();
                    emailCook.setError("Email is required");
                    emailCook.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textmail).matches()){
                    Toast.makeText(CreateAccountCook.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    emailCook.setError("Valid email is required !");
                    emailCook.requestFocus();
                } else if (TextUtils.isEmpty(textaddress)){
                    Toast.makeText(CreateAccountCook.this, "Please, enter your address", Toast.LENGTH_LONG).show();
                    addressCook.setError("Address is required");
                    addressCook.requestFocus();
                } else if (TextUtils.isEmpty(textvoidcheck)){
                    Toast.makeText(CreateAccountCook.this, "Please, enter your void check", Toast.LENGTH_LONG).show();
                    voidcheckCook.setError("Void check is required");
                    voidcheckCook.requestFocus();
                } else if (TextUtils.isEmpty(textdescription)) {
                    Toast.makeText(CreateAccountCook.this, "Please, enter your description", Toast.LENGTH_LONG).show();
                    descriptionCook.setError("Void check is required");
                    descriptionCook.requestFocus();
                }else if (TextUtils.isEmpty(textpassword)){
                    Toast.makeText(CreateAccountCook.this, "Please, enter your password", Toast.LENGTH_LONG).show();
                    passwordCook.setError("Password is required");
                    passwordCook.requestFocus();
                } else if (TextUtils.isEmpty(textconfirmpassword)){
                    Toast.makeText(CreateAccountCook.this, "Please, confirm your password", Toast.LENGTH_LONG).show();
                    confirmpasswordCook.setError("Confirmation is required");
                    confirmpasswordCook.requestFocus();
                } else if (!textpassword.equals(textconfirmpassword)){
                    Toast.makeText(CreateAccountCook.this, "Please, same password", Toast.LENGTH_LONG).show();
                    confirmpasswordCook.setError("Confirmation is required");
                    confirmpasswordCook.requestFocus();
                } else {
                    progressBarCook.setVisibility(View.VISIBLE);
                    registerCook(textfullname, textmail, textpassword, textaddress, textvoidcheck, textdescription);
                }
            }
        });
    }

    private void registerCook(String textfullname, String textmail, String textpassword, String textaddress, String textvoidcheck, String textdescription){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Création du profile du cuisinier
        auth.createUserWithEmailAndPassword(textmail, textpassword).addOnCompleteListener(CreateAccountCook.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(CreateAccountCook.this, "User registered successfull", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    // On envoie à la FireBase RealTime DataBase
                    CookDetails writeCookDetails = new CookDetails(textfullname, textmail, textpassword, textaddress, textvoidcheck, textdescription);
                    // On extrait la réfrérence à partir de Database for "Users"
                    DatabaseReference referenceCook = FirebaseDatabase.getInstance().getReference("Users");


                    // referenceCook.child("Cook").child(firebaseUser.getUid()).setValue(writeCookDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                    referenceCook.child("Cook").child(firebaseUser.getUid()).setValue(writeCookDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(CreateAccountCook.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                progressBarCook.setVisibility(View.GONE);

                               // On envoie l'utilisateur dans la page de connexion
                                Intent intent =  new Intent(CreateAccountCook.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CreateAccountCook.this, "User registered failed. Please try again.", Toast.LENGTH_LONG).show();
                                progressBarCook.setVisibility(View.GONE);

                            }

                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        passwordCook.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and symbols");
                        passwordCook.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e){
                        emailCook.setError("Your email is invalid or already in use. Kindly re-enter.");
                        emailCook.requestFocus();
                    } catch(FirebaseAuthUserCollisionException e){
                        emailCook.setError("User is already registered with this email. Use another email.");
                        emailCook.requestFocus();
                    } catch(Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(CreateAccountCook.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBarCook.setVisibility(View.GONE);
                }
            }
        });
    }
}