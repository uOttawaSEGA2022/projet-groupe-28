package com.example.mealer28;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class CreateAccountClient extends AppCompatActivity {

    private EditText fullnameClient, emailClient, passwordClient, confirmpasswordClient, addressClient, credicardClient, cvvClient;
    private ProgressBar progressbarClient;
    private static final String TAG = "CreateAccountCook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_client);

        getSupportActionBar().setTitle("Register as Client");

        Toast.makeText(CreateAccountClient.this, "You can register now", Toast.LENGTH_LONG).show();

        progressbarClient = findViewById(R.id.progressbarClient);
        fullnameClient = findViewById(R.id.fullnameClient);
        emailClient = findViewById(R.id.emailClient);
        passwordClient = findViewById(R.id.passwordClient);
        confirmpasswordClient = findViewById(R.id.confirmpasswordClient);
        addressClient = findViewById(R.id.addressClient);
        credicardClient = findViewById(R.id.credicardClient);
        cvvClient = findViewById(R.id.cvvClient);


        Button registerClientButton = findViewById(R.id.registerClientButton);
        registerClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenir les valeurs d'entrées éditées par l'utilisateur
                String textfullname = fullnameClient.getText().toString();
                String textmail = emailClient.getText().toString();
                String textpassword = passwordClient.getText().toString();
                String textconfirmpassword= confirmpasswordClient.getText().toString();
                String textaddress  = addressClient.getText().toString();
                String textcreditcard = credicardClient.getText().toString();
                String textcvv = cvvClient.getText().toString();

                // On vérifie si toutes les entrées sont correctement entrées et on affiche les messages d'erreur pertinents

                if (TextUtils.isEmpty(textfullname)){
                    Toast.makeText(CreateAccountClient.this, "Please, enter your full name", Toast.LENGTH_LONG).show();
                    fullnameClient.setError("Full name is required");
                    fullnameClient.requestFocus();
                } else if (TextUtils.isEmpty(textmail)){
                    Toast.makeText(CreateAccountClient.this, "Please, enter your email address", Toast.LENGTH_LONG).show();
                    emailClient.setError("Email is required");
                    emailClient.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textmail).matches()){
                    Toast.makeText(CreateAccountClient.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    emailClient.setError("Valid email is required !");
                    emailClient.requestFocus();
                } else if (TextUtils.isEmpty(textaddress)){
                    Toast.makeText(CreateAccountClient.this, "Please, enter your address", Toast.LENGTH_LONG).show();
                    addressClient.setError("Address is required");
                    addressClient.requestFocus();
                } else if (TextUtils.isEmpty(textcreditcard)){
                    Toast.makeText(CreateAccountClient.this, "Please, enter your credit card number", Toast.LENGTH_LONG).show();
                    credicardClient.setError("Credit card number is required");
                    credicardClient.requestFocus();
                } else if (TextUtils.isEmpty(textcvv)) {
                    Toast.makeText(CreateAccountClient.this, "Please, enter your cvv", Toast.LENGTH_LONG).show();
                    cvvClient.setError("CVV is required");
                    cvvClient.requestFocus();
                }else if (TextUtils.isEmpty(textpassword)){
                    Toast.makeText(CreateAccountClient.this, "Please, enter your password", Toast.LENGTH_LONG).show();
                    passwordClient.setError("Password is required");
                    passwordClient.requestFocus();
                } else if (TextUtils.isEmpty(textconfirmpassword)){
                    Toast.makeText(CreateAccountClient.this, "Please, confirm your password", Toast.LENGTH_LONG).show();
                    confirmpasswordClient.setError("Confirmation is required");
                    confirmpasswordClient.requestFocus();
                } else if (!textpassword.equals(textconfirmpassword)){
                    Toast.makeText(CreateAccountClient.this, "Please, same password", Toast.LENGTH_LONG).show();
                    confirmpasswordClient.setError("Confirmation is required");
                    confirmpasswordClient.requestFocus();
                } else {
                    progressbarClient.setVisibility(View.VISIBLE);
                    registerClient(textfullname, textmail, textpassword, textaddress, textcreditcard, textcvv);
                }
            }
        });
    }

    private void registerClient(String textfullname,String textmail, String textpassword, String textaddress, String textcreditcard, String textcvv){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Création du profile du client
        auth.createUserWithEmailAndPassword(textmail, textpassword).addOnCompleteListener(CreateAccountClient.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    // On envoie à la FireBase RealTime DataBase
                    ClientDetails writeClientDetails = new ClientDetails(textfullname, textmail, textpassword, textaddress, textcreditcard, textcvv);
                    // On extrait la réfrérence à partir de Database for "Users"
                    DatabaseReference referenceClient = FirebaseDatabase.getInstance().getReference("Users");

                    referenceClient.child("Client").child(firebaseUser.getUid()).setValue(writeClientDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(CreateAccountClient.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                progressbarClient.setVisibility(View.GONE);

                                // On envoie l'utilisateur dans la page de connexion
                                Intent intent =  new Intent(CreateAccountClient.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CreateAccountClient.this, "User registered failed. Please try again.", Toast.LENGTH_LONG).show();
                                progressbarClient.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        passwordClient.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and symbols");
                        passwordClient.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e){
                        emailClient.setError("Your email is invalid or already in use. Kindly re-enter.");
                        emailClient.requestFocus();
                    } catch(FirebaseAuthUserCollisionException e){
                        emailClient.setError("User is already registered with this email. Use another email.");
                        emailClient.requestFocus();
                    } catch(Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(CreateAccountClient.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressbarClient.setVisibility(View.GONE);
                }
            }
        });

    }
}