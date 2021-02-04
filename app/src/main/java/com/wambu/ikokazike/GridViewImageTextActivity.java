package com.wambu.ikokazike;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class GridViewImageTextActivity extends Activity   {

    GridView androidGridView;

    String[] gridViewString = {"Painters", "Plumbers", "Electricians", "Handymen", "Welders", "New Listing"};

    int[] gridViewImageId = {R.drawable.brush, R.drawable.pipe, R.drawable.electricity, R.drawable.hand, R.drawable.welder, R.drawable.plus};

    Toolbar toolbarHome;

   SharedPreferences sharedPreferences;
  //  SharedPreferences pref =  getApplicationContext().getSharedPreferences("LocationData",0);
 //   SharedPreferences.Editor editor;



   // SimpleLocation location;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_image_text_example);


        requestPermission();



        //consider removing all this below, lat lng can be assigned at individual service activities


        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);  //change back to private variable in exception occurs

            client.getLastLocation().addOnSuccessListener(GridViewImageTextActivity.this, new OnSuccessListener<Location>() {
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






       // final String

     //   pref =  getApplicationContext().getSharedPreferences("LocationData",0);
     //   editor= pref.edit();

    //    editor.putString("START_LONGITUDE",startLongitude.);





        sharedPreferences = getSharedPreferences("KAZIKWOTEDATA", Context.MODE_PRIVATE);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(GridViewImageTextActivity.this, gridViewString, gridViewImageId);
        androidGridView=findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);

        toolbarHome= findViewById(R.id.toolbarHome);
        toolbarHome.setTitle("KaziKwote");
        toolbarHome.setTitleTextColor(Color.BLACK);
        toolbarHome.setNavigationIcon(R.drawable.ic_account);

        toolbarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMyListing = new Intent(getApplicationContext(), MyServicesActivity.class);
                startActivity(toMyListing);
            }
        });


        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(),"Showing you painters in your area...",Toast.LENGTH_SHORT).show();
                        Intent toPainterIntent  = new Intent(getApplicationContext(), PainterActivity.class);
                        startActivity(toPainterIntent);

                        break;

                    case 1:
                        Toast.makeText(getApplicationContext(),"Showing you plumbers in your area...",Toast.LENGTH_SHORT).show();
                        Intent toPlumberIntent = new Intent(getApplicationContext(),PlumberActivity.class);
                        startActivity(toPlumberIntent);
                        break;

                    case 2:
                        Toast.makeText(getApplicationContext(),"Showing you electricians in your area...",Toast.LENGTH_SHORT).show();
                        Intent toElecIntent = new Intent(getApplicationContext(),ElectricianActivity.class);
                        startActivity(toElecIntent);
                        break;

                    case 3:
                        Toast.makeText(getApplicationContext(),"Showing you handymen in your area...",Toast.LENGTH_SHORT).show();
                        Intent toHandIntent = new Intent(getApplicationContext(),HandsmanActivity.class);
                        startActivity(toHandIntent);
                        break;

                    case 4:
                        Toast.makeText(getApplicationContext(),"Showing you welders in your area...",Toast.LENGTH_SHORT).show();
                        Intent toWelderIntent = new Intent(getApplicationContext(),WelderActivity.class);
                        startActivity(toWelderIntent);
                        break;

                    case 5:

                        /*
                        * So basically here we check if the user login in was successful (in LoginActivity)
                        * if true we jump directly  to AddService else, we go to Login
                        * My keyboards fucked so typing b's is a pain lmao
                        *
                        *
                        * */


                        if(sharedPreferences.getBoolean("LOGINSTATUS",false)){
                            Intent toAddService = new Intent(getApplicationContext(),AddService.class);
                            startActivity(toAddService);
                            break;

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"You need to be Logged In to do that",Toast.LENGTH_SHORT).show();
                            Intent toLogin = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(toLogin);
                        }





                }

            }
        });








    }


    private void requestPermission(){

        ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION},1);
    }




}
