package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView passwordReset;
    EditText forgotPassEmailTxt;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        passwordReset = findViewById(R.id.passwordResetImage);
        forgotPassEmailTxt= findViewById(R.id.passwordResetEditText);
        progressDialog = new ProgressDialog(this);

        passwordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Working...");
                progressDialog.show();

                String email = forgotPassEmailTxt.getText().toString();

                if(!email.isEmpty()){
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Check your email for the password reset link",Toast.LENGTH_LONG).show();
                                Intent intent  = new Intent(getApplicationContext(),GridViewImageTextActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error Resetting Password, Try Again Later",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter a valid email",Toast.LENGTH_LONG).show();

                }


            }
        });


    }
}
