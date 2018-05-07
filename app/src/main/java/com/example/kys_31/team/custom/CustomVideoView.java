package com.example.kys_31.team.custom;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
 * @author 杨贺
 * @function 自定义视频播放器
 * Created by kys_7 on 2017/2/27
 */

public class CustomVideoView extends VideoView {



    public CustomVideoView(Context context) {
        super(context);

    }
    public CustomVideoView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }
    //视频的大小设置
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //
        int width=getDefaultSize(0,widthMeasureSpec);
        int height=getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }
    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
