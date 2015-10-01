package com.mobileme.photolocator.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mobileme.photolocator.api.HSPhoto;
import com.mobileme.photolocator.api.HSRespSuccess;
import com.mobileme.photolocator.dao.HSRestHelper;
import com.mobileme.photolocator.dao.HSSettings;

import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.Response;

/**
 * Created by User on 24.09.2015.
 */
public class HSService extends Service {

    private HSRestHelper restHelper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("HS","start service");
        restHelper = HSRestHelper.getInstance(this);
        sendPhotosAsync();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.v("HS","stop service");
        super.onDestroy();
    }

    public void sendPhotosAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (restHelper) {  // for sync calls from many places
                    // For all non sended photos
                    for (HSPhoto ph : restHelper.getSettings().getDao().getHsPhotoDBHelper().getPhotoList()) {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MILLISECOND, -3600000);
                        Date checkTime = cal.getTime();

                        final HSPhoto p = ph;
                        if (p.getStatus() == HSSettings.STATUS_NEW || p.getStatus() == HSSettings.STATUS_SENDERROR ||
                                (p.getStatus() == HSSettings.STATUS_SENDING &&  new Date(p.getStatusdate()).before(checkTime)
                                )
                                ) {
                            Log.v("HS", "try send photo=" + p);
                            p.setStatus(HSSettings.STATUS_SENDING);
                            p.setStatusdate(new Date().getTime());
                            p.setStatusMsg(null);
                            restHelper.getSettings().getDao().getHsPhotoDBHelper().updatePhoto(p);
                            restHelper.sendDisplayUpdate();

                            //p.setImage(null);
                            //p.setLongitude(0);
                            //p.setCreatedate(null);
                            //p.setStatusdate(null);
                            //p.setStatusMsg(null);
                           // p.setStatus(0);
                           // p.setLatitude(0);

                            restHelper.addPhoto(
                                    new Callback<HSRespSuccess>() {
                                        @Override
                                        public void onResponse(Response<HSRespSuccess> response) {
                                            Log.v("HS", "sent ok response=" + response.body());
                                            if (response.body() != null && response.body().getSuccess()) {
                                                p.setStatus(HSSettings.STATUS_SENDOK);
                                                p.setStatusMsg(null);
                                            } else {
                                                p.setStatus(HSSettings.STATUS_SENDERROR);
                                                if (response.body() != null) {
                                                    p.setStatusMsg(response.body().getMessage());
                                                } else if (response.raw() != null) {
                                                //    p.setStatusMsg(response.raw().code() + " " + response.raw().message());
                                                }
                                            }
                                            p.setStatusdate(new Date().getTime());
                                            restHelper.getSettings().getDao().getHsPhotoDBHelper().updatePhoto(p);
                                            restHelper.sendDisplayUpdate();
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Log.v("HS", "sent fail p=" + p);
                                            t.printStackTrace();
                                            p.setStatus(HSSettings.STATUS_SENDERROR);
                                            p.setStatusdate(new Date().getTime());
                                           // p.setStatusMsg(t.getMessage());
                                            restHelper.getSettings().getDao().getHsPhotoDBHelper().updatePhoto(p);
                                            restHelper.sendDisplayUpdate();
                                        }
                                    }, p);

                        }
                    }
                }
            }
        }).start();
    }

}
