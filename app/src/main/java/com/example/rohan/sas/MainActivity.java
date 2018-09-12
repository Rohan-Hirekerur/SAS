package com.example.rohan.sas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText memail;
    private EditText mpassword;
    private Button msignIn;
    private TextView msignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        msignIn = findViewById(R.id.logIn);
        msignUp = findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();

        msignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

        msignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = memail.getText().toString();
                final String password = mpassword.getText().toString();

                if(email.isEmpty()|| password.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Enter valid email/password",Toast.LENGTH_LONG).show();

                    if(email.isEmpty())
                    memail.setError("Cannot be empty");
                    if(password.isEmpty())
                    mpassword.setError("Cannot be empty");
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification();
                                        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                                        startActivity(intent);
                                        finish();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("Authentication Failed !")
                                                .setMessage("Check your email and/or password")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                })
                                                .setNeutralButton("Forgot", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Toast.makeText(MainActivity.this,"Check your mail to reset password !",Toast.LENGTH_LONG).show();
                                                                    }
                                                                });
                                                    }
                                                })
                                                .setCancelable(true).show();


                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this,QuizActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
