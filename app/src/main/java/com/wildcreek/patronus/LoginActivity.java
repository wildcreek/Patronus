package com.jiangdg.keepappalive;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * 登录界面
 * <p>
 * Created by jianddongguo on 2017/7/7.
 * http://blog.csdn.net/andrexpert
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mEdtUsrName;
    private EditText mEdtUsrPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdtUsrName = (EditText) findViewById(R.id.etv_usrname);
        mEdtUsrPasswd = (EditText) findViewById(R.id.etv_usrpswd);
        verifyStoragePermissions(this);
    }

    public void onLoginClick(View v) {
//        if("jiang".equals(mEdtUsrName.getText().toString())
//                && "123".equals(mEdtUsrPasswd.getText().toString())){
//            Intent intent = new Intent(LoginActivity.this,SportsActivity.class);
//            startActivity(intent);
//        }
        Intent intent = new Intent(LoginActivity.this, SportsActivity.class);
        startActivity(intent);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
// Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
