package com.example.kys_31.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys_31.team.R;
import com.example.kys_31.team.custom.CustomViewPager;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.util.MyWindowManagerUtil;

import static com.example.kys_31.team.variable.Variable.head;
import static com.example.kys_31.team.variable.Variable.methedUtil;
import static com.example.kys_31.team.variable.Variable.str_Account;
import static com.example.kys_31.team.variable.Variable.str_Head;
import static com.example.kys_31.team.variable.Variable.str_Name;
import static com.example.kys_31.team.variable.Variable.str_Password;
import static com.example.kys_31.team.variable.Variable.str_Phonenumber;
import static com.example.kys_31.team.variable.Variable.str_School;
import static com.example.kys_31.team.variable.Variable.str_Sex;
import static com.example.kys_31.team.variable.Variable.str_address;

/**
 * @author 杨贺
 * @function 欢迎界面
 */
public class WelcomeActivity extends Activity implements View.OnClickListener,Init{

    /*定义控件变量名称*/
    private TextView start_tv;
    private CustomViewPager bgPicture;

    /*定义变量名称*/
    private long firstTime=0;
    private final int[]  MIMAGE=new int[]{R.mipmap.bg_twos,R.mipmap.bg_ones,R.mipmap.bg_threes};


    /*标签*/
    private String TAG="welcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_welcome);

        initControl();//初始化控件
        addControlListener();//增加控件监听
        autoLogin();//自动登陆
    }

    /*初始化图片*/
    private void autoLogin() {

       SharedPreferences sp=getSharedPreferences("auto_Login",0);
        str_Account=sp.getString("account","");
        if(TextUtils.isEmpty(str_Account)){
          initPicture();
        }
       else {
            head=methedUtil.covertStringToIcon(sp.getString("head"," "));
            str_address=sp.getString("address"," ");
            str_Name=sp.getString("name"," ");
            str_Sex=sp.getString("sex"," ");
            str_Head=sp.getString("head"," ");
            str_Phonenumber=sp.getString("phonenumber"," ");
            str_School=sp.getString("school"," ");
            str_Password=sp.getString("password"," ");
            if(MyWindowManagerUtil.smallWindow!=null){
                MyWindowManagerUtil.smallWindow.setVisibility(View.GONE);
            }
            startActivity(new Intent(WelcomeActivity.this,ServiceActivity.class));
            finish();

        }
    }
    private void initPicture() {
        bgPicture.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return MIMAGE.length+1;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
            @Override
            public void destroyItem(ViewGroup container,int position,Object object){
                container.removeView((View)object);
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                ImageView imageView = new ImageView(WelcomeActivity.this);
                if(position==3){ /*滑到第四章自动跳转*/
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    imageView.setImageResource(MIMAGE[position]);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    container.addView(imageView);
                    bgPicture.setObjectForPosition(imageView, position);
                }
                return imageView;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_tv:
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    methedUtil.showToast(WelcomeActivity.this,"再按一次退出程序");
                    firstTime=secondTime;
                    return true;
                }
                else {
                    if(MyWindowManagerUtil.smallWindow!=null){MyWindowManagerUtil.smallWindow.setVisibility(View.VISIBLE); }
                    finish();
                }
                break;
        }
        return super.onKeyUp(keyCode,event);
    }

    @Override
    public void initControl() {
        bgPicture=(CustomViewPager)findViewById(R.id.cvp_bgPicture);
        start_tv=(TextView)findViewById(R.id.start_tv);
    }

    @Override
    public void addControlListener() {
        start_tv.setOnClickListener(this);
    }

}
