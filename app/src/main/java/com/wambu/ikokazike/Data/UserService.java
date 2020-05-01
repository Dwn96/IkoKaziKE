package com.wambu.ikokazike.Data;

import java.io.Serializable;

public class UserService implements Serializable {

    String serviceName="",servicePhone="",serviceCategory="",serviceDescription="",serviceRate="",serviceRegion="",dateAdded="";


    public UserService(String serviceName, String servicePhone, String serviceCategory, String serviceDescription, String serviceRate, String serviceRegion, String dateAdded) {
        this.serviceName = serviceName;
        this.servicePhone = servicePhone;
        this.serviceCategory = serviceCategory;
        this.serviceDescription = serviceDescription;
        this.serviceRate = serviceRate;
        this.serviceRegion = serviceRegion;
        this.dateAdded = dateAdded;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(String serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getServiceRegion() {
        return serviceRegion;
    }

    public void setServiceRegion(String serviceRegion) {
        this.serviceRegion = serviceRegion;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }


    public UserService(String serviceName, String servicePhone, String serviceDescription, String dateAdded, String serviceRegion) {
        this.serviceName = serviceName;
        this.servicePhone = servicePhone;
        this.serviceDescription = serviceDescription;
        this.dateAdded = dateAdded;
        this.serviceRegion=serviceRegion;
    }
}
