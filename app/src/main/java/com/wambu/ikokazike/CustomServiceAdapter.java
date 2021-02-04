package com.wambu.ikokazike;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.wambu.ikokazike.Data.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CustomServiceAdapter extends RecyclerView.Adapter<CustomServiceAdapter.CustomServiceHolder> {

    //Location location;


    LocationCalcInterface locationCalcInterface;



    private Context context;
    ArrayList<UserService> dataList = new ArrayList<>();


    private TextView tvName,tvLocation,tvRate,tvDistanceCalc;

    private double startLat, startLong;




    public CustomServiceAdapter(Context con , ArrayList<UserService> list, double startLatitude, double startLongitude){

        context=con;
        dataList=list;
        locationCalcInterface = (LocationCalcInterface) context;

        startLat = startLatitude;
        startLong=startLongitude;

    }

    public CustomServiceAdapter(Context con , ArrayList<UserService> list){

        context=con;
        dataList=list;
        locationCalcInterface = (LocationCalcInterface) context;



    }


    @NonNull
    @Override
    public CustomServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_service_item,parent,false);

        CustomServiceHolder serviceHolder = new CustomServiceHolder(view);

        return serviceHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomServiceHolder holder, final int position) {

        UserService userService = dataList.get(position);

        String name = userService.getServiceName();
        String location = userService.getServiceRegion();
        String rate = userService.getServiceRate();
        String distanceCalc = "";


       LatLng p1 = locationCalcInterface.getLocationFromAddress(context,location);

       final double endLatitude = p1.latitude;
       final double endLongitude=p1.longitude;

        Log.e("End latitude",String.valueOf(endLatitude) );
        Log.e("End longitude",String.valueOf(endLongitude) );







        Location loc1 = new Location("");
     loc1.setLatitude(startLat);
     loc1.setLongitude(startLong);

        Location loc2 = new Location("");
        loc2.setLatitude(endLatitude);
        loc2.setLongitude(endLongitude);

        //double distanceInMeters = loc1.distanceTo(loc2);

        final float[] results = new float[3];

        Log.e("Start Latitude",String.valueOf(startLat));
        Log.e("Start Longitude",String.valueOf(startLong));

        Location.distanceBetween(startLat,startLong,endLatitude,endLongitude,results);

      final int  distanceInMeters = (int) results[0];

      String distanceBetween= "" +distanceInMeters/1000+ " Km away";


        Log.e("Calculated Distance",String.valueOf(distanceInMeters));







        tvName.setText(name);
        tvLocation.setText(location);
        tvRate.setText(rate);
        tvDistanceCalc.setText(distanceBetween);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService userService1 = dataList.get(position);
                showDialog(userService1);

            }
        });



    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomServiceHolder extends RecyclerView.ViewHolder{



        public CustomServiceHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.text_row_name);
            tvLocation=itemView.findViewById(R.id.text_row_location);
            tvRate=itemView.findViewById(R.id.text_row_rate);
            tvDistanceCalc=itemView.findViewById(R.id.text_row_calculateDistance);





        }
    }

    public void showDialog(UserService serviceObject){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_view_service);
        dialog.show();


        final TextView tvName = dialog.findViewById(R.id.tv_name);
        final TextView tvPhone = dialog.findViewById(R.id.tv_phone);
        final TextView tvAddress = dialog.findViewById(R.id.tv_Location);
        final TextView tvRate = dialog.findViewById(R.id.tv_Rate);
        final TextView tvDistance = dialog.findViewById(R.id.tv_distanceBetween);

        Toolbar toolbarViewService = dialog.findViewById(R.id.toolbarViewService);

        toolbarViewService.setNavigationIcon(R.drawable.ic_baseline_close_24);
        toolbarViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }


        });



        tvName.setText(serviceObject.getServiceName());
        tvPhone.setText(serviceObject.getServicePhone());
        tvAddress.setText(serviceObject.getServiceRegion());
        tvRate.setText(serviceObject.getServiceRate());
        tvDistance.setText(serviceObject.getServiceDescription());



    }

}
