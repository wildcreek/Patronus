package com.wildcreek.patronus.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.wildcreek.patronus.SinglePixelActivity;
import com.wildcreek.patronus.receiver.ScreenBroadcastReceiver;
import com.wildcreek.patronus.utils.LogHelper;

import java.lang.ref.WeakReference;


public class SinglePixelManager {
    private Context mContext;
    private static SinglePixelManager mSinglePixelManager;
    // 使用弱引用，防止内存泄漏
    private WeakReference<Activity> mActivityRef;
    private ScreenBroadcastReceiver mScreenBroadcastReceiver;
    private SinglePixelManager(Context mContext){
        this.mContext = mContext;
    }

    // 单例模式
    public static SinglePixelManager getInstance(Context context){
        if(mSinglePixelManager == null){
            mSinglePixelManager = new SinglePixelManager(context);
        }
        return mSinglePixelManager;
    }

    public void initialize(){
        mScreenBroadcastReceiver = new ScreenBroadcastReceiver(new ScreenBroadcastReceiver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                finishSinglePixelActivity();
            }

            @Override
            public void onScreenOff() {
                startSinglePixelActivity();
            }

            @Override
            public void onUserPresent() {

            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenBroadcastReceiver,filter);
    }

    public void unInitialize(){
        mContext.unregisterReceiver(mScreenBroadcastReceiver);
    }
    // 获得SinglePixelActivity的引用
    public void setSingleActivity(Activity mActivity){
        mActivityRef = new WeakReference<>(mActivity);
    }

    // 启动SinglePixelActivity
    public void startSinglePixelActivity(){
        LogHelper.error("准备启动SinglePixelActivity...");
        Intent intent = new Intent(mContext,SinglePixelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    // 结束SinglePixelActivity
    public void finishSinglePixelActivity(){
        LogHelper.error("准备结束SinglePixelActivity...");
        if(mActivityRef != null){
            Activity mActivity = mActivityRef.get();
            if(mActivity != null){
                mActivity.finish();
            }
        }
    }
}
