package com.mobileme.photolocator.api;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 30.08.2015.
 */
public class HSRespProfile implements Serializable {
    // authorizaion record
    private HSRequestAuth auth;
    // list of available services
    private List<HSServiceInfo> serviceInfoList;
    // profile
    private HSProfileInfo profileInfo;

    public HSRequestAuth getAuth() {
        return auth;
    }

    public void setAuth(HSRequestAuth auth) {
        this.auth = auth;
    }


    public List<HSServiceInfo> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<HSServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    public HSProfileInfo getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(HSProfileInfo profileInfo) {
        this.profileInfo = profileInfo;
    }


}

