package com.mobileme.inhouse_android.api;

import java.io.Serializable;

/**
 * Created by User on 31.08.2015.
 */
public class HSComment implements Serializable {
    private int idx;
    private String comment;
    private HSProfileInfo userProfile;
    private long dateCreate;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public HSProfileInfo getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(HSProfileInfo userProfile) {
        this.userProfile = userProfile;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }
}
