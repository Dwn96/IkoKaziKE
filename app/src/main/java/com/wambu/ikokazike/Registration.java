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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wambu.ikokazike.Data.UserInfo;


public class Registration extends AppCompatActivity {

    private EditText editTextName,editTextEmail,editTextPassword;
    private Button btnRegister;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPreferences = getSharedPreferences("KAZIKWOTEDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        firebaseAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("USERS");
        progressDialog = new ProgressDialog(this);

        editTextName=findViewById(R.id.editText_signup_name);
        editTextEmail=findViewById(R.id.editText_signup_email);
        editTextPassword=findViewById(R.id.editText_signup_password);
        btnRegister=findViewById(R.id.signupBtn);


        if(getIntent().hasExtra("MOBILE")){

            editTextPassword.setVisibility(View.GONE);
        }
        else{
            editTextPassword.setVisibility(View.VISIBLE);

        }



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = "";


                if (getIntent().hasExtra("MOBILE")){

                    if (!name.isEmpty()){

                        if(!email.isEmpty()){

                            progressDialog.setMessage("Working....");
                            progressDialog.show();

                            String phone = getIntent().getExtras().getString("MOBILE");
                            final String uid = getIntent().getExtras().getString("UID");

                            UserInfo userInfo = new UserInfo(name,email,phone);

                            databaseUsers.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();

                                    if(task.isSuccessful()){

                                        editor.putString("UID",uid); //change this to .commit() if ran into issues
                                       // editor.commit();

                                        Toast.makeText(getApplicationContext(),"Successful registration",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Error registering user",Toast.LENGTH_SHORT).show();
                                    }





                                }
                            });


                        }
                        else {

                            Toast.makeText(getApplicationContext(),"Email field is empty",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Name field empty",Toast.LENGTH_SHORT).show();


                    }



                }else{

                    password = editTextPassword.getText().toString();
                    registerUser(name,email,password);
                }







            }
        });




    }

    public void registerUser(final String name,final String email,String password){


        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") ){


            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        //save user info to database
                        FirebaseUser currentUser  = firebaseAuth.getCurrentUser();
                        final String uid = currentUser.getUid();

                        UserInfo userInfo = new UserInfo(name,email,"");

                        databaseUsers.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){

                                    editor.putString("UID",uid);
                                    editor.commit();  //change this to .commit() if ran into issues
                                    Toast.makeText(getApplicationContext(),"Successful registration",Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                            }
                        });


                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Error registering user",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else if (name.isEmpty()){
            Toast.makeText(getApplicationContext(),"Name field is empty", Toast.LENGTH_SHORT).show();

        }
        else if (email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Email field is empty", Toast.LENGTH_SHORT).show();

        }

        else if (!email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
            Toast.makeText(getApplicationContext(),"That email doesn't look right", Toast.LENGTH_SHORT).show();

        }

        else if (password.matches("")){
            Toast.makeText(getApplicationContext(),"Password field is empty", Toast.LENGTH_SHORT).show();

        }







    }

}
