package com.example.kys_31.team.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author 张同心
 * @function 数据库辅助类
 * Created by kys_31 on 2017/2/28.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private Context context;//操作环境

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ZTX","创建数据库成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
