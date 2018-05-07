package com.example.kys_31.team.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kys_31.team.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 张同心
 * @function SQL数据库
 * Created by kys_31 on 2017/2/28
 */

public class Sqldatabase {

    /*定义变量名称*/
    private SQLiteDatabase sql=null;
    private DatabaseHelper mDatabaseHelper=null;
    private Context context=null;

    /*标签*/
    private String TAG="sqldatabase";

    /*构造方法*/
    public Sqldatabase(Context context){
        mDatabaseHelper=new DatabaseHelper(context,"team.db",null,1);
        this.context=context;
    }

    /*创建数据库*/
    public SQLiteDatabase createdatabase(){
        sql=mDatabaseHelper.getWritableDatabase();
        return sql;
    }

    /*创建数据表*/
    public void createdatatable(){
        if(sql!=null){
            String userMessagetable="create table user_Important_Message("+"id INTEGER PRIMARY KEY autoincrement"+",account text"+",password text"+",head text"+",name text"+",phonenumber text"+",address text"+",school text"+",sex text)";
            String systemMessageTable="create table system_Message("+"id INTEGER PRIMARY KEY"+",content text"+",time text)";
            sql.execSQL(userMessagetable);
            sql.execSQL(systemMessageTable);
        }
        else {
           Variable.methedUtil.showToast(context,"创建数据库失败");
        }

    }

    /*添加数据*/
    public void  add_Data(HashMap<String,String> map){
        sql=mDatabaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("head",map.get("head"));
        values.put("account",map.get("account"));
        values.put("password",map.get("password"));
        values.put("name",map.get("name"));
        values.put("phonenumber",map.get("phonenumber"));
        values.put("address",map.get("address"));
        values.put("school",map.get("school"));
        values.put("sex",map.get("sex"));
        sql.insert("user_Important_Message",null,values);
        sql.close();
    }
    public void add_DataTo_SystemMessage(HashMap<String,String> map){
        sql=mDatabaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("content",map.get("content"));
        values.put("time",map.get("time"));
        sql.insert("system_Message",null,values);
        sql.close();
    }

    /*删除数据,根据账号删除*/
    public void delete_Data(String account){
        sql=mDatabaseHelper.getWritableDatabase();
        sql.delete("user_Important_Message","account like ?",new String[]{account});
        sql.close();
    }
    public void delete_DataTo_SystemMessage(String time){
        sql=mDatabaseHelper.getWritableDatabase();
        sql.delete("system_Message","time like ?",new String[]{time});
        sql.close();
    }

    /*更改数据*/
    public void updata_Data(String account,HashMap<String,String> map){
        sql=mDatabaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",map.get("name"));
        values.put("phonenumber",map.get("phonenumber"));
        values.put("address",map.get("address"));
        values.put("password",map.get("password"));
        values.put("head",map.get("head"));
        sql.update("user_Important_Message",values,"account like ?",new String[]{account});
        sql.close();
    }

    /*查询数据,存入List中*/
    public  List<HashMap<String,String>> query_Data(){
        sql=mDatabaseHelper.getWritableDatabase();
        List<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
        Cursor cursor=sql.query("user_Important_Message",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
           for(int i=0;i<cursor.getCount();i++){
               cursor.moveToPosition(i);
               HashMap<String,String> map=new HashMap<String,String>();
               map.put("account",cursor.getString(1));
               map.put("password",cursor.getString(2));
               map.put("head",cursor.getString(3));
               map.put("name",cursor.getString(4));
               map.put("phonenumber",cursor.getString(5));
               map.put("address",cursor.getString(6));
               map.put("school",cursor.getString(7));
               map.put("sex",cursor.getString(8));
               list.add(0,map);
           }
        }
        cursor.close();
        sql.close();
        return list;
    }

    public List<HashMap<String,String>> query_DataTo_SystemMessage(){
        sql=mDatabaseHelper.getWritableDatabase();
        List<HashMap<String,String>> list=new ArrayList<>();
        Cursor cursor=sql.query("system_Message",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            for (int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
               HashMap<String,String> map=new HashMap<>();
                map.put("content",cursor.getString(1));
                map.put("time",cursor.getString(2));
                list.add(0,map);
            }
        }
        sql.close();
        cursor.close();
        return list;
    }
}
