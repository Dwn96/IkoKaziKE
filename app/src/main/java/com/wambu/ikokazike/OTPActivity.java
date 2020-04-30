package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    EditText otpPhoneEditText;
    Button verifyButton;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        firebaseAuth= FirebaseAuth.getInstance();

        otpPhoneEditText= findViewById(R.id.editText_OTP);
        verifyButton=findViewById(R.id.button_otpVerify);

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Verifying Phone Number");

        verificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhone(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Verification failed: " +e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        };


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = "+254"+otpPhoneEditText.getText().toString();

                if (!phone.isEmpty()){

                    verifyPhoneNumber(phone);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter a valid phone number ", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }

    //bbbbbbbbbbbbbbbbbbb

    public void verifyPhoneNumber(String phoneNum){

        progressDialog.show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum,60, TimeUnit.SECONDS, this, verificationStateChangedCallbacks );

    }

    public void signInWithPhone(PhoneAuthCredential phoneAuthCredential){

        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Successful Verification",Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = task.getResult().getUser();
                    String uid = currentUser.getUid();
                    Intent intent = new Intent(getApplicationContext(),Registration.class);
                    intent.putExtra("MOBILE",phone);
                    intent.putExtra("UID",uid);
                    startActivity(intent);

                }
                else{

                    Toast.makeText(getApplicationContext(),"Error using OTP login",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

}
