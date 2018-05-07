package com.example.kys_31.team.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.kys_31.team.util.MyWindowManagerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 张同心
 * @function 服务悬浮窗
 * Created by kys_31 on 2017/3/6.
 */
public class FloatWindowService extends Service {

    private String ZTX="floatwindow";//log标签

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if (MyWindowManagerUtil.smallWindow==null){  MyWindowManagerUtil.createSmallWindow(getApplicationContext());  }
        else {  MyWindowManagerUtil.smallWindow.setVisibility(View.VISIBLE);   }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
