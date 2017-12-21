package com.wildcreek.patronus.strategy;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class AccountManagerStrategy {
    private Context mContext;
    private AccountManagerStrategy(Context mContext){
        this.mContext = mContext;
    }
    private static AccountManagerStrategy manager;
    // 单例模式
    public static AccountManagerStrategy getInstance(Context context){
        if(manager == null){
            manager = new AccountManagerStrategy(context);
        }
        return manager;
    }

    public void initialize(){
        AccountManager accountManager = (AccountManager) mContext.getSystemService(Context.ACCOUNT_SERVICE);
        Account account = null;
        //Account[] accounts = accountManager.getAccountsByType(AccountManager.)
    }
    public void unInitialize(){
    }
}
