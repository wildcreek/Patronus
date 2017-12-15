package com.wildcreek.patronus.strategy;

import android.content.Context;
import android.content.Intent;
import com.wildcreek.patronus.service.SilentPlayerService;

public class SilentPlayerStrategy {
    private Context mContext;
    private SilentPlayerStrategy(Context mContext){
        this.mContext = mContext;
    }
    private static SilentPlayerStrategy mSilentPlayerManager;
    // 单例模式
    public static SilentPlayerStrategy getInstance(Context context){
        if(mSilentPlayerManager == null){
            mSilentPlayerManager = new SilentPlayerStrategy(context);
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
