package com.example.kys_31.team.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.kys_31.team.R;
import com.example.kys_31.team.activity.ServiceActivity;
import com.example.kys_31.team.util.MyWindowManagerUtil;

import java.lang.reflect.Field;

import static com.example.kys_31.team.variable.Variable.methedUtil;
import static com.example.kys_31.team.variable.Variable.str_Head;

/**
 * @author  张同心
 * @function 悬浮窗的小视图
 * Created by kys_31 on 2016/10/25.
 */
public class FloatWindowSmallView extends LinearLayout {

    //声明控件
    private RoundImageView head=null;

    public static int statusBarHeight;//记录系统状态栏的高度
    private WindowManager windowManager;//用于更新小悬浮窗位置
    private WindowManager.LayoutParams mParams;//小悬浮窗的参数
    private float xInScreen;//记录当前手指位置在屏幕上的横坐标值
    private float yInScreen;//记录当前手指位置在屏幕上的纵坐标值
    private float xDownInScreen;//记录手指按下时在屏幕上的横坐标值
    private float yDownInScreen;//记录手指按下时在屏幕上的纵坐标值
    private float xView;
    private float yView;
    private Context context;
    public int witch_mode=0;
    public View view;

    /*标签*/
    private String ZTX="FloatWindowSmallView";

    public FloatWindowSmallView(final Context context,int which) {
        super(context);
        this.context=context;
        witch_mode=which;
        windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.small_float_window_view, this);
        view=findViewById(R.id.small_window_layout);
        head=(RoundImageView)findViewById(R.id.iv_head);
        head.setImageBitmap(methedUtil.covertStringToIcon(str_Head));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDownInScreen=event.getRawX();
                yDownInScreen=event.getRawY()-getStatusBarHeight();//减去状态栏的高度
                xView = event.getX();
                yView = event.getY();
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-getStatusBarHeight();
                updateViewPosition();//更新小悬浮窗的位置
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(xDownInScreen - xInScreen)<10 && Math.abs(yDownInScreen - yInScreen)<10) {
                    MyWindowManagerUtil.smallWindow.setVisibility(GONE);
                    Intent intent=new Intent(context,ServiceActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 更新小悬浮窗的位置
     */
    private void updateViewPosition(){
        mParams.x=(int)(xInScreen - xView);
        mParams.y=(int)(yInScreen - yView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params){
        mParams=params;
    }

    /**
     * 用于获取状态的高度
     * @return
     */
    private int getStatusBarHeight(){
        if(statusBarHeight==0){
            try{
                Class<?> c=Class.forName("com.android.internal.R$dimen");
                Object o=c.newInstance();
                Field field=c.getField("status_bar_height");
                int x =(Integer)field.get(o);
                statusBarHeight=getResources().getDimensionPixelSize(x);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗
     */
    private void openBigWindow(){
        MyWindowManagerUtil.removeSmallWindow(getContext());
    }
    public void setHead(Bitmap head){
        Log.i(ZTX,"设置头像");
        this.head.setImageBitmap(head);
    }
}
