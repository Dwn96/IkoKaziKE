package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wambu.ikokazike.Data.UserService;

import java.util.ArrayList;

public class PainterActivity extends AppCompatActivity {


    ArrayList<UserService> allServiceList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    DatabaseReference databaseServices;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Working");
        sharedPreferences = getSharedPreferences("KAZIKWOTEDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String userId= firebaseAuth.getCurrentUser().getUid();

        databaseServices = FirebaseDatabase.getInstance().getReference("SERVICE").child(userId);






    }

    public void ReadAllNotes(){

        allServiceList.clear();
        progressDialog.show();

        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){ //dataSnapshot will return all our data, so this is how we can get a single entry

                    UserService userService = snapshot.getValue(UserService.class);
                    allServiceList.add(userService);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
