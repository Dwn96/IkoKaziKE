package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class PainterActivity extends Activity implements LocationCalcInterface {

    ArrayList<UserService> allPaintersList = new ArrayList<>();
    ProgressDialog progressDialog;
    DatabaseReference databasePainters;
    FirebaseAuth firebaseAuth;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewServices;
    Toolbar toolbarPainters;
    Query query;

    Location mLocation;

    Geocoder coder;
   // double longitude,latitude;

    FusedLocationProviderClient client;

    private MutableLiveData<LatLng> latLngMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);

        client = LocationServices.getFusedLocationProviderClient(this);


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


        client.getLastLocation().addOnSuccessListener(PainterActivity.this, new OnSuccessListener<Location>() {
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




/*
        location= new SimpleLocation(this);

        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }


        final double startLatitude = location.getLatitude();
        final double startLongitude = location.getLongitude();


 */










      firebaseAuth=FirebaseAuth.getInstance();

         toolbarPainters= findViewById(R.id.toolbarPainters);
         toolbarPainters.setTitle("Painters");
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

        String userId= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();


        //databasePainters = FirebaseDatabase.getInstance().getReference("SERVICE");

        databasePainters = FirebaseDatabase.getInstance().getReference("SERVICE").child(userId);
       // query = databasePainters.orderByChild("serviceCategory").equalTo("Painter");



        recyclerViewServices= findViewById(R.id.recycler_Painters);


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



    public void ReadPainters(){


        progressDialog.show();


        databasePainters.orderByChild("serviceCategory").equalTo("Painter").addListenerForSingleValueEvent(new ValueEventListener() {
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



                client.getLastLocation().addOnSuccessListener(PainterActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {



                        double latitude = location.getLatitude();
                        double   longitude = location.getLongitude();

                        Log.e("Start Latitude",String.valueOf(latitude));
                        Log.e("Start Longitude",String.valueOf(longitude));


                      CustomServiceAdapter serviceAdapter = new CustomServiceAdapter(PainterActivity.this,allPaintersList,latitude,longitude);

                      recyclerViewServices.setAdapter(serviceAdapter);



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