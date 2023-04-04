package com.example.etaxpointapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class Login extends AppCompatActivity {
    private Button login;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    private FirebaseAuth authProfile;
    private TimePickerDialog timePickerDialog;
EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       //Set id's
        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        //calling the database
        authProfile= FirebaseAuth.getInstance();
            //database=FirebaseDatabase.getInstance();
            // myref = database.getReference("USERS");
        FirebaseUser user = authProfile.getCurrentUser();
        if (user != null) {
            // User is signed in
            startActivity(new Intent(Login.this, HomePage.class));
            finish();
        }
        //log in button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textUsername = username.getText().toString();
                String textPassword = password.getText().toString();
                if(TextUtils.isEmpty(textUsername)){
                    username.setError("Email is required!");
                    username.requestFocus();
             }
                else if(TextUtils.isEmpty(textPassword)){
                    password.setError("Password is required!");
                    password.requestFocus();
                }else
                    progressBar.setVisibility(View.VISIBLE);
                login(textUsername,textPassword);
            }
        });


    }

    private void login(String textUsername,String textPassword){


        authProfile.signInWithEmailAndPassword(textUsername, textPassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(Login.this, HomePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(Login.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }
}