package com.example.kys_31.team.util;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Toast;

import com.example.kys_31.team.bean.School;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.kys_31.team.variable.Variable.all_School;

/**
 * @author 张同心
 * @function 常用方法
 * Created by kys_31 on 2017/2/28.
 */

public class MethedUtil {

    /**
     * 将Bitmap转换为字符串
     * @param bitmap 头像
     * @return 字符串
     */
    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream toByteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, toByteArray);
        byte[] bitmap_Byte = toByteArray.toByteArray();
        return Base64.encodeToString(bitmap_Byte, Base64.DEFAULT);
    }

    /**
     * 将字符串转换为头像
     * @param headURl 字符串
     * @return 头像
     */
    public Bitmap   covertStringToIcon(String headURl) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(headURl, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     *显示Toast
     */
    public void showToast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    /**
     * 将头像存到SDcard中
     */
    public void put_Head_to_SDcard(Bitmap bitmap){
        File file_Head=new File(Environment.getExternalStorageDirectory(),"team_Head.jpg");
        try {
            if (file_Head.exists()){
                file_Head.delete();
            }
            file_Head.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(file_Head);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);//压缩照片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean check_Net_Available(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(info==null){return false;}
        else {
            if(info.isAvailable()){return true;}
            else{return false;}
        }
    }

    /*获取学校的数据*/
    public void get_School_Data(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<School> query_School=new BmobQuery<School>();
                query_School.findObjects(new FindListener<School>() {
                    @Override
                    public void done(List<School> list, BmobException e) {
                        all_School=list;
                    }
                });
            }
        }).start();
    }

    /*获得系统时间*/
    public String getSystemTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String systemTime=format.format(date);
        return systemTime;
    }

    /*隐藏标题栏*/
    public void hideTitle(AppCompatActivity activity){
        android.support.v7.app.ActionBar bar=activity.getSupportActionBar();
        bar.hide();
    }
}
