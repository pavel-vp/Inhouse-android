package com.mobileme.inhouse_android.api;

import java.io.Serializable;

/**
 * Class of response (success or not)
 * Created by User on 27.08.2015.
 */
public class HSRespSuccess implements Serializable {
    private boolean success;
    private String message;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
