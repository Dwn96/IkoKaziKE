package com.wambu.ikokazike;

import android.widget.Spinner;
import android.widget.TextView;

import com.wambu.ikokazike.Data.UserService;

public interface UpdateInterface {

    void updateUserService(UserService userService);
    void deleteUserService(UserService userService);
    void populateSpinner(Spinner spinner, int a);
    void updateRateSpinnerPosition(Spinner spinner,String string);
    void updateCategorySpinnerPosition(Spinner spinner,String string);

}
