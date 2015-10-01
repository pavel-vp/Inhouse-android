package com.mobileme.photolocator.api;

import java.io.Serializable;

/**
 * Created by User on 31.08.2015.
 */
public class HSRequestInfo implements Serializable {
    private int idx;
    private String name;
    private HSServiceInfo serviceInfo;
    private String description;
    private long timeCreate;
    private long timeTarget;
    private long timeFinish;
    private int status;
    private HSVendorInfo vendorInfo;


    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public long getTimeTarget() {
        return timeTarget;
    }

    public void setTimeTarget(long timeTarget) {
        this.timeTarget = timeTarget;
    }

    public long getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(long timeFinish) {
        this.timeFinish = timeFinish;
    }

    public HSVendorInfo getVendorInfo() {
        return vendorInfo;
    }

    public void setVendorInfo(HSVendorInfo vendorInfo) {
        this.vendorInfo = vendorInfo;
    }

}
