package com.wambu.ikokazike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Registration extends AppCompatActivity {

    private EditText editTextName,editTextEmail,editTextPassword;
    private Button btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName=findViewById(R.id.editText_signup_name);
        editTextEmail=findViewById(R.id.editText_signup_email);
        editTextPassword=findViewById(R.id.editText_signup_password);
        btnRegister=findViewById(R.id.signupBtn);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

}
