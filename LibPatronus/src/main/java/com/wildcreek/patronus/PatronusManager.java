package com.wildcreek.patronus;

import android.content.Context;
import android.os.Build;

import com.wildcreek.patronus.strategy.InvisibleNotificationManager;
import com.wildcreek.patronus.strategy.JobSchedulerManager;
import com.wildcreek.patronus.strategy.SinglePixelManager;

/**
 - @Description:
 - @Author:  wildcreek
 - @Time:  2017/12/13 0013 上午 10:20
 */
public class PatronusManager {
    private static PatronusManager mPatronManager;
    private Context mContext;
    private JobSchedulerManager mJobManager;
    private PatronusManager(Context mContext){
        this.mContext = mContext;
    }

    // 单例模式
    public static PatronusManager getInstance(Context context){
        if(mPatronManager == null){
            mPatronManager = new PatronusManager(context);
        }
        return mPatronManager;
    }
    public void intialize(){
        // 1 静态注册监听系统广播
        // 2. 注册锁屏广播监听器
        SinglePixelManager.getInstance(mContext).initialize();
        // 3. 启动JobScheduler
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mJobManager = JobSchedulerManager.getJobSchedulerInstance(mContext);
            mJobManager.startJobScheduler();
        }

        // 4. 前台隐形通知绑定Service
        InvisibleNotificationManager.getInstance(mContext).initialize();
        // 5. 无声音乐播放
        //SilentPlayerManager.getInstance(this).initialize();
    }
    public void unIntialize(){
        // 1 静态注册监听系统广播
        // 2. 注册锁屏广播监听器
        SinglePixelManager.getInstance(mContext).unInitialize();
        // 3. 启动JobScheduler
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mJobManager = JobSchedulerManager.getJobSchedulerInstance(mContext);
            mJobManager.stopJobScheduler();
        }
        // 4. 前台隐形通知绑定Service
        InvisibleNotificationManager.getInstance(mContext).unInitialize();
        // 5. 无声音乐播放
        //SilentPlayerManager.getInstance(this).initialize();
    }

}