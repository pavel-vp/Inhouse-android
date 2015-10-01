package com.mobileme.photolocator.dao;

import android.content.Context;

/**
 * Created by User on 27.08.2015.
 */
public class HSSettings {

    public static final String BASE_URL = "http://test3-inhouseapp.rhcloud.com/";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "hsbase";

    public static final int STATUS_NEW = 0;
    public static final int STATUS_SENDING = 1;
    public static final int STATUS_SENDOK = 2;
    public static final int STATUS_SENDERROR = 3;

    private Dao dao;

    Context ctx;

    public HSSettings(Context context) {
        this.ctx = context;
        this.dao = new Dao(ctx);
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }



}
