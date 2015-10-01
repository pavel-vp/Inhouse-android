package com.mobileme.photolocator.dao;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mobileme.photolocator.api.HSPhoto;
import com.mobileme.photolocator.api.HSRespPrefs;
import com.mobileme.photolocator.api.HSRespSuccess;
import com.mobileme.photolocator.api.IRestApi;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by User on 27.08.2015.
 */
public class HSRestHelper {

    public static String UPDATEPHOTOLIST_INTENT_NAME = "updatephotolist";

    private static HSRestHelper hsRestHelper = null;
    private static IRestApi service = null;

    public static HSRestHelper getInstance(Context ctx) {
        if (hsRestHelper == null) {
            hsRestHelper = new HSRestHelper(ctx);
        }
        return hsRestHelper;
    }

    private HSSettings settings;
    private Context ctx;

    class LoggingInterceptor implements Interceptor {

        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i("HS",String.format("Sending request %s on %s%n%s, body %s",
                    request.url(), chain.connection(), request.headers(), request.body()));

            com.squareup.okhttp.Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i("HS", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }


    public HSRestHelper(Context ctx) {
        this.ctx = ctx;
        this.settings = new HSSettings(ctx);
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(HSSettings.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(IRestApi.class);
    }


    public void getPrefs(Callback<HSRespPrefs> cb) {
        Call<HSRespPrefs> callRes = service.getPrefs();
        callRes.enqueue(cb);
    }

    public void addPhoto(Callback<HSRespSuccess> cb, HSPhoto photo) {
        Call<HSRespSuccess> callRes = service.addPhoto(photo);
        callRes.enqueue(cb);
    }

    public HSSettings getSettings() {
        return settings;
    }

    public void setSettings(HSSettings settings) {
        this.settings = settings;
    }

    public void sendDisplayUpdate() {
        // send broadcast to refresh display
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(new Intent(UPDATEPHOTOLIST_INTENT_NAME));
    }


}
