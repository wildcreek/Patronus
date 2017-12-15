package com.wildcreek.patronus.strategy;

import android.content.Context;
import android.content.Intent;
import com.wildcreek.patronus.service.SilentPlayerService;

public class SilentPlayerManager {
    private Context mContext;
    private SilentPlayerManager(Context mContext){
        this.mContext = mContext;
    }
    private static SilentPlayerManager mSilentPlayerManager;
    // 单例模式
    public static SilentPlayerManager getInstance(Context context){
        if(mSilentPlayerManager == null){
            mSilentPlayerManager = new SilentPlayerManager(context);
        }
        return mSilentPlayerManager;
    }

    public void initialize(){
        Intent intent = new Intent(mContext,SilentPlayerService.class);
        mContext.startService(intent);
    }
    public void unInitialize(){
        Intent intent = new Intent(mContext,SilentPlayerService.class);
        mContext.stopService(intent);
    }
}
