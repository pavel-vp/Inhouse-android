package com.mobileme.photolocator.dao;

import android.content.Context;

import com.mobileme.photolocator.dbhelper.HSPhotoDBHelper;

/**
 * Created by User on 27.08.2015.
 */
public class Dao {

    HSPhotoDBHelper hsPhotoDBHelper;
    Context ctx;

    public Dao(Context context) {
        this.ctx = context;
        hsPhotoDBHelper = new HSPhotoDBHelper(ctx);
    }

    public HSPhotoDBHelper getHsPhotoDBHelper() {
        return hsPhotoDBHelper;
    }

    public void setHsPhotoDBHelper(HSPhotoDBHelper hsPhotoDBHelper) {
        this.hsPhotoDBHelper = hsPhotoDBHelper;
    }

}
