package com.example.kys_31.team.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.example.kys_31.team.R;
import com.example.kys_31.team.activity.ServiceActivity;
import com.example.kys_31.team.activity.SystemMessageActivity;
import com.example.kys_31.team.variable.Variable;

/**
 * @author 张同心
 * Created by kys_31 on 2017/3/31.
 */

public class NotificationService extends Service {

    private Notification.Builder builder=null;
    private int messageNotificationID=1000;
    private Notification notification=null;
    private NotificationManager notificationManager=null;

    //标签
    private String TAG="NotificationSerevice";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startID){
        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        builder=new Notification.Builder(getApplicationContext());
        startNotificationService();
        return super.onStartCommand(intent,flags,startID);
    }

    private void startNotificationService() {
        builder.setTicker("递到重大消息");
        builder.setContentTitle("递到消息");
        builder.setContentText(Variable.notificationMessage);
        builder.setSmallIcon(R.mipmap.logo_logos);
        builder.setSound(Uri.parse("android.resource://com.example.kys_31.team/" + R.raw.notification));
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent=new Intent(this, SystemMessageActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification=builder.build();
        notificationManager.notify(messageNotificationID,notification);
        messageNotificationID++;
    }

}
