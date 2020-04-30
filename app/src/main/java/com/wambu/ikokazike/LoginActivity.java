package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView toSignUpTxt,forgotPasswordTv,OTPtextView;
    private Button btnLogin;
    private EditText editTextEmail,editTextPassword;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    SharedPreferences  sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences = getSharedPreferences("KAZIKWOTEDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        OTPtextView= findViewById(R.id.OtpTextView);

        toSignUpTxt=findViewById(R.id.signupTxt);
        editTextEmail= findViewById(R.id.txtemail);
        editTextPassword=findViewById(R.id.txtpass);
        btnLogin = findViewById(R.id.loginBtn);
        forgotPasswordTv= findViewById((R.id.forgotPasswordtxt));

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
                String emailId= editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(!emailId.isEmpty() && emailId.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") && !password.isEmpty()){
                    loginUser(emailId,password);

                }

                if(emailId.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email Field is empty", Toast.LENGTH_SHORT).show();
                }
                if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password field is empty", Toast.LENGTH_SHORT).show();
                }


                if(password.isEmpty() && emailId.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Both fields are empty", Toast.LENGTH_SHORT).show();
                }

                if(!emailId.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
                    Toast.makeText(getApplicationContext(),"That email doesn't look quite right ", Toast.LENGTH_SHORT).show();
                }

            }
        });


       forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
               startActivity(intent);
           }
       });

       OTPtextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent toOTP = new Intent(getApplicationContext(),OTPActivity.class);
               startActivity(toOTP);

           }
       });

    }

    public void loginUser(String email, String password){
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    editor.putBoolean("LOGINSTATUS",true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),GridViewImageTextActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    editor.putBoolean("LOGINSTATUS",false);
                    editor.commit();
                    String errorMsg = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),"" +errorMsg, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}
