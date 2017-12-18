package com.wildcreek.patronusdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.marswin89.marsdaemon.DaemonManager;
import com.wildcreek.patronus.utils.LogHelper;
import com.wildcreek.patronusdemo.service.Service1;


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
        // 6. MarsDaemon 多进程保活方案
        DaemonManager daemonManager= new DaemonManager(this);
        daemonManager.init();
        startService(new Intent(this, Service1.class));
    }
}
