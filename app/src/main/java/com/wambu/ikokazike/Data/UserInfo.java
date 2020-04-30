package com.wambu.ikokazike.Data;

import java.io.Serializable;

public class UserInfo implements Serializable {








    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    String userName="",userEmail="", mobileNumber= "";

    public UserInfo(String name, String email, String mobile){
        userName=name;
        userEmail=email;
        mobileNumber=mobile;
  }

    public UserInfo(){


    }





}
