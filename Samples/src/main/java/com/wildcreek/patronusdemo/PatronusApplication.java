package com.wildcreek.patronusdemo;

import android.app.Application;
import android.content.Context;

import com.wildcreek.patronus.PatronusManager;
import com.wildcreek.patronus.utils.LogHelper;


public class PatronusApplication extends Application {
    public static Context context;
    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        com.wildcreek.patronusdemo.utils.CrashHandler.getInstance().init(this);
        LogHelper.error("PatronusApplication onCreate ");
        PatronusManager.getInstance(this).intialize();
    }
}
