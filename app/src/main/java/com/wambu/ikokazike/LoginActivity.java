package com.wambu.ikokazike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView toSignUpTxt;
    private Button btnLogin;
    private EditText editTextEmail,editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toSignUpTxt=findViewById(R.id.signupTxt);
        editTextEmail= findViewById(R.id.txtemail);
        editTextPassword=findViewById(R.id.txtpass);
        btnLogin = findViewById(R.id.loginBtn);

        toSignUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegistration = new Intent(LoginActivity.this,Registration.class);
                startActivity(toRegistration);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
