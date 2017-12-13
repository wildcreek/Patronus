package com.wildcreek.patronus.manager;

import android.content.Context;
import android.content.Intent;

import com.wildcreek.patronus.TestActivity;
import com.wildcreek.patronus.receiver.SystemBroadcastReceiver;
import com.wildcreek.patronus.utils.Contants;
import com.wildcreek.patronus.utils.LogHelper;
import com.wildcreek.patronus.utils.SystemUtils;


public class SystemBroadcastManager {
    private Context mContext;
    private static SystemBroadcastManager mSystemBroadcastManager;
    private SystemBroadcastReceiver mSystemBroadcastReceiver;
    private SystemBroadcastManager(Context mContext){
        this.mContext = mContext;
    }

    // 单例模式
    public static SystemBroadcastManager getInstance(Context context){
        if(mSystemBroadcastManager == null){
            mSystemBroadcastManager = new SystemBroadcastManager(context);
        }
        return mSystemBroadcastManager;
    }

    public void initialize(){
        mSystemBroadcastReceiver = new SystemBroadcastReceiver(new SystemBroadcastReceiver.SystemBroadcastListener() {
            @Override
            public void onBroadcastReceive() {
                if (SystemUtils.isAPPALive(mContext, Contants.PACKAGE_NAME)) {
                    LogHelper.error("KeepAliveReceiver---->APP还是活着的");
                    return;
                } else {
                    Intent intentAlive = new Intent(mContext, TestActivity.class);
                    intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intentAlive);
                    LogHelper.error("KeepAliveReceiver---->复活进程(APP)");
                }
            }
        });
    }

    public void unInitialize(){
        mContext.unregisterReceiver(mSystemBroadcastReceiver);
    }

}
