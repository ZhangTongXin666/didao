package com.example.kys_31.team.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.kys_31.team.custom.FloatWindowSmallView;

/**
 * @author  张同心
 * @function 悬浮窗管理
 * Created by kys_31 on 2016/10/25.
 */
public class MyWindowManagerUtil {

    public static FloatWindowSmallView smallWindow=null;//小悬浮窗View比例
    private static WindowManager.LayoutParams smallWindowParams=null;//小悬浮窗View的参数
    private static WindowManager mWindowManager=null;//用于控制在屏幕上添加或移除悬浮窗
    private static int which_mode=0;//用于判断是网络模式，还是单机模式。（这样用户名和头像就可以确定了）

    private static String ZTX="MyWindow";

    /**
     * 将小悬浮窗从屏幕上移除。
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            smallWindow.setVisibility(View.GONE);
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     * @param context   必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(context,which_mode);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = smallWindow.view.getLayoutParams().width  ;
                smallWindowParams.height = smallWindow.view.getLayoutParams().height;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            }
            windowManager.addView(smallWindow, smallWindowParams);
            smallWindow.setParams(smallWindowParams);
        }
        else {
            smallWindow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     * @param context
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
