package com.example.kys_31.team.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kys_31.team.R;
import com.example.kys_31.team.bean.MyUser;
import com.example.kys_31.team.custom.CustomProgressBar;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.variable.Variable;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.kys_31.team.variable.Variable.database;
import static com.example.kys_31.team.variable.Variable.head;
import static com.example.kys_31.team.variable.Variable.methedUtil;
import static com.example.kys_31.team.variable.Variable.str_Account;
import static com.example.kys_31.team.variable.Variable.str_Name;
import static com.example.kys_31.team.variable.Variable.str_Password;
import static com.example.kys_31.team.variable.Variable.str_address;

/**
 * @author 杨贺
 * @function 更改密码
 * Created by kys_7 on 2017/3/8.
 */

public class ChangepasswardActivity extends Activity implements View.OnClickListener,Init{

    /*声明变量名称*/
    private EditText old_password;
    private EditText new_password;
    private EditText ensure_password;
    private Button true_bt;
    private Button back_bt;
    private CustomProgressBar progressBar;

    /*标签*/
    private String TAG="ChangepasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changedpassword);
        initControl();
        addControlListener();
    }

    /*修改*/
    private void motify() {
        String str_Old_Password=old_password.getText().toString();
        final String str_New_Password=new_password.getText().toString();
        String str_Ensure_Password=ensure_password.getText().toString();

        if(str_Old_Password.equals(Variable.str_Password)){
            if(str_New_Password.equals(str_Ensure_Password)&& !TextUtils.isEmpty(str_Ensure_Password)){
                if(str_New_Password.length()>=6 &&str_New_Password.length()<20){
                    if(methedUtil.check_Net_Available(ChangepasswardActivity.this)){
                                /*显示进度条*/
                        progressBar=new CustomProgressBar(ChangepasswardActivity.this,"保存中。。。");
                        progressBar.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BmobQuery<MyUser> query_User=new BmobQuery<MyUser>();
                                query_User.findObjects(new FindListener<MyUser>() {
                                    @Override
                                    public void done(List<MyUser> list, BmobException e) {
                                        for (int i=0;i<list.size();i++){
                                            if(list.get(i).getUsername().equals(str_Account)){
                                                String id=list.get(i).getObjectId();
                                                MyUser user=new MyUser();
                                                user.setPassword(new_password.getText().toString());
                                                user.update(id, new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        str_Password=new_password.getText().toString();
                                                        HashMap<String,String> map=new HashMap<String, String>();
                                                        map.put("name",str_Name);
                                                        map.put("head",methedUtil.convertBitmapToString(head));
                                                        map.put("phonenumber",str_Account);
                                                        map.put("address",str_address);
                                                        map.put("password",str_Password);
                                                        database.updata_Data(str_Account,map);

                                                        progressBar.stop();
                                                        ensure_password.setText("");
                                                        new_password.setText("");
                                                        old_password.setText("");
                                                        methedUtil.showToast(ChangepasswardActivity.this,"修改成功");
                                                        finish();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }).start();
                    }
                    else {methedUtil.showToast(ChangepasswardActivity.this,"网络不可用");}
                }
                else {methedUtil.showToast(ChangepasswardActivity.this,"密码不符合规范");
                    new_password.setText("");
                    ensure_password.setText("");
                }
            }
            else {
                methedUtil.showToast(ChangepasswardActivity.this,"两次输入密码不一致");
            }

        }
        else {methedUtil.showToast(ChangepasswardActivity.this,"旧密码输入有误");
            old_password.setText("");
            new_password.setText("");
            ensure_password.setText("");
        }
    }

    @Override
    public void initControl() {
        old_password= (EditText) findViewById(R.id.old_pw_et);
        new_password= (EditText) findViewById(R.id.new_pw_et);
        ensure_password= (EditText) findViewById(R.id.ensure_pw_et);
        true_bt= (Button) findViewById(R.id.true_bt);
        back_bt= (Button) findViewById(R.id.back_bt);
    }

    @Override
    public void addControlListener() {
        true_bt.setOnClickListener(this);
        back_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.true_bt:
                motify();
                break;
            case R.id.back_bt:
                finish();
                break;
        }
    }
}
