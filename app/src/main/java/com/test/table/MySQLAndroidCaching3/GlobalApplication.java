package com.test.table.MySQLAndroidCaching3;

import android.app.Application;
import android.content.Context;


public class GlobalApplication extends Application {
//used for appContext across entire App
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalApplication.appContext=getApplicationContext();
    }

    public static Context getAppContext() {
        return GlobalApplication.appContext;
    }
}

