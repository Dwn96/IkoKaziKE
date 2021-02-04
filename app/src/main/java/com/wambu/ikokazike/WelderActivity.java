package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wambu.ikokazike.Data.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class WelderActivity extends Activity implements LocationCalcInterface {

    ArrayList<UserService> allPaintersList = new ArrayList<>();
    ProgressDialog progressDialog;
    DatabaseReference databasePainters;
    FirebaseAuth firebaseAuth;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewServices;
    Toolbar toolbarPainters;
    Query query;
    AppCompatImageButton imageButton;

    Location mLocation;

    Geocoder coder;
    // double longitude,latitude;

    FusedLocationProviderClient client;

    private MutableLiveData<LatLng> latLngMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welder);

        client = LocationServices.getFusedLocationProviderClient(this);

        imageButton = findViewById(R.id.filter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(WelderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_filter);
                dialog.show();

                Button saveFilter = dialog.findViewById(R.id.button_save_Filter);

                Toolbar toolbarFilter  =  dialog.findViewById(R.id.toolbarFilter);
                toolbarFilter.setNavigationIcon(R.drawable.ic_baseline_close_24);

                toolbarFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                saveFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        /*
                         * Fake it till you make it lmao. This is supposed to "simulate" a requery
                         * after "geofence" has been setup
                         *
                         * */

                        progressDialog.setMessage("Working.");
                        progressDialog.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();

                            }
                        }, 2000);

                    }
                });
            }
        });



        requestPermission();

/*
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);  //change back to private variable in exception occurs

        client.getLastLocation().addOnSuccessListener(PainterActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null){

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Log.d("Latitude",String.valueOf(latitude));
                    Log.d("Longitude",String.valueOf(longitude));



                }

            }
        });


 */

/*
        client.getLastLocation().addOnSuccessListener(PainterActivity.this, new OnSuccessListener<Location>() {   //this is what i commented out last, revert if crash
            @Override
            public void onSuccess(Location location) {



                double latitude = location.getLatitude();
                double   longitude = location.getLongitude();

                    Log.e("Start Latitude",String.valueOf(latitude));
                    Log.e("Start Longitude",String.valueOf(longitude));


                    //  CustomServiceAdapter serviceAdapter = new CustomServiceAdapter(PainterActivity.this,allPaintersList,latitude,longitude);

                    //    recyclerViewServices.setAdapter(serviceAdapter);



            }

        });


*/

/*
        location= new SimpleLocation(this);

        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }


        final double startLatitude = location.getLatitude();
        final double startLongitude = location.getLongitude();


 */


        firebaseAuth = FirebaseAuth.getInstance();

        toolbarPainters = findViewById(R.id.toolbarWelder);
        toolbarPainters.setTitle("Welders");
        toolbarPainters.setTitleTextColor(Color.BLACK);
        toolbarPainters.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbarPainters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Working");

        //String userId= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

//        String userId = firebaseAuth.getCurrentUser().getUid();


        //databasePainters = FirebaseDatabase.getInstance().getReference("SERVICE");

        databasePainters = FirebaseDatabase.getInstance().getReference("SERVICE");  //used to be .child(userID)
        query = databasePainters.orderByChild("serviceCategory").equalTo("Welder");


        recyclerViewServices = findViewById(R.id.recycler_Welder);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewServices.setLayoutManager(linearLayoutManager);

        ReadPainters();


    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    public void ReadPainters() {


        progressDialog.show();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allPaintersList.clear();
                progressDialog.dismiss();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UserService userService = snapshot.getValue(UserService.class);
                    allPaintersList.add(userService);
                }
                progressDialog.dismiss();
                // ServiceAdapter serviceAdapter = new ServiceAdapter(PainterActivity.this,allPaintersList);


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                    return;
                }
                client.getLastLocation().addOnSuccessListener(WelderActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        //The app was crashing if GPS wasn't turned on, so here's a neat little fix

                        if (location != null) {

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();


                            Log.e("Start Latitude", String.valueOf(latitude));
                            Log.e("Start Longitude", String.valueOf(longitude));





                            CustomServiceAdapter serviceAdapter = new CustomServiceAdapter(WelderActivity.this, allPaintersList, latitude, longitude);

                            recyclerViewServices.setAdapter(serviceAdapter);


                        }
                        else{
                            finish();
                            Toast.makeText(getApplicationContext(),"Turn on your GPS", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        }


                    }

                });





                //   Log.e("Lat out successListener",String.valueOf(latitude));
                //    Log.e("Lgn out successListener",String.valueOf(longitude));




                //  CustomServiceAdapter serviceAdapter = new CustomServiceAdapter(PainterActivity.this,allPaintersList,latitude,longitude);
                //   recyclerViewServices.setAdapter(serviceAdapter);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("PainterActivityFirebase","onCancelled",databaseError.toException());

            }
        });


        /*
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allPaintersList.clear();
                progressDialog.dismiss();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    UserService userService = snapshot.getValue(UserService.class);
                    allPaintersList.add(userService);
                    }
                progressDialog.dismiss();
               // ServiceAdapter serviceAdapter = new ServiceAdapter(PainterActivity.this,allPaintersList);

                CustomServiceAdapter serviceAdapter = new CustomServiceAdapter(PainterActivity.this,allPaintersList);

                recyclerViewServices.setAdapter(serviceAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



         */



    }


    @Override
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try{
            address = coder.getFromLocationName(strAddress,5);
            if(address == null ){
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1= new LatLng(location.getLatitude(),location.getLongitude());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return p1;

    }



    private void requestPermission(){

        ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION},1);
    }






}
