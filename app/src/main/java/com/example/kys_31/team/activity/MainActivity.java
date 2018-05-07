package com.example.kys_31.team.activity;
/**
 * @author 张同心
 * @startTime 2017-2-27
 * @function 登录
 */

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys_31.team.R;
import com.example.kys_31.team.adapter.MainPopAdapter;
import com.example.kys_31.team.bean.MyUser;
import com.example.kys_31.team.custom.CustomProgressBar;
import com.example.kys_31.team.custom.RoundImageView;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.variable.Variable;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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
import static com.example.kys_31.team.variable.Variable.user_Important_Message_List;

public class MainActivity extends AppCompatActivity implements Init,View.OnClickListener{

    /*定义控件变量名称*/
    private RoundImageView riv_Head=null;
    private EditText et_Account=null;
    private EditText et_Password=null;
    private Button bt_Login=null;
    private TextView tv_Register=null;
    private ImageView iv_Down=null;
    private ImageView iv_Clear_ALL=null;
    private ImageView iv_Clear_Password=null;
    private CustomProgressBar progressBar;
    public static ListView lv_User_List;

    /*定义变量*/
    private SharedPreferences sp=null;
    public static boolean user_If_Save=false;//用户是否存在
    private long firstTime=0;

    /*标签*/
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){ getWindow().setStatusBarColor(getResources().getColor(R.color.logintitlesame,null));}//改变状态栏颜色，低于4.4无法改变。
        setContentView(R.layout.activity_login);
        methedUtil.hideTitle(MainActivity.this);//隐藏标题栏
        initControl();//初始化控件
        addControlListener();//增加控件监听
    }

    /*显示Popupwindow*/
    private void showPopupwindow(){
        View parent = (TextView)this.findViewById(R.id.et_account);
        final View popView = View.inflate(MainActivity.this,R.layout.popupwindow_quest_login_item_view,null);
        lv_User_List=(ListView)popView.findViewById(R.id.lv_user_List);
        MainPopAdapter adapter=new MainPopAdapter(MainActivity.this, Variable.user_Important_Message_List);
        final PopupWindow popWindow=new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lv_User_List.setAdapter(adapter);
        lv_User_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_Account.setText(Variable.user_Important_Message_List.get(position).get("account"));
                et_Password.setText(Variable.user_Important_Message_List.get(position).get("password"));
                str_Account=et_Account.getText().toString();
                str_Password=et_Password.getText().toString();
                loginTest();
                popWindow.dismiss();
            }
        });
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(true);
        ColorDrawable dw=new ColorDrawable();
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAsDropDown(parent);
        popWindow.setTouchable(true);
    }

    /*登录*/
    private void login() {
        str_Account=et_Account.getText().toString();
        str_Password=et_Password.getText().toString();

        /*判断账号密码是否为空*/
        if(TextUtils.isEmpty(str_Account)){
            Toast.makeText(MainActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(str_Password)){
            Toast.makeText(MainActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        loginTest();//登录检测
    }

    /*后台登录检测*/
    private void loginTest() {
        if(methedUtil.check_Net_Available(MainActivity.this)){
            /*显示进度条*/
            progressBar=new CustomProgressBar(MainActivity.this,"登陆中。。。");
            progressBar.start();

            MyUser user=new MyUser();
            user.setUsername(str_Account);
            user.setPassword(str_Password);
            user.login(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    final HashMap<String,String> map=new HashMap<String,String>();
                    if(e.getErrorCode()!=101){
                        /*判断用户是否已存在本地*/
                        for (int i=0;i<Variable.user_Important_Message_List.size();i++){
                            if(str_Account.equals(Variable.user_Important_Message_List.get(i).get("account"))){
                                user_If_Save=true;
                                i=Variable.user_Important_Message_List.size();
                            }
                        }

                        BmobQuery<MyUser> bmobQuery=new BmobQuery<MyUser>();
                        bmobQuery.findObjects(new FindListener<MyUser>() {
                            @Override
                            public void done(List<MyUser> list, BmobException e) {
                                for (int i=0;i<list.size();i++){
                                    if(str_Account.equals(list.get(i).getUsername())){

                                        str_School=list.get(i).getSchool();
                                        str_address=list.get(i).getAddress();
                                        str_Head=list.get(i).getHead();
                                        str_Phonenumber=list.get(i).getPhone();
                                        str_Name=list.get(i).getTrue_name();
                                        str_Sex=list.get(i).getSex();
                                        i=list.size();

                                     /*实现自动登录*/
                                        SharedPreferences sp=getSharedPreferences("auto_Login",0);
                                        SharedPreferences.Editor editor=sp.edit();
                                        editor.putString("account",str_Account);
                                        editor.putString("password",str_Password);
                                        editor.putString("head",str_Head);
                                        editor.putString("phonenumber",str_Account);
                                        editor.putString("address",str_address);
                                        editor.putString("name",str_Name);
                                        editor.putString("school",str_School);
                                        editor.putString("sex",str_Sex);
                                        editor.commit();

                                        if(!user_If_Save){
                                            map.put("school",str_School);
                                            map.put("account",str_Account);
                                            map.put("password",str_Password);
                                            map.put("head",str_Head);
                                            map.put("address",str_address);
                                            map.put("phonenumber",str_Account);
                                            map.put("name",str_Name);
                                            map.put("sex",str_Sex);
                                            Variable.database.add_Data(map);
                                        }

                                       /*关闭Dialog*/
                                       progressBar.stop();
                                        Variable.user_Important_Message_List=Variable.database.query_Data();
                                       /*界面跳转*/
                                        startActivity(new Intent(MainActivity.this,ServiceActivity.class));
                                        finish();
                                    }
                                }
                            }
                        });
                    }
                    else {
                        /*查看本地是否存在该账户，存在即删除*/
                        for(int i=0;i<Variable.user_Important_Message_List.size();i++){
                            if (str_Account.equals(Variable.user_Important_Message_List.get(i).get("account"))){
                                Variable.database.delete_Data(str_Account);
                                i=Variable.user_Important_Message_List.size();
                            }
                        }
                        /*提示登录失败*/
                        progressBar.stop();
                        et_Password.setText("");
                        Variable.methedUtil.showToast(MainActivity.this,"密码错误");
                    }
                }
            });
        }
        else {methedUtil.showToast(MainActivity.this,"无网络连接");}
    }


    /**
     * 初始化控件
     */
    @Override
    public void initControl() {
        riv_Head=(RoundImageView)findViewById(R.id.riv_head);
        et_Account=(EditText)findViewById(R.id.et_account);
        et_Password=(EditText)findViewById(R.id.et_password);
        bt_Login=(Button)findViewById(R.id.bt_login);
        tv_Register=(TextView)findViewById(R.id.tv_register);
        iv_Down=(ImageView)findViewById(R.id.iv_down);
        iv_Clear_ALL=(ImageView)findViewById(R.id.iv_clear_All);
        iv_Clear_Password=(ImageView)findViewById(R.id.iv_clear_Password);
        Variable.user_Important_Message_List=Variable.database.query_Data();//初始化数据
    }

    @Override
    public void addControlListener() {
        bt_Login.setOnClickListener(this);
        tv_Register.setOnClickListener(this);
        iv_Down.setOnClickListener(this);
        iv_Clear_ALL.setOnClickListener(this);
        iv_Clear_Password.setOnClickListener(this);
        et_Account.addTextChangedListener(new EditTextListener(1));
        et_Password.addTextChangedListener(new EditTextListener(2));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                break;
            case R.id.iv_down:
                showPopupwindow();//显示Popupwindow
                break;
            case R.id.iv_clear_All:
                et_Password.setText("");
                et_Account.setText("");
                break;
            case R.id.iv_clear_Password:
                et_Password.setText("");
                break;
        }
    }


    /*自定义EditText的监听器*/
    class EditTextListener implements TextWatcher{
        int which=0;
        public EditTextListener(int which){
            this.which=which;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            iv_Clear_ALL.setVisibility(View.GONE);
            iv_Clear_Password.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (which==1){
                if (et_Password.getText().length()!=0){
                    iv_Clear_Password.setVisibility(View.VISIBLE);
                }
                iv_Clear_ALL.setVisibility(View.VISIBLE);
            }
            else {
                if(et_Account.getText().length()!=0){
                    iv_Clear_ALL.setVisibility(View.VISIBLE);
                }
                iv_Clear_Password.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            et_Account.setSelection(et_Account.length());
            if(which==1){
                if(et_Account.length()==0){
                    iv_Clear_ALL.setVisibility(View.GONE);
                }
            }
            else {
                if(et_Password.length()==0){
                    iv_Clear_Password.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    methedUtil.showToast(MainActivity.this,"再按一次退出程序");
                    firstTime=secondTime;
                    return true;
                }
                else {
                    finish();//退出程序
                }
                break;
        }
        return super.onKeyUp(keyCode,event);
    }
}
