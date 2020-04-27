package com.wambu.ikokazike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class GridViewImageTextActivity extends Activity {

    GridView androidGridView;

    String[] gridViewString = {"Painters", "Plumbers", "Electricians", "Handsmen", "Welder", "New Listing"};

    int[] gridViewImageId = {R.drawable.brush, R.drawable.pipe, R.drawable.electricity, R.drawable.hand, R.drawable.welder, R.drawable.plus};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_image_text_example);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(GridViewImageTextActivity.this, gridViewString, gridViewImageId);
        androidGridView=findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(),"Showing you painters in your area...",Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(getApplicationContext(),"You need to be Logged In to do that",Toast.LENGTH_SHORT).show();
                        Intent toLogin = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(toLogin);
                        break;


                }

            }
        });


    }


}
