package com.mobileme.photolocator.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 30.09.2015.
 */
public class PhotoLocUploadReceiver extends BroadcastReceiver {

    NotificationManager nm;
    Context ctx;


    @Override
    public void onReceive(Context ctx, Intent in) {
        ctx.startService(new Intent(ctx, HSService.class));

    }
}
