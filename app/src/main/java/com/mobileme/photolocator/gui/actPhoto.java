package com.mobileme.photolocator.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileme.photolocator.R;
import com.mobileme.photolocator.api.HSPhoto;
import com.mobileme.photolocator.dao.Utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 17.09.2015.
 */
public class actPhoto extends Activity implements SurfaceHolder.Callback, View.OnClickListener,
        Camera.PictureCallback, Camera.AutoFocusCallback, LocationListener {

    public static String EXTRA_OUTPUT = "output";
    public  static String PHOTO_OBJ = "photoobj";

    public static final int MODE_PREVIEW = 1;
    public static final int MODE_SHOOTING = 2;
    public static final int MODE_SHOOTED = 3;

    public static final int ACTION_OK = 1;
    public static final int ACTION_SHOOT = 2;
    public static final int ACTION_CANCEL = 3;

    private Camera camera ;
    private byte[] photoData;
    SurfaceView surface;
    SurfaceHolder surfaceHolder;
    DisplayMetrics dm;

    Button btnOk;
    Button btnCancel;
    EditText edComment;

    private int mode = MODE_PREVIEW;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camera = null;
        photoData = null;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setContentView(R.layout.actphoto);
        surface = (SurfaceView) findViewById(R.id.surface);

        surfaceHolder = surface.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction(ACTION_OK);
            }
        });

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction(ACTION_CANCEL);
            }
        });
        edComment = (EditText)findViewById(R.id.edComment);
        updateState();
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        camera.takePicture(null, null, this);
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w/h;

        if (sizes==null) return null;

        Camera.Size optimalSize = null;

        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Find size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try
        {
            if (camera == null) {
                camera = Camera.open();
            }
            camera.setPreviewDisplay(holder);
            //camera.setPreviewCallback(this);
        }
        catch (IOException e)
        {
        }


        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) dm.widthPixels / dm.heightPixels;

        int previewSurfaceWidth = dm.widthPixels;
        int previewSurfaceHeight = dm.heightPixels;

        Camera.Parameters par = camera.getParameters();


        //par.setPictureSize(1280, 960);
        par.setJpegQuality(70);
        par.setJpegThumbnailQuality(1);

        List<Camera.Size> sizes = par.getSupportedPreviewSizes();
        Camera.Size optimalSize = getOptimalPreviewSize(sizes, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

        par.setPreviewSize(optimalSize.width, optimalSize.height);

        ViewGroup.LayoutParams lp = surface.getLayoutParams();

        // ландшафтный
        par.setRotation(0);
        try {
            camera.setParameters(par);
        } catch (Exception e) {
            //do nothing
        }

        // здесь корректируем размер отображаемого preview, чтобы не было искажений
/*        lp.width = previewSurfaceWidth;
        lp.height = (int) (previewSurfaceWidth / aspect);

        surface.setLayoutParams(lp);
*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data != null) {
            photoData = data.clone();
        }
        camera.cancelAutoFocus();
        camera.stopPreview();
        doAction(ACTION_SHOOT);
    }

    @Override
    protected void onResume()
    {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        super.onResume();
        if (camera == null) {
            camera = Camera.open();
        }
        if (camera != null) {
            camera.startPreview();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);

        if (camera != null)
        {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void doAction(int action) {
        switch (mode) {
            case MODE_PREVIEW:
                if (action == ACTION_OK) {
                    mode = MODE_SHOOTING;
                    camera.autoFocus(this);
                } else {
                        this.finish();
                }
                break;
            case MODE_SHOOTING:
                if (action == ACTION_SHOOT) {
                    mode = MODE_SHOOTED;
                } else {
                    this.finish();
                }
                break;
            case MODE_SHOOTED:
                if (action == ACTION_OK) {
                    // save data
/*                    try {
                        FileOutputStream stream = new FileOutputStream(outputFileName);
                        stream.write(photoData);
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    Location loc = getBestLocation();
                    if (loc != null) {
                        Intent resInt = new Intent();
                        HSPhoto photo = new HSPhoto();
                        photo.setCreatedate(Calendar.getInstance().getTime().getTime());
                        photo.setLatitude(loc.getLatitude());
                        photo.setLongitude(loc.getLongitude());
                        photo.setName(edComment.getText().toString());
                        photo.setImage(photoData);
                        photo.setImagemini(Utils.rescale(photoData));
                        photo.setStatusdate(Calendar.getInstance().getTime().getTime());
                        resInt.putExtra(PHOTO_OBJ, photo);
                        setResult(RESULT_OK, resInt);
                        this.finish();
                    } else {
                        Toast.makeText(this, "Не определены координаты", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mode = MODE_PREVIEW;
                    camera.startPreview();
                }
                break;
        }
        updateState();
    }

    private Location getBestLocation() {
        Location locWIFI = null;
        Location locGPS = null;

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locWIFI = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (locGPS != null)
            return locGPS;
        else
            return locWIFI;
    }

    private void updateState() {
        switch (mode) {
            case MODE_PREVIEW:
                btnOk.setText("СФОТОГРАФИРОВАТЬ");
                btnOk.setEnabled(true);
                edComment.setVisibility(View.INVISIBLE);
                break;
            case MODE_SHOOTING:
                btnOk.setText("ФОТОГРАФИРУЕМ...");
                btnOk.setEnabled(false);
                edComment.setVisibility(View.INVISIBLE);
                break;
            case MODE_SHOOTED:
                btnOk.setText("ОТПРАВИТЬ");
                btnOk.setEnabled(true);
                edComment.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
