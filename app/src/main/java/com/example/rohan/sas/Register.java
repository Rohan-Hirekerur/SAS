package com.example.rohan.sas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText memail;
    private EditText mpassword;
    private EditText mconpassword;
    private Button mregister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        memail = findViewById(R.id.Remail);
        mpassword = findViewById(R.id.Rpassword);
        mconpassword = findViewById(R.id.RconfirmPassword);
        mregister = findViewById(R.id.Register);
        mAuth = FirebaseAuth.getInstance();

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = memail.getText().toString();
                String password = mpassword.getText().toString();
                String conPass = mconpassword.getText().toString();

                if(email.isEmpty()||password.isEmpty()||conPass.isEmpty())
                {
                    Toast.makeText(Register.this,"Enter valid credentials",Toast.LENGTH_LONG).show();
                    if(email.isEmpty())
                        memail.setError("Cannot be empty");
                    if(password.isEmpty())
                        mpassword.setError("Cannot be empty");
                    if(conPass.isEmpty())
                        mconpassword.setError("Cannot be empty");
                }
                else
                {
                    if(password.equals(conPass)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Intent intent = new Intent(Register.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            AlertDialog.Builder builder= new AlertDialog.Builder(Register.this);
                                            builder.setTitle("User already Exists !")
                                                    .setMessage("Click forgot password to change it")
                                                    .setCancelable(true)
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
                                                                            Toast.makeText(Register.this,"Check your mail to reset password !",Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                });
                    }
                    else {
                        mpassword.setError("Passwords don't match !");
                        mconpassword.setError("Passwords don't match !");
                    }
                }
            }
        });
    }
}
