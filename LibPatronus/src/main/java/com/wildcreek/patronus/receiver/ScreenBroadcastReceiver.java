package com.wildcreek.patronus.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wildcreek.patronus.utils.LogHelper;

/*
 * File: ScreenBroadCastReceiver.java

 * Author: wildcreek

 * Create: 2017/12/13 0013 下午 14:19

 */

public class ScreenBroadcastReceiver extends BroadcastReceiver {

    public interface ScreenStateListener {
        void onScreenOn();
        void onScreenOff();
        void onUserPresent();
    }

    private ScreenStateListener mStateReceiverListener;
    public void setScreenStateListener(ScreenStateListener mStateReceiverListener){
        this.mStateReceiverListener = mStateReceiverListener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogHelper.error("SreenLockReceiver-->监听到系统广播："+action);
        if(mStateReceiverListener == null){
            return;
        }
        if(Intent.ACTION_SCREEN_ON.equals(action)){         // 开屏
            mStateReceiverListener.onScreenOn();
        }else if(Intent.ACTION_SCREEN_OFF.equals(action)){  // 锁屏
            mStateReceiverListener.onScreenOff();
        }else if(Intent.ACTION_USER_PRESENT.equals(action)){ // 解锁
            mStateReceiverListener.onUserPresent();
        }
    }

}
