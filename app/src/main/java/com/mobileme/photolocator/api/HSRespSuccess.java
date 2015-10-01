package com.mobileme.photolocator.api;

import java.io.Serializable;

/**
 * Class of response (success or not)
 * Created by User on 27.08.2015.
 */
public class HSRespSuccess implements Serializable {
    private boolean success;
    private String message;


    public boolean getSuccess() {
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

    @Override
    public String toString() {
        return "HSRespSuccess{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
