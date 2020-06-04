package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;
import com.wambu.ikokazike.Data.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyServicesActivity extends Activity implements UpdateInterface {


    ArrayList<UserService> allServiceList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    DatabaseReference databaseServices;
    FirebaseAuth firebaseAuth;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewServices;
    Toolbar toolbarMyListings;

    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myservices);

        firebaseAuth = FirebaseAuth.getInstance();

        toolbarMyListings= findViewById(R.id.toolbarMyListings);
        toolbarMyListings.setTitle("My Listings");
        toolbarMyListings.setTitleTextColor(Color.BLACK);
        toolbarMyListings.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbarMyListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Working");
        sharedPreferences = getSharedPreferences("KAZIKWOTEDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String userId= firebaseAuth.getCurrentUser().getUid();

        databaseServices = FirebaseDatabase.getInstance().getReference("SERVICE");   //used to be .child(userId);

        query = databaseServices.orderByChild("posterId").equalTo(userId);

        recyclerViewServices= findViewById(R.id.recycler_myServices);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewServices.setLayoutManager(linearLayoutManager);



        ReadAllNotes();







    }

    @Override
    protected void onResume() {
        super.onResume();
        ReadAllNotes();
    }

    public void ReadAllNotes(){

        allServiceList.clear();
        progressDialog.show();
        final String userId= firebaseAuth.getCurrentUser().getUid();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                allServiceList.clear();  //delete if fail occurs

                progressDialog.dismiss();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){ //dataSnapshot will return all our data, so this is how we can get a single entry

                    UserService userService = snapshot.getValue(UserService.class);
                    allServiceList.add(userService);
                }
                progressDialog.dismiss();
               ServiceAdapter  serviceAdapter = new ServiceAdapter(MyServicesActivity.this,allServiceList,userId);
               recyclerViewServices.setAdapter(serviceAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void updateUserService(final UserService userService) {



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                query.getRef().setValue(userService).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Changes successfully saved",Toast.LENGTH_SHORT).show();
                            ReadAllNotes();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Changes could not be saved",Toast.LENGTH_SHORT).show();


                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*

        databaseServices.child().setValue(userService).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Changes successfully saved",Toast.LENGTH_SHORT).show();
                    ReadAllNotes();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Changes could not be saved",Toast.LENGTH_SHORT).show();


                }

            }
        });

         */

    }

    @Override
    public void deleteUserService(UserService userService) {


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                query.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Listing Deleted",Toast.LENGTH_SHORT).show();
                            ReadAllNotes();

                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database Error", "onCancelled", databaseError.toException());
            }
        });


/*
       query.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Listing Deleted",Toast.LENGTH_SHORT).show();
                    ReadAllNotes();

                }

            }
        });

       */

    }


    @Override
    public void populateSpinner(Spinner spinner,  int a) {

        List<String> rates = new ArrayList<String>();
        rates.add("Below 5000");
        rates.add("Ksh 5000-10000");
        rates.add("Ksh 10000-15000");
        rates.add("Ksh 15000-20000");
        rates.add("Ksh 20000-30000");
        rates.add("Above 30000");
        rates.add("[Update your rate]");


        List<String> services = new ArrayList<String>();
        services.add("Painter");
        services.add("Plumber");
        services.add("Electrician");
        services.add("Handsman");
        services.add("Welder");
        services.add("[Update your job category]");


        





       // String [] rateArray = new String[]{"Below 5000","Ksh 5000-10000","Ksh 10000-15000","Ksh 15000-20000","Ksh 20000-30000","Above 30000","Update your rate"};
        //String[] serviceArray = new String[]{"Painter", "Plumber", "Electrician", "Handsman", "Welder","" };


       List<String> dataSet = new ArrayList<String>();

        if (a == 1 ){
            dataSet.addAll(rates);

        }
        else if(a == 2){
            dataSet.addAll(services);

        }


        final int listSize = dataSet.size() - 1;



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dataSet){
            @Override
            public int getCount() {
                return listSize; //Truncate list
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //int selectedPosition = dataAdapter.getPosition(string);
        //spinner.setSelection(listSize); remove to revert


        //spinner.setSelection(Arrays.asList(servicesArray).indexOf(string)); // Hidden item to appear in the spinner










        }

    @Override
    public void updateRateSpinnerPosition(Spinner spinner, String string) {

        String [] rateArray = new String[]{"Below 5000","Ksh 5000-10000","Ksh 10000-15000","Ksh 15000-20000","Ksh 20000-30000","Above 30000"};

        spinner.setSelection(Arrays.asList(rateArray).indexOf(string));
    }

    @Override
    public void updateCategorySpinnerPosition(Spinner spinner, String string) {

        String[] serviceArray = new String[]{"Painter", "Plumber", "Electrician", "Handsman", "Welder" };

        spinner.setSelection(Arrays.asList(serviceArray).indexOf(string));

    }


}
