package com.wambu.ikokazike;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;


public class GridViewImageTextActivity extends Activity {

    GridView androidGridView;

    String[] gridViewString = {"Painters", "Plumbers", "Electricians", "Handsmen", "Welder", "New Listing"};

    int[] gridViewImageId = {R.drawable.brush, R.drawable.pipe, R.drawable.electricity, R.drawable.hand, R.drawable.welder, R.drawable.plus};

    Toolbar toolbarHome;

    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_image_text_example);


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
                        Intent tempIntent  = new Intent(getApplicationContext(), MyServicesActivity.class);
                        startActivity(tempIntent);

                        break;

                    case 1:
                        Toast.makeText(getApplicationContext(),"Showing you plumbers in your area...",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(getApplicationContext(),"Showing you electricians in your area...",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(getApplicationContext(),"Showing you handsmen in your area...",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Toast.makeText(getApplicationContext(),"Showing you welders in your area...",Toast.LENGTH_SHORT).show();
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


}
