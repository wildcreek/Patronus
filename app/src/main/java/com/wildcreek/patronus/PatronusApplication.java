package com.wildcreek.patronus;

import android.app.Application;
import android.content.Context;

import com.wildcreek.patronus.utils.LogHelper;

/*
 * File: PatronusApplication.java

 * Author: Administrator

 * Create: 2017/12/13 0013 上午 10:17

 */

public class PatronusApplication extends Application {
    public static Context context;
    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LogHelper.error("PatronusApplication onCreate ");
    }
}
