package com.example.kys_31.team.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author 张同心
 * @function 文字的横向滚动
 * Created by kys_31 on 2017/3/1.
 */

public class AutoTextView extends TextView{
    /*定义变量名称*/
    private int width,height;
    private Paint paintText=new Paint();
    private float posx,posy;
    private float speed=0.0f;
    private String text="hello word";
    private float textWidth=0;
    private float moveDistance=0.0f;
    private boolean isStarting=false;

    public AutoTextView(Context context) {
        super(context);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(){
        paintText.setTextSize(50.0f);
        paintText.setColor(Color.BLACK);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);
        paintText.setAntiAlias(true);//设置锯齿
        text=getText().toString();
        textWidth=paintText.measureText(text);
        Log.i("AutoTextView","textWidth= "+textWidth);
        this.speed=textWidth;
        moveDistance=textWidth*2+width;

    }

    /**
     * 显示尺寸
     * @param indowManager 屏幕管理
     */
    public void initDisplayMetrics(WindowManager indowManager){
        /*取得屏幕分辨率大小*/
        DisplayMetrics dm=new DisplayMetrics();
        indowManager.getDefaultDisplay().getMetrics(dm);
        this.width=dm.widthPixels;
        this.height=dm.heightPixels;

        initView();
        this.posx=width+textWidth;
        Paint.FontMetrics fm=paintText.getFontMetrics();
        float baseline =fm.descent-fm.ascent;
        this.posy=height/2-baseline;

    }

    public void startScroll(){
        isStarting=true;
        invalidate();
    }

    public void stopScroll(){
        isStarting=false;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawText(text,posx-speed,posy,paintText);
        if(!isStarting){return;}
        speed+=2.0f;
        if(speed>moveDistance){speed=textWidth;}
        invalidate();
    }
}
