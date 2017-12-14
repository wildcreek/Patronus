package com.wildcreek.patronus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wildcreek.patronus.utils.Constants;
import com.wildcreek.patronus.utils.LogHelper;
import com.wildcreek.patronus.manager.SinglePixelManager;
import com.wildcreek.patronus.utils.SystemUtils;


/**1像素Activity
 *
 * Created by jianddongguo on 2017/7/8.
 */

public class SinglePixelActivity extends AppCompatActivity {
    private static final String TAG = "SinglePixelActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        SinglePixelManager.getInstance(this).setSingleActivity(this);
    }

    @Override
    protected void onDestroy() {

        LogHelper.error("SinglePixelActivity onDestroy--->1像素保活被终止");
        if(! SystemUtils.isAPPALive(this, Constants.PACKAGE_NAME)){
            Intent intentAlive = new Intent(this, TestActivity.class);
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            LogHelper.error("SinglePixelActivity---->APP被干掉了，我要重启它");
        }
        super.onDestroy();
    }
}
