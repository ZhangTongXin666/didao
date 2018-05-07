package com.example.kys_31.team.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.kys_31.team.R;
import com.example.kys_31.team.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  张同心
 * Created by kys_31 on 2017/3/28.
 */

public class MarqueeView extends ViewFlipper{

    private Context mContext;
    private List<String> notices;
    private boolean isSetAnimDuration=false;//是否设置动画时间
    private OnItemClickListener onItemClickListener;

    private int interval =2000;//两行文字翻页时时间间隔
    private int animDuration=500;//一行动画执行时间
    private int textSize=14;
    private int textColor=0xffffffff;

    private int gravity=Gravity.START | Gravity.CENTER_VERTICAL;//文字位置 左 中 右
    private static final int TEXT_GRAVITY_LEFT=0,TEXT_GRAVITY_CENTER=1,TEXT_GRAVITY_RIGHT=2;

    //标签
    private String logcat="MarqueeView";//选取框视图

    public MarqueeView(Context context) {
        this(context,null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    private void init(Context context, AttributeSet attrs, int i) {
        this.mContext=context;
        if(notices==null){
            notices=new ArrayList<String>();
        }
        TypedArray typedArray=mContext.getTheme().obtainStyledAttributes(attrs,R.styleable.MarqueeViewStyle,0,0);
        interval=typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval,interval);
        isSetAnimDuration=typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration);//是否设置动画时间
        animDuration=typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration,animDuration);
        if(typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)){
            textSize=(int)typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize,textSize);//h获得的是PX值。
            textSize= DisplayUtil.px2sp(mContext,textSize);//尺寸转换
        }

        textColor=typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor,textColor);
        int gravityType=typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity,gravity);
        switch (gravityType){
            case TEXT_GRAVITY_CENTER:
                gravity=Gravity.CENTER;
                break;
            case TEXT_GRAVITY_RIGHT:
                gravity=Gravity.END | Gravity.CENTER_VERTICAL;
                break;
        }
        typedArray.recycle();//缓存属性

        Animation animIn= AnimationUtils.loadAnimation(mContext,R.anim.anim_marquee_in);
        if(isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);//设置进入动画

        Animation animOut=AnimationUtils.loadAnimation(mContext,R.anim.anim_marquee_out);
        if(isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);//设置离场动画

    }

    /*根据公告字符串启动轮播*/
    public boolean startWithText(final String notice){
        if(TextUtils.isEmpty(notice)) return false;
        //ViewTreeObserver类构造方法被私有化，所有不可实例化
        ViewTreeObserver vto=getViewTreeObserver();
        if(vto.isAlive()){
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);//清楚当前视图的状态监听
                    startWithFixedWidth(notice,getWidth());//getWidth()获取视图的宽度，（像素px）
                }
            });
        }
        return true;
    }

    /*根据个公告字符串列表启动轮播*/
    public void startWithList(List<String> notices){
//        for(String s:notices){
//            startWithText(s);
//        }
        this.notices=notices;
        start();
    }

    /*根据宽度和公告字符串启动轮播*/
    private void startWithFixedWidth(String notice,int width){
        Log.i(logcat,"查看视图宽度"+width);
        int noticeLength=notice.length();
        int dpW=DisplayUtil.px2dip(mContext,width);
        int limit=dpW/textSize;//一行能放多少个文字
        if(dpW==0) throw new RuntimeException("please set MarqueeView width !");
        if(noticeLength<=limit) notices.add(notice);
        else {
            int size=noticeLength/limit+(noticeLength % limit !=0 ?1 :0);
            for(int i=0;i<size;i++){
                int startIndex=i*limit;
                int endIndex=((i+1)*limit>=noticeLength ? noticeLength:(i+1)*limit);
                notices.add(notice.substring(startIndex,endIndex));
            }
        }
        start();
    }

    /*启动轮播*/
    private boolean start(){
        if(notices==null || notices.size()==0  ) return false;
        removeAllViews();//清除视图上所有视图

        for(int i=0;i<notices.size();i++){
            final TextView textView=createTextView(notices.get(i),i);
            final int finalI=i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(logcat,"textView点击事件");
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(finalI,textView);
                    }
                }
            });
            addView(textView);
        }
        if(notices.size()>1){
            setFlipInterval(interval);//设置切换时间间隔
            startFlipping();//开始切换
        }
        return true;
    }

    /*创建ViewFlipper下的TextView*/
    private TextView createTextView(String text,int position){
        TextView tv=new TextView(mContext);
        tv.setGravity(gravity);
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        tv.setTag(position);
        return tv;
    }

    public int getPosition(){
        return (int)getCurrentView().getTag();
    }
    public List<String> getNotices(){
        return notices;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position, TextView textView);
    }
}
