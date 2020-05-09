package com.wambu.ikokazike;

import android.widget.Spinner;
import android.widget.TextView;

import com.wambu.ikokazike.Data.UserService;

public interface UpdateInterface {

    void updateUserService(UserService userService);
    void populateSpinner(Spinner spinner, String string, int a);
    //public void getIndexofSelectedSpinnerItem(Spinner spinner, );
    //public void autoCompleteAddress(TextView textView);
}
