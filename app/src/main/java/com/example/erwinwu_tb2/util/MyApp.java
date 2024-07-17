package com.example.erwinwu_tb2.util;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}