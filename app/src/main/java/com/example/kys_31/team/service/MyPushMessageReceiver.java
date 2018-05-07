package com.example.kys_31.team.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.kys_31.team.variable.Variable;

import java.util.HashMap;

import cn.bmob.push.PushConstants;

/**
 * @author 张同心
 * Created by kys_31 on 2017/3/31.
 */

public class MyPushMessageReceiver extends BroadcastReceiver{
    //标签
    private String TAG="MyPushMessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String strMessage=intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Log.i("MyPush",strMessage);
            Variable.notificationMessage=strMessage.substring(10,strMessage.length()-2);
            if(!TextUtils.isEmpty(Variable.notificationMessage)){
                Intent notificationIntent=new Intent();
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntent.setClass(context,NotificationService.class);
                notificationIntent.setPackage(context.getPackageName());
                context.startService(notificationIntent);
                HashMap<String,String> map=new HashMap<>();
                map.put("content",Variable.notificationMessage);
                map.put("time",Variable.methedUtil.getSystemTime());
                Variable.database.add_DataTo_SystemMessage(map);
            }
        }
    }
}
