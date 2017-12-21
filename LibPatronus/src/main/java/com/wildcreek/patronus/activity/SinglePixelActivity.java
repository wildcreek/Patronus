package com.wildcreek.patronus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wildcreek.patronus.strategy.SinglePixelStrategy;
import com.wildcreek.patronus.utils.Constants;
import com.wildcreek.patronus.utils.LogHelper;
import com.wildcreek.patronus.utils.SystemUtils;


/**
 * 1像素Activity
 */

public class SinglePixelActivity extends Activity {
    private static final String TAG = "SinglePixelActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.error("onCreate--->启动1像素保活");
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 300;
        attrParams.width = 300;
        mWindow.setAttributes(attrParams);
        // 绑定SinglePixelActivity到ScreenManager
        SinglePixelStrategy.getInstance(this).setSingleActivity(this);
    }

    @Override
    protected void onDestroy() {

        LogHelper.error("SinglePixelActivity onDestroy--->1像素保活被终止");
        if(! SystemUtils.isAPPALive(this, Constants.PACKAGE_NAME)){
            Intent intentAlive = new Intent();
            intentAlive.setComponent(getCallingActivity());
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            LogHelper.error("SinglePixelActivity---->APP被干掉了，我要重启它");
        }
        super.onDestroy();
    }
}
