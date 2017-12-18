package com.marswin89.marsdaemon;
/*
 * File: DeamonManager.java

 * Author: Administrator

 * Create: 2017/12/18 0018 下午 17:41

 */

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DaemonManager {
    private Context mContext;
    private final String DAEMON_PERMITTING_SP_FILENAME 	= "d_permit";
    private final String DAEMON_PERMITTING_SP_KEY 		= "permitted";
    private DaemonConfigurations mConfigurations;
    private BufferedReader mBufferedReader;//release later to save time

    public DaemonManager(Context mContext ) {
        this.mConfigurations = createDaemonConfigurations();
        this.mContext = mContext;
    }

    public void init(){
        if(!isDaemonPermitting(mContext) || mConfigurations == null){
            return ;
        }
        String processName = getProcessName();
        String packageName = mContext.getPackageName();

        if(processName.startsWith(mConfigurations.PERSISTENT_CONFIG.PROCESS_NAME)){
            IDaemonStrategy.Fetcher.fetchStrategy().onPersistentCreate(mContext, mConfigurations);
        }else if(processName.startsWith(mConfigurations.DAEMON_ASSISTANT_CONFIG.PROCESS_NAME)){
            IDaemonStrategy.Fetcher.fetchStrategy().onDaemonAssistantCreate(mContext, mConfigurations);
        }else if(processName.startsWith(packageName)){
            IDaemonStrategy.Fetcher.fetchStrategy().onInitialization(mContext);
        }
        releaseIO();
    }

    private String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            mBufferedReader = new BufferedReader(new FileReader(file));
            return mBufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * release reader IO
     */
    private void releaseIO(){
        if(mBufferedReader != null){
            try {
                mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mBufferedReader = null;
        }
    }

    private boolean isDaemonPermitting(Context context){
        SharedPreferences sp = context.getSharedPreferences(DAEMON_PERMITTING_SP_FILENAME, Context.MODE_PRIVATE);
        return sp.getBoolean(DAEMON_PERMITTING_SP_KEY, true);
    }

    protected boolean setDaemonPermiiting(Context context, boolean isPermitting) {
        SharedPreferences sp = context.getSharedPreferences(DAEMON_PERMITTING_SP_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(DAEMON_PERMITTING_SP_KEY, isPermitting);
        return editor.commit();
    }


    private DaemonConfigurations createDaemonConfigurations(){
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                "com.wildcreek.patronusdemo:process1",
                "com.wildcreek.patronusdemo.service.Service1",
                "com.wildcreek.patronusdemo.receiver.Receiver1");
        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                "com.wildcreek.patronusdemo:process2",
                "com.wildcreek.patronusdemo.service.Service2",
                "com.wildcreek.patronusdemo.receiver.Receiver2");
        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
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
