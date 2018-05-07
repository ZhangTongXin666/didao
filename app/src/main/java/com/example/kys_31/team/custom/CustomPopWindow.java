package com.example.kys_31.team.custom;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.kys_31.team.R;


public class CustomPopWindow extends PopupWindow implements View.OnClickListener
{
private Button firstButton;
    private Button secondButton;
    private Button thirdButton;
    private LayoutInflater layoutInflater;
    private View popView;
    private MyPopWindowListener myPopWindowListener;
    public CustomPopWindow(Context context, MyPopWindowListener myPopWindowListener){
        this.myPopWindowListener=myPopWindowListener;
        this.layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }
    private void init(){
        popView=layoutInflater.inflate(R.layout.register_popupwindow_picture_view,null);
        firstButton=(Button)popView.findViewById(R.id.take_photo);
        secondButton=(Button)popView.findViewById(R.id.album);
        thirdButton=(Button)popView.findViewById(R.id.cancel);
        firstButton.setOnClickListener(this);
        secondButton.setOnClickListener(this);
        thirdButton.setOnClickListener(this);


        this.setContentView(popView);//把View添加到popwindow中
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);//设置selectpicpopwindow弹出窗体的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置selectpicpopwindow弹出窗体的高
        this.setFocusable(true);//设置selectpicpopwindow弹出窗体可点击
        this.setAnimationStyle(R.style.BottomPopWindowAnimation);//设置selectpicpopwindow弹出窗体的动画效果


        ColorDrawable dw=new ColorDrawable();
        this.setBackgroundDrawable(dw);
        popView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int height=popView.findViewById(R.id.pop_window).getTop();
                int y=(int)event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (y<height){
                        dismiss();
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId()){
            case R.id.take_photo:
                myPopWindowListener.firstItem();
                break;
            case R.id.album:
                myPopWindowListener.secondItem();
                break;
            case R.id.cancel:
                myPopWindowListener.thirdItem();
                break;
        }

    }



}
