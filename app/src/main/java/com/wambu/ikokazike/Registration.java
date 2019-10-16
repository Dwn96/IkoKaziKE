package com.wambu.ikokazike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class Registration extends AppCompatActivity {

    private MaterialButton btnRegister, btnLinkToLogin;
    private TextInputLayout inputName, inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputName = findViewById(R.id.rTextName);
        inputEmail = findViewById(R.id.rTextEmail);
        inputPassword = findViewById(R.id.rTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);



btnRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent toLogin= new Intent(Registration.this,LoginActivity.class);
        startActivity(toLogin);



    }
});

    }
}
