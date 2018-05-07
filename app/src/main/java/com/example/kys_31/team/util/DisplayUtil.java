package com.example.kys_31.team.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * @author 张同心
 * Created by kys_31 on 2017/3/28.
 */

public class DisplayUtil {

    /*将px值转换为dip或dp，保证尺寸大小不变*/
    public static int px2dip(Context context, float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        Log.i("logcat","查看Scale:"+scale);
        Log.i("logcat","查看未转换到整形前："+(pxValue/scale+0.5f));
        return (int)(pxValue/scale+0.5f);
    }
    /*将dip或dp转换为px值，保证尺寸的不变*/
    public static int dip2px(Context context,float dipValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int) (dipValue*scale+0.5);
    }
    /*将px值转化为sp值，保证文字大小不变*/
    public static int px2sp(Context context,float pxValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        Log.i("logcat","查看fontScale:"+fontScale);
        Log.i("logcat","查看未转换到整形前："+(pxValue/fontScale+0.5f));
        return (int) (pxValue/fontScale+0.5f);
    }
    /*将sp值转换为px值，保证文字大小不变*/
    public static int sp2px(Context context,float spValue){
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }

    /*屏幕宽度 （像素）*/
    public static int getWindwoWidth(Activity context){
        DisplayMetrics metrics=new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /*屏幕高度（像素）*/
    public static int getWindowHeight(Activity context){
        DisplayMetrics metrics=new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
