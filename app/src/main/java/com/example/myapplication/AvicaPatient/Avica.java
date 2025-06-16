package com.example.myapplication.AvicaPatient;

import android.app.Application;
import android.content.Context;

import com.example.myapplication.AvicaPatient.HttpUtils.HttpRequestHandler;
import com.tencent.mmkv.MMKV;

public class Avica extends Application {


    public static final String TAG = Avica.class.getSimpleName();

    private static Context context;
    private static Avica mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = this.getApplicationContext();
        HttpRequestHandler.setAndroidContext(this);
        MMKV.initialize(this);

    }

    public static synchronized Avica getInstance() {
        return mInstance;
    }
    public static Context getContext(){
        return context;
    }


}
