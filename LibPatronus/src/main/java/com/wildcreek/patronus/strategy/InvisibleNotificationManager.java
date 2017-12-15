package com.wildcreek.patronus.strategy;
/*
 * File: InvisibleNotificationManager.java

 * Author: Administrator

 * Create: 2017/12/13 0013 下午 16:56

 */

import android.content.Context;
import android.content.Intent;

import com.wildcreek.patronus.service.ForegroundService;

public class InvisibleNotificationManager {
    private Context mContext;
    private InvisibleNotificationManager(Context mContext){
        this.mContext = mContext;
    }
    private static InvisibleNotificationManager mInvisibleNotificationManager;
    // 单例模式
    public static InvisibleNotificationManager getInstance(Context context){
        if(mInvisibleNotificationManager == null){
            mInvisibleNotificationManager = new InvisibleNotificationManager(context);
        }
        return mInvisibleNotificationManager;
    }

    public void initialize(){
        Intent intent = new Intent(mContext, ForegroundService.class);
        mContext.startService(intent);
    }
    public void unInitialize(){
        Intent intent = new Intent(mContext, ForegroundService.class);
        mContext.stopService(intent);
    }
}
