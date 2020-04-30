package com.wambu.ikokazike;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toolbar;

public class AddService extends AppCompatActivity {

    EditText addName,addPhone,addDescription;
    Spinner  addCategory,addRate,addRegion;
    Button postService;
    Toolbar toolbarAddService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        addName= findViewById(R.id.editText_addService_name);
        addPhone= findViewById(R.id.editText_addService_phone);
        addDescription = findViewById(R.id.editText_addService_description);
        addCategory= findViewById(R.id.spinner_addCategory);
        addRate= findViewById(R.id.spinner_addRate);
        addRegion= findViewById(R.id.spinner_addRegion);
        postService= findViewById(R.id.button_postService);
        toolbarAddService= findViewById(R.id.toolbarAddService);


        toolbarAddService.setTitle("Add A Service");
        toolbarAddService.setTitleTextColor(Color.BLACK);
        toolbarAddService.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbarAddService.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });









    }
}
