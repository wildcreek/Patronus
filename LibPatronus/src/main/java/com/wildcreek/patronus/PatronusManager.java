package com.wildcreek.patronus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.marswin89.marsdaemon.DaemonConfigurations;
import com.marswin89.marsdaemon.DaemonManager;
import com.wildcreek.patronus.strategy.InvisibleNotificationStrategy;
import com.wildcreek.patronus.strategy.JobSchedulerStrategy;
import com.wildcreek.patronus.strategy.SinglePixelStrategy;

/**
 - @Description:
 - @Author:  wildcreek
 - @Time:  2017/12/13 0013 上午 10:20
 */
public class PatronusManager {
    private static PatronusManager mPatronManager;
    private Context mContext;
    private JobSchedulerStrategy mJobManager;
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
        SinglePixelStrategy.getInstance(mContext).initialize();
        // 3. 启动JobScheduler
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mJobManager = JobSchedulerStrategy.getJobSchedulerInstance(mContext);
            mJobManager.startJobScheduler();
        }

        // 4. 前台隐形通知绑定Service
        InvisibleNotificationStrategy.getInstance(mContext).initialize();
        // 5. 无声音乐播放
        //SilentPlayerManager.getInstance(this).initialize();
        // 6. MarsDaemon 多进程保活方案
        DaemonManager daemonManager = new DaemonManager(mContext ,createDaemonConfigurations());
        daemonManager.init();
        Intent intent =  new Intent();
        intent.setComponent(new ComponentName("com.wildcreek.patronusdemo","com.wildcreek.patronusdemo.service.Service1"));
        mContext.startService(intent);

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
        DaemonConfigurations.DaemonListener listener = new PatronusManager.MyDaemonListener();
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
    public void unIntialize(){
        // 1 静态注册监听系统广播
        // 2. 注册锁屏广播监听器
        SinglePixelStrategy.getInstance(mContext).unInitialize();
        // 3. 启动JobScheduler
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mJobManager = JobSchedulerStrategy.getJobSchedulerInstance(mContext);
            mJobManager.stopJobScheduler();
        }
        // 4. 前台隐形通知绑定Service
        InvisibleNotificationStrategy.getInstance(mContext).unInitialize();
        // 5. 无声音乐播放
        //SilentPlayerManager.getInstance(this).initialize();
    }

}
