package com.example.kys_31.team.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys_31.team.R;
import com.example.kys_31.team.bean.PersonPhone;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.kys_31.team.variable.Variable.methedUtil;

/**
 * @author 张同心
 * @function 客服咨询
 * Created by kys_31 on 2017/3/7.
 */

public class PersonServiceActivity  extends Activity implements View.OnClickListener{

    /*定义控件变量名称*/
    private ImageView iv_One=null;
    private ImageView iv_Two=null;
    private ImageView iv_Three=null;
    private ImageView iv_Four=null;
    private TextView tv_One=null;
    private TextView tv_Two=null;
    private TextView tv_Three=null;
    private TextView tv_Four=null;
    private ImageView ivBack=null;
    private TextView tvBack=null;

    /*定义变量名称*/
    private String str_One_Phone,str_Two_Phone,str_Three_Phone,str_Four_Phone;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){  getWindow().setStatusBarColor(getResources().getColor(R.color.registertitle));}
        setContentView(R.layout.activity_personservice);

        apply_Permission();
        initControl();//初始化变量

        if(methedUtil.check_Net_Available(PersonServiceActivity.this)){ initPhone(); }
        else {methedUtil.showToast(PersonServiceActivity.this,"网络不可用,无法拨打客服");}

    }

    /* 申请拨打电话授权*/
    private void apply_Permission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},100);
        }
    }

    /**
     * 获取客服电话
     */
    private void initPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<PersonPhone> query=new BmobQuery<PersonPhone>();
                query.findObjects(new FindListener<PersonPhone>() {
                    @Override
                    public void done(List<PersonPhone> list, BmobException e) {
                        str_One_Phone=list.get(0).getStr_One_Phone();
                        str_Two_Phone=list.get(0).getStr_Two_Phone();
                        str_Three_Phone=list.get(0).getStr_Three_Phone();
                        str_Four_Phone=list.get(0).getStr_Four_Phone();
                    }
                });
            }
        }).start();

    }

    /**
     * 初始化变量
     */
    private void initControl() {
        ivBack=(ImageView)findViewById(R.id.iv_back);
        tvBack=(TextView)findViewById(R.id.tv_back);
        tv_One=(TextView)findViewById(R.id.tv_one);
        tv_Two=(TextView)findViewById(R.id.tv_two);
        tv_Three=(TextView)findViewById(R.id.tv_three);
        tv_Four=(TextView)findViewById(R.id.tv_four);
        iv_One=(ImageView) findViewById(R.id.iv_one);
        iv_Two=(ImageView) findViewById(R.id.iv_two);
        iv_Three=(ImageView) findViewById(R.id.iv_three);
        iv_Four=(ImageView) findViewById(R.id.iv_four);

        iv_One.setOnClickListener(this);
        iv_Two.setOnClickListener(this);
        iv_Three.setOnClickListener(this);
        iv_Four.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        switch (v.getId()){
            case R.id.iv_one:
                if(!TextUtils.isEmpty(str_One_Phone)){
                    intent.setData(Uri.parse("tel:"+str_One_Phone));
                }
                else {
                    intent.setData(Uri.parse("tel:"+"1008611"));
                }
                startActivity(intent);
                break;
            case R.id.iv_two:
                if(!TextUtils.isEmpty(str_Two_Phone)){
                    intent.setData(Uri.parse("tel:"+str_Two_Phone));
                }
                else {
                    intent.setData(Uri.parse("tel:"+"1008611"));
                }
                startActivity(intent);
                break;
            case R.id.iv_three:
                if(!TextUtils.isEmpty(str_Three_Phone)){
                    intent.setData(Uri.parse("tel:"+str_Three_Phone));
                }
                else {
                    intent.setData(Uri.parse("tel:"+"1008611"));
                }
                startActivity(intent);
                break;
            case R.id.iv_four:
                if(!TextUtils.isEmpty(str_Four_Phone)){
                    intent.setData(Uri.parse("tel:"+str_Four_Phone));
                }
                else {
                    intent.setData(Uri.parse("tel:"+"1008611"));
                }
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
