package com.example.rohan.sas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText conpassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.Remail);
        password = findViewById(R.id.Rpassword);
        conpassword = findViewById(R.id.RconfirmPassword);
        register = findViewById(R.id.Register);
    }
}
