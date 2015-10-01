package com.mobileme.photolocator.api;

import java.io.Serializable;

/**
 * Created by User on 31.08.2015.
 */
public class HSRequestProfile implements Serializable {

    private HSRequestAuth auth;
    private HSProfileInfo profileInfo;

    public HSRequestAuth getAuth() {
        return auth;
    }

    public void setAuth(HSRequestAuth auth) {
        this.auth = auth;
    }

    public HSProfileInfo getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(HSProfileInfo profileInfo) {
        this.profileInfo = profileInfo;
    }
}
