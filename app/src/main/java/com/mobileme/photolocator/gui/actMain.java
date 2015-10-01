package com.mobileme.photolocator.gui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.mobileme.photolocator.R;
import com.mobileme.photolocator.api.HSPhoto;
import com.mobileme.photolocator.api.HSRespPrefs;
import com.mobileme.photolocator.dao.HSRestHelper;
import com.mobileme.photolocator.service.HSService;
import com.mobileme.photolocator.service.PhotoLocUploadReceiver;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;

public class actMain extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    SwipeListView lvPhotos;
    PhotoListAdapter adapter;
    List<HSPhoto> listPhotos = new ArrayList<>();
    protected LocalBroadcastManager localBroadcastManager;
    private HSRestHelper restHelper;


    private BroadcastReceiver mMainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            actMain.this.updateList();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);
        restHelper = HSRestHelper.getInstance(this);

        lvPhotos=(SwipeListView)findViewById(R.id.lvPhotoList);
        adapter = new PhotoListAdapter(this,R.layout.photoitem, listPhotos);

        lvPhotos.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
        lvPhotos.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
        lvPhotos.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        lvPhotos.setOffsetLeft(150); // left side offset
        lvPhotos.setOffsetRight(150); // right side offset
        lvPhotos.setAnimationTime(300); // Animation time
        lvPhotos.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
        lvPhotos.setAdapter(adapter);
        updateList();

        lvPhotos.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
                for (int i = 0; i < listPhotos.size(); i++) {
                    if (i != position) {
                        lvPhotos.closeAnimate(i);
                    }
                }
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
                for (int i = 0; i < listPhotos.size(); i++) {
                    lvPhotos.closeAnimate(i);
                }
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));

                lvPhotos.openAnimate(position); //when you touch front view it will open

            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));

                lvPhotos.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }

        });

        restHelper.getPrefs(
                new Callback<HSRespPrefs>() {
                    @Override
                    public void onResponse(Response<HSRespPrefs> response) {
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });

        findViewById(R.id.btnPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(actMain.this, actPhoto.class);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
        restartTimers();
//        service.startService(new Intent());
    }

    @Override
    protected void onDestroy() {
  //      service.stopSelf();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(mMainReceiver, new IntentFilter(HSRestHelper.UPDATEPHOTOLIST_INTENT_NAME));
        startService(new Intent(this, HSService.class));
    }

    @Override
    protected void onStop() {
        localBroadcastManager.unregisterReceiver(mMainReceiver);
        stopService(new Intent(this, HSService.class));
        super.onStop();
    }

    public void updateList() {
        listPhotos.clear();
        listPhotos.addAll(restHelper.getSettings().getDao().getHsPhotoDBHelper().getPhotoList());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final HSPhoto photo = (HSPhoto)(data.getExtras().get(actPhoto.PHOTO_OBJ));
                restHelper.getSettings().getDao().getHsPhotoDBHelper().addPhoto(photo);
                updateList();
                startService(new Intent(this, HSService.class));

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture

            } else {
                // Image capture failed, advise user
            }
        }
    }

    private void restartTimers() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            // Таймер на загрузку
            Intent intent = new Intent(this, PhotoLocUploadReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            // На случай, если мы ранее запускали активити, а потом поменяли время,
            // откажемся от уведомления
            am.cancel(pendingIntent);

            am.setRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_FIFTEEN_MINUTES,  AlarmManager.INTERVAL_HOUR * 2, pendingIntent);

    }


}
