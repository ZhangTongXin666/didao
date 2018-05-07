package com.example.kys_31.team.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys_31.team.R;
import com.example.kys_31.team.bean.MyUser;
import com.example.kys_31.team.bean.School;
import com.example.kys_31.team.custom.CustomProgressBar;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.variable.Variable;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.example.kys_31.team.variable.Variable.all_School;
import static com.example.kys_31.team.variable.Variable.methedUtil;
import static com.example.kys_31.team.variable.Variable.str_Head;
import static com.example.kys_31.team.variable.Variable.str_Phonenumber;

public class RegisterActivity extends AppCompatActivity implements Init,View.OnClickListener
{
    private Button mRegister; //注册
    private EditText mAccount; //账户
    private EditText mPassword; //密码
    private EditText mAddress; //地址
    private EditText et_Name;//姓名
    private AutoCompleteTextView actv_School;//学校
    private CheckBox cb_Read;
    private RadioGroup rg_sex;
    private TextView tv_User_Explain;
    private CustomProgressBar progressBar;
    private ImageView ivBack;
    private TextView tvBack;

    /*定义变量*/
    private String str_Account,str_Password,str_Sex,str_Address,str_Name,str_School;

    /*标签*/
    private String TAG="registerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){getWindow().setStatusBarColor(getResources().getColor(R.color.registertitle,null));}//状态栏颜色
        setContentView(R.layout.activity_register);
        methedUtil.hideTitle(RegisterActivity.this);//隐藏标题栏
          /*显示进度条*/
        progressBar=new CustomProgressBar(this,"获取数据。。。");
        progressBar.start();
        initControl();
        addControlListener();
        initSchoolData();
    }


    /*使用说明*/
    private void explain() {
        LayoutInflater inflater=LayoutInflater.from(RegisterActivity.this);
        View explain_View=inflater.inflate(R.layout.dialog_userexplain_view,null);
        final Dialog dialog=new AlertDialog.Builder(RegisterActivity.this).create();
        dialog.show();
        dialog.setContentView(explain_View);
        CheckBox cb_Sure=(CheckBox) explain_View.findViewById(R.id.cb_sure);
        CheckBox cb_Nosure=(CheckBox) explain_View.findViewById(R.id.cb_nosure);
        cb_Sure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_Read.setChecked(true);
                dialog.dismiss();
            }
        });
        cb_Nosure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_Read.setChecked(false);
                dialog.dismiss();
            }
        });
    }

    /**
     * 初始化学校数据
     */
    private void initSchoolData() {
        if(methedUtil.check_Net_Available(RegisterActivity.this)){
            BmobQuery<School> query_School=new BmobQuery<School>();
            query_School.findObjects(new FindListener<School>() {
                @Override
                public void done(List<School> list, BmobException e) {
                    all_School=list;
                    if(all_School != null && all_School.size()!=0){
                        String[] arr=new String[all_School.size()];
                        for(int i=0;i<arr.length;i++){
                            arr[i]=all_School.get(i).getSchool();
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,arr);
                        actv_School.setAdapter(adapter);
                    }
                    progressBar.stop();
                }
            });
        }
        else {methedUtil.showToast(RegisterActivity.this,"网络不可用");}
    }

    /*注册*/
    private void register() {
        /*获取填入信息*/
        str_Account=mAccount.getText().toString();
        str_Password=mPassword.getText().toString();
        str_Address=mAddress.getText().toString();
        str_Name=et_Name.getText().toString();
        str_School=actv_School.getText().toString();
        boolean school_If_Save=false;
        if(!TextUtils.isEmpty(str_School)&&!TextUtils.isEmpty(str_Name)&&!TextUtils.isEmpty(str_Account)&&!TextUtils.isEmpty(str_Password)&&!TextUtils.isEmpty(str_Address)){
            if(all_School != null && all_School.size()!=0){
                for(int i=0;i<all_School.size();i++){
                    if(str_School.equals(all_School.get(i).getSchool())){
                        school_If_Save=true;
                        str_Sex=((RadioButton)findViewById(rg_sex.getCheckedRadioButtonId())).getText().toString();
                             /*判断信息是否正确*/
                        if(str_Account.substring(0,2).equals("13") ||str_Account.substring(0,2).equals("14") || str_Account.substring(0,2).equals("15")||str_Account.substring(0,2).equals("18")){
                            if(str_Password.length()>=6 && str_Password.length()<20){
                                if (cb_Read.isChecked()){
                                    registerMessage();//注册信息
                                }
                                else {
                                    methedUtil.showToast(RegisterActivity.this,"请先阅读递到服务使用条例");
                                }
                            }
                            else {
                                methedUtil.showToast(RegisterActivity.this,"密码不规范");
                            }
                        }else {
                            methedUtil.showToast(RegisterActivity.this,"手机号输入有误");
                        }
                    }
                }
                if(!school_If_Save){methedUtil.showToast(RegisterActivity.this,"没有此学校");}
            }
        }
        else{ methedUtil.showToast(RegisterActivity.this,"信息填写不完整"); }
    }

    /**
     * 注册信息
     */
    private void registerMessage() {
        if(methedUtil.check_Net_Available(RegisterActivity.this)){
            /*显示进度条*/
            progressBar=new CustomProgressBar(RegisterActivity.this,"注册中。。。");
            progressBar.start();
                 /*注册成功，将数据保存到本地*/
            MyUser user=new MyUser();
            user.setSchool(str_School);
            user.setTrue_name(str_Name);
            user.setUsername(str_Account);
            user.setPassword(str_Password);
            str_Head=methedUtil.convertBitmapToString(BitmapFactory.decodeResource(getResources(),R.mipmap.logo_logos));
            user.setHead(str_Head);
            user.setAddress(str_Address);
            user.setSex(str_Sex);
            user.setPhone(str_Account);
            user.setCount("1");

            user.signUp(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e.getErrorCode()!=202){
                      /*数据保存到本地*/
                        HashMap<String,String> map=new HashMap<String,String>();
                        map.put("head",str_Head);
                        map.put("account",str_Account);
                        map.put("password",str_Password);
                        map.put("name",str_Name);
                        map.put("phonenumber",str_Phonenumber);
                        map.put("address",str_Address);
                        map.put("school",str_School);
                        map.put("sex",str_Sex);
                        Variable.database.add_Data(map);
                        Variable.user_Important_Message_List=Variable.database.query_Data();

                        /*将默认的头像放在本地*/
                        progressBar.stop();
                        Variable.head= BitmapFactory.decodeResource(getResources(),R.mipmap.logo_logos);
                        methedUtil.put_Head_to_SDcard(Variable.head);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        /*提示注册失败*/
                        progressBar.stop();
                        methedUtil.showToast(RegisterActivity.this,"账号已存在");
                        mAccount.setText("");
                        mPassword.setText("");
                    }
                }
            });
        }
        else {methedUtil.showToast(RegisterActivity.this,"无网络连接");}
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                register();
                break;
            case R.id.tv_use_Explain:
                explain();
                break;

            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    public void initControl() {
        tv_User_Explain=(TextView)findViewById(R.id.tv_use_Explain);
        rg_sex=(RadioGroup)findViewById(R.id.rg_sex);
        actv_School=(AutoCompleteTextView) findViewById(R.id.actv_school);
        cb_Read=(CheckBox)findViewById(R.id.cb_sure_Read);
        et_Name=(EditText)findViewById(R.id.et_name);
        mRegister=(Button)findViewById(R.id.register);
        mAccount=(EditText)findViewById(R.id.account);
        mPassword=(EditText)findViewById(R.id.password);
        mAddress =(EditText)findViewById(R.id.address);
        ivBack=(ImageView)findViewById(R.id.iv_back);
        tvBack=(TextView)findViewById(R.id.tv_back);
    }

    @Override
    public void addControlListener() {
        mRegister.setOnClickListener(this);
        tv_User_Explain.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }
}
