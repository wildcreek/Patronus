package com.wildcreek.patronus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wildcreek.patronus.manager.InvisibleNotificationManager;
import com.wildcreek.patronus.manager.JobSchedulerManager;
import com.wildcreek.patronus.manager.SilentPlayerManager;
import com.wildcreek.patronus.manager.SinglePixelManager;
import com.wildcreek.patronus.manager.SystemBroadcastManager;
import com.wildcreek.patronus.push.HwPushManager;
import com.wildcreek.patronus.utils.LogHelper;

import java.util.Timer;
import java.util.TimerTask;

/** 运动界面，处理各种保活逻辑
 *
 * Created by jianddongguo on 2017/7/7.
 * http://blog.csdn.net/andrexpert
 */

public class TestActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private TextView mTvRunTime;
    private Button mBtnRun;

    private int timeSec;
    private int timeMin;
    private int timeHour;
    private Timer mRunTimer;
    private boolean isRunning;
    // 1像素Activity管理类
    private SinglePixelManager mScreenManager;
    // JobService，执行系统任务
    private JobSchedulerManager mJobManager;
    // 华为推送管理类
    private HwPushManager mHwPushManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        LogHelper.error("SportsActivity--->onCreate");
        // 1. 注册锁屏广播监听器
        SinglePixelManager.getInstance(this).initialize();
        // 2. 启动JobScheduler
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mJobManager = JobSchedulerManager.getJobSchedulerInstance(this);
            mJobManager.startJobScheduler();
//        }
        // 3. 监听系统广播
        SystemBroadcastManager.getInstance(this).initialize();
        // 4. 前台隐形通知绑定Service
        InvisibleNotificationManager.getInstance(this).initialize();
        // 5. 无声音乐播放
        SilentPlayerManager.getInstance(this).initialize();
        // 6. 华为推送保活，允许接收透传
//        mHwPushManager = HwPushManager.getInstance(this);
//        mHwPushManager.startRequestToken();
//        mHwPushManager.isEnableReceiveNormalMsg(true);
//        mHwPushManager.isEnableReceiverNotifyMsg(true);

        mToolBar = (Toolbar)findViewById(R.id.toolbar_sports);
        mToolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        mToolBar.setTitle("跑步啦");
        mTvRunTime = (TextView)findViewById(R.id.tv_run_time);
        mBtnRun = (Button)findViewById(R.id.btn_run);
    }

    public void onRunningClick(View v){
        if(! isRunning){
            mBtnRun.setText("停止跑步");
            startRunTimer();
            // 3. 启动前台Service
            // 4. 启动播放音乐Service
            //startPlayMusicService();
        }else{
            mBtnRun.setText("开始跑步");
            stopRunTimer();
            //关闭前台Service
            //关闭启动播放音乐Service
            //stopPlayMusicService();
        }
        isRunning = !isRunning;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 禁用返回键
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isRunning){
                Toast.makeText(TestActivity.this,"正在跑步",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startRunTimer() {
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                timeSec++;
                if(timeSec == 60){
                    timeSec = 0;
                    timeMin++;
                }
                if(timeMin == 60){
                    timeMin = 0;
                    timeHour++;
                }
                if(timeHour == 24){
                    timeSec = 0;
                    timeMin = 0;
                    timeHour = 0;
                }
                // 更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvRunTime.setText(timeHour+" : "+timeMin+" : "+timeSec);
                    }
                });
            }
        };
        mRunTimer = new Timer();
        // 每隔1s更新一下时间
        mRunTimer.schedule(mTask,1000,1000);
    }

    private void stopRunTimer(){
        if(mRunTimer != null){
            mRunTimer.cancel();
            mRunTimer = null;
        }
        timeSec = 0;
        timeMin = 0;
        timeHour = 0;
        mTvRunTime.setText(timeHour+" : "+timeMin+" : "+timeSec);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LogHelper.error("SportsActivity -->onDestroy");
        stopRunTimer();
//        mScreenListener.stopScreenReceiverListener();
    }
}
