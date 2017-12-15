package com.wildcreek.patronusdemo;

import android.app.Application;
import android.content.Context;

import com.marswin89.marsdaemon.DaemonClient;
import com.marswin89.marsdaemon.DaemonConfigurations;
import com.wildcreek.patronusdemo.receiver.Receiver1;
import com.wildcreek.patronusdemo.receiver.Receiver2;
import com.wildcreek.patronusdemo.service.Service1;
import com.wildcreek.patronusdemo.service.Service2;
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
    }
    private DaemonClient mDaemonClient;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mDaemonClient = new DaemonClient(createDaemonConfigurations());
        mDaemonClient.onAttachBaseContext(base);
    }
    private DaemonConfigurations createDaemonConfigurations(){
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                "com.wildcreek.patronusdemo:process1",
                Service1.class.getCanonicalName(),
                Receiver1.class.getCanonicalName());
        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                "com.wildcreek.patronusdemo:process2",
                Service2.class.getCanonicalName(),
                Receiver2.class.getCanonicalName());
        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        //return new DaemonConfigurations(configuration1, configuration2);//listener can be null
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }


    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDead() {
        }
    }
}
