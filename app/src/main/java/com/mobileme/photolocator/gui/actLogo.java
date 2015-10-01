package com.mobileme.photolocator.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mobileme.photolocator.R;

public class actLogo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actlogo);

    }

    @Override
    public void onResume() {
        super.onResume();

        Handler hdl = new Handler();
        hdl.postDelayed(new Runnable() {
            @Override
            public void run() {
                // redirect to appropriate state
                Intent intent = new Intent();
                intent.setClass(actLogo.this, actMain.class);
                startActivity(intent);
                actLogo.this.finish();
            }
        }, 3000);
    }

}
