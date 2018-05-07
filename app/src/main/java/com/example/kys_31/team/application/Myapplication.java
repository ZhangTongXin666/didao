package com.example.kys_31.team.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kys_31.team.database.Sqldatabase;
import com.example.kys_31.team.variable.Variable;

import c.b.BP;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

import static com.example.kys_31.team.variable.Variable.database;
import static com.example.kys_31.team.variable.Variable.methedUtil;
import static com.example.kys_31.team.variable.Variable.sendtype_List;
import static com.example.kys_31.team.variable.Variable.user_Important_Message_List;

/**
 * @aothor 张同心
 * @function 启动加载
 * Created by kys_31 on 2017/2/28.
 */

public class Myapplication extends Application {

    /*定义变量名称*/
    private SQLiteDatabase sql=null;

    /*标签*/
    private String TAG="myapplication";

    @Override
    public void onCreate(){
        //获取bmob密匙
        Bmob.initialize(this,"451bbbacb6142c5eebf56a66a3a34daf");
        BP.init("451bbbacb6142c5eebf56a66a3a34daf");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);
        initDatabase();//初始化数据库
        getData();//获取数据
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        database=new Sqldatabase(getApplicationContext());
        sql= database.createdatabase();
        Cursor cursor = sql.rawQuery("select name from sqlite_master where type='table';", null);
        while(cursor.moveToNext()) {
            //遍历出表名
            String name = cursor.getString(0);
            if (name.equals("user_Important_Message"))
                return;
        }
        database.createdatatable();
    }

    /**
     * 获取本地数据
     */
    private void getData() {
        Variable.if_Show_Floatwindow = false;
        user_Important_Message_List = database.query_Data();
        sendtype_List.add("顺丰快递");
        sendtype_List.add("圆通快递");
        sendtype_List.add("申通快递");
        sendtype_List.add("中通快递");
        sendtype_List.add("韵达快递");
        sendtype_List.add("天天快递");
        sendtype_List.add("百世汇通");
        sendtype_List.add("国通快递");
        sendtype_List.add("京东快递");
        sendtype_List.add("EMS");
        sendtype_List.add("邮政包裹");
        sendtype_List.add("聚美优品快递");
        sendtype_List.add("全峰快递");
        sendtype_List.add("如风达快递");
        sendtype_List.add("世运快递");
        sendtype_List.add("宅急送");
        get_School_Data();
    }

    private void get_School_Data() {
        if(methedUtil.check_Net_Available(getApplicationContext())){
                methedUtil.get_School_Data();
        }
    }
}
