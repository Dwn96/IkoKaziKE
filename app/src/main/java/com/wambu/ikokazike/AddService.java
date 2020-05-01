package com.wambu.ikokazike;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;
import com.wambu.ikokazike.Data.UserInfo;
import com.wambu.ikokazike.Data.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;





public class AddService extends Activity {

    EditText addName,addPhone,addDescription, addLocation;
    Spinner  addCategory,addRate,addRegion;
    Button postService;
    Toolbar toolbarAddService;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String TAG = "placeautocomplete";
    TextView tvLocation,tvLocation2;


    DatabaseReference databaseServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);


        addLocation = findViewById(R.id.edit_text_Location);


        //Initialize Places
        Places.initialize(getApplicationContext(),"AIzaSyDfod9sngt3b0l5KAFd50GB0MnN8xZja6Y");

        //Set editText non focusable
        addLocation.setFocusable(false);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initialize place fields list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,
                        Place.Field.NAME);

                //Set filter

                //TypeFilter typeFilter = new


                //Create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        ,fieldList).setCountry("KE").build(AddService.this);


                //Start activity result
                startActivityForResult(intent,100);
            }
        });






        sharedPreferences = getSharedPreferences("KAZIKWOTEDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();










        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving post");



        addName= findViewById(R.id.editText_addService_name);
        addPhone= findViewById(R.id.editText_addService_phone);
        addDescription = findViewById(R.id.editText_addService_description);
        addCategory= findViewById(R.id.spinner_addCategory);

        addRate= findViewById(R.id.spinner_addRate);
        //addRegion= findViewById(R.id.spinner_addRegion);
        postService= findViewById(R.id.button_postService);
        toolbarAddService= findViewById(R.id.toolbarAddService);


        //Initialize spinner data array

        String[] services = new String[]{"Painter", "Plumber", "Electrician", "Handsman", "Welder" };
        String [] rates = new String[]{"Below 5000","Ksh 5000-10000","Ksh 10000-15000","Ksh 15000-20000","Ksh 20000-30000","Above 30000"};

       addCategory.setAdapter(new HintSpinnerAdapter<>(this,services,"Select Category"));
       addRate.setAdapter(new HintSpinnerAdapter<>(this,rates,"Select your rate"));






        String userId = sharedPreferences.getString("UID","");
        databaseServices = FirebaseDatabase.getInstance().getReference("SERVICE").child(userId);


        toolbarAddService.setTitle("Add A Service");
        toolbarAddService.setTitleTextColor(Color.BLACK);
        toolbarAddService.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbarAddService.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        postService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addName.getText().toString();
                String phone = addPhone.getText().toString();
                String category = addCategory.getSelectedItem().toString();
                String description = addDescription.getText().toString();
                String rate = addRate.getSelectedItem().toString();
                String selectedRegion= addLocation.getText().toString();





                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                Calendar calendar = Calendar.getInstance();
                String todaysDate = simpleDateFormat.format(calendar.getTime());

                if(!name.isEmpty()){

                    if(!phone.isEmpty()){

                        if(!description.isEmpty()){

                            progressDialog.show();

                            String key = databaseServices.push().getKey();
                            UserService userService = new UserService(name,phone,category,description,rate,selectedRegion,todaysDate);

                            databaseServices.child(key).setValue(userService).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();

                                        Toast.makeText(getApplicationContext(),"Service Successfully posted", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                    else {

                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Error saving post", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });



                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Description field is empty",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Phone field is empty",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Name field is empty",Toast.LENGTH_SHORT).show();
                }




            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode == RESULT_OK){
            //wHEN SUCCESSFUL
            //INITIALIZE PLACE


            Place place = Autocomplete.getPlaceFromIntent(data);

            //Set address on Editext

            addLocation.setText(place.getAddress());

        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){

            Status status = Autocomplete.getStatusFromIntent(data);

            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();

        }
    }
}
