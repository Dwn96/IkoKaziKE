package com.wambu.ikokazike;

import android.content.Context;
import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;


public interface LocationCalcInterface {

    LatLng getLocationFromAddress(Context context, String strAddress);



}
