package com.example.kys_31.team.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.kys_31.team.R;


/**
 * 侧滑
 * Created by lenovo on 2017/2/15.
 */

public class SdeingMenu extends HorizontalScrollView {


    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    //屏幕宽度
    private int mScreeWidth;
    //与屏幕右侧边距 dp
    private int mMenuRightPadding = 100;
    //menu的宽和高
    private int mMenuWidth;

    private boolean once;

    //切换按钮
    private boolean isOpen;


    /**
     * 未使用自定义属性时，调用
     *
     * @param context
     * @param attrs
     */
    public SdeingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 当使用了自定义属性时，调用此方法
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SdeingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {   //如果没有设置slidingMenu_rightPadding的值那就用默认的50dp，
                // 如果有就直接获取slidingMenu_rightPadding的值
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 50, context
                                    .getResources().getDisplayMetrics()));
            }
        }

        a.recycle();//释放

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMertrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMertrics);
        mScreeWidth = outMertrics.widthPixels;//宽度像素值
//        //把dp转化为px（单位转换小方法）
//        mMenuRightPadding= (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,50,context
//                        .getResources().getDisplayMetrics());//把50dp转化为像素值

    }

    public SdeingMenu(Context context) {
        this(context, null);
    }

    /**
     * 设置子View的宽和高以及自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);//得到lnearLayout第一个元素
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);//得到linearLayout第二个元素
            //设置子view的宽度和高
            mMenuWidth = mMenu.getLayoutParams().width = mScreeWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreeWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量将Menu隐藏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    /**
     * 触摸屏事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度(判断当前左侧多出来的部分)
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }
    /**
     * 切换菜单
     */
    public void toggle() {
        Log.i("TAG","查看值："+isOpen);
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
