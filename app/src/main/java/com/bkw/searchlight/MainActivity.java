package com.bkw.searchlight;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private WindowManager windowManager;
    private WindowManager.LayoutParams wmParams, wm;
    private int wmType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void initView() {
        //以下：Window的相关属性设置，可自定义，
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        wmParams = new WindowManager.LayoutParams();
        wmParams.type = wmType;
        wmParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        wmParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.format = PixelFormat.RGBA_8888;

        wm = new WindowManager.LayoutParams();
        wm.type = wmType;
        wm.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        wm.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wm.gravity = Gravity.RIGHT | Gravity.TOP;
        wm.x = 0;
        wm.y = 0;
        wm.width = 100;
        wm.height = 100;
        wm.format = PixelFormat.RGBA_8888;
    }
}
