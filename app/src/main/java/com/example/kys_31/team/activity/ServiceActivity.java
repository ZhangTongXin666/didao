package com.example.kys_31.team.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys_31.team.R;
import com.example.kys_31.team.adapter.Fuction_adapter;
import com.example.kys_31.team.adapter.ServicePopAdapter;
import com.example.kys_31.team.bean.Indent;
import com.example.kys_31.team.bean.MyUser;
import com.example.kys_31.team.bean.PriceAndCount;
import com.example.kys_31.team.custom.RoundImageView;
import com.example.kys_31.team.service.FloatWindowService;
import com.example.kys_31.team.slider.SdeingMenu;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.util.MyWindowManagerUtil;
import com.example.kys_31.team.variable.Variable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.zip.Inflater;

import c.b.BP;
import c.b.PListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import static com.example.kys_31.team.R.id.et_getnumber;
import static com.example.kys_31.team.variable.Variable.if_Show_Floatwindow;
import static com.example.kys_31.team.variable.Variable.methedUtil;
import static com.example.kys_31.team.variable.Variable.sendtype_List;
import static com.example.kys_31.team.variable.Variable.str_Account;
import static com.example.kys_31.team.variable.Variable.str_Head;
import static com.example.kys_31.team.variable.Variable.str_Name;
import static com.example.kys_31.team.variable.Variable.str_School;
import static com.example.kys_31.team.variable.Variable.str_Sex;
import static com.example.kys_31.team.variable.Variable.str_address;

/**
 * @author 张同心
 * @function 服务功能
 * Created by kys_31 on 2017/3/6.
 */

public class ServiceActivity extends Activity implements Button.OnClickListener,Init{

    /*定义变量名称*/
    private RoundImageView riv_Head=null;
    private AutoCompleteTextView et_Sendtype=null;
    private EditText et_Getnumber=null;
    private EditText et_Detail=null;
    private Button bt_Service=null;
    private Button bt_Window=null;
    private ImageView iv_Add=null;
    private ImageView iv_exit=null;
    private ListView fuction_lv;
    private TextView tv_Slider_name=null;
    private RoundImageView riv_Slider_Head=null;
    private TextView tvThingType;
    private LinearLayout llThingType;
    private SdeingMenu mLeftMenu;//侧滑菜单


    /*定义变量名称*/
    private double service_Price=0.01;
    private long firstTime=0;
    private Fuction_adapter adapter;
    private Intent intent;
    private String count;
    private String nocount;
    private String id;
    private Dialog payDialog;
    private int payNotifacationDialog=0;//支付通知dialog自己的状态
    private int dialogNotifacationPay=0;//dialog通知支付自己的状态
    private boolean whichPay=true;

    /*标签*/
    private String TAG="ServiceActivity";

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){ getWindow().setStatusBarColor(getResources().getColor(R.color.servicetitlesame,null));}//改变状态栏
        setContentView(R.layout.activity_service);
        /*版本更新提醒*/
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.initAppVersion();
        BmobUpdateAgent.update(this);

        initControl();
        addControlListener();
        initSendType();
        ListItemClick();
    }

    /*选择快递服务*/
    private void initSendType() {
        String[] arr=new String[]{"顺丰快递","圆通快递","申通快递","中通快递","韵达快递","天天快递",
                "百世汇通","京东快递","国通快递","EMS快递","邮政包裹","聚美优品快递","全峰快递","如风达快递",
                "世运快递","宅急送"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ServiceActivity.this,android.R.layout.simple_list_item_1,arr);
        et_Sendtype.setAdapter(adapter);
    }

    /*选择快递类型*/
    private void selectSendType() {
        View parent=(TextView)findViewById(R.id.et_sendtype);
        final View popView=View.inflate(ServiceActivity.this,R.layout.popupwindow_sendtype_item_service_activity,null);
        ListView lv_Typename=(ListView) popView.findViewById(R.id.lv_sendtype);
        ServicePopAdapter service_Adapter=new ServicePopAdapter(ServiceActivity.this,sendtype_List);
        final PopupWindow popWindow=new PopupWindow(popView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        lv_Typename.setAdapter(service_Adapter);
        lv_Typename.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_Sendtype.setText(sendtype_List.get(position));
                et_Sendtype.setSelection(et_Sendtype.getText().length());
                et_Getnumber.requestFocus();
                popWindow.dismiss();
            }
        });
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(true);
        ColorDrawable cd=new ColorDrawable();
        popWindow.setBackgroundDrawable(cd);
        popWindow.showAsDropDown(parent);
        popWindow.setTouchable(true);
    }

    /*选择物品类型*/
    private void selectThingType() {
        View parent=findViewById(R.id.tv_thingType);
        final View popThingType= View.inflate(ServiceActivity.this,R.layout.popupwindow_thingtype_service_activity,null);
        ListView lvThingType=(ListView)popThingType.findViewById(R.id.lv_thingType);
        final List<String> listThingType=new ArrayList<>();
        listThingType.add("易碎物品");
        listThingType.add("电子产品");
        listThingType.add("服装鞋类");
        listThingType.add("图书音像");
        listThingType.add("生鲜水果");
        listThingType.add("各种零食");
        listThingType.add("重要文件");
        listThingType.add("其他");
        final PopupWindow popWindow=new PopupWindow(popThingType, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ServiceActivity.this,R.layout.popupwindow_thingtype_item_service_activity,R.id.tv_thingType,listThingType);
        lvThingType.setAdapter(adapter);
        lvThingType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvThingType.setText(listThingType.get(position));
                popWindow.dismiss();
            }
        });
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(true);
        ColorDrawable cd=new ColorDrawable(Color.WHITE);
        popWindow.setBackgroundDrawable(cd);
        popWindow.showAsDropDown(parent);
        popWindow.setTouchable(true);
    }

    /**
     * 切换菜单
     * @param view
     */
    public void toggleMenu(View view) {
        mLeftMenu.toggle();
    }
    //ListView点击事件
    private void ListItemClick() {
        fuction_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent_edit=new Intent();
                        intent_edit.setClass(ServiceActivity.this, EditActivity.class);
                        startActivity(intent_edit);
                        mLeftMenu.toggle();
                        break;
                    case 1:
                        Intent intent_customer_service=new Intent();
                        intent_customer_service.setClass(ServiceActivity.this, PersonServiceActivity.class);
                        startActivity(intent_customer_service);
                        mLeftMenu.toggle();
                        break;
                    case 2:
                        Intent intent_systemMessage_service=new Intent();
                        intent_systemMessage_service.setClass(ServiceActivity.this, SystemMessageActivity.class);
                        startActivity(intent_systemMessage_service);
                        mLeftMenu.toggle();
                        break;
                    case 3:
                        Intent intent_Message_service=new Intent();
                        intent_Message_service.setClass(ServiceActivity.this, MainServiceActivity.class);
                        startActivity(intent_Message_service);
                        mLeftMenu.toggle();
                        break;
                    case 4:
                        Intent intent_indent_service=new Intent();
                        intent_indent_service.setClass(ServiceActivity.this, MainServiceActivity.class);
                        startActivity(intent_indent_service);
                        mLeftMenu.toggle();
                        break;
                }
            }
        });
    }

    /*开启悬浮窗*/
    private void start_Float_Window() {
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){
            if(!if_Show_Floatwindow){
                askForpermission();
            }
            else {
                MyWindowManagerUtil.smallWindow.setVisibility(View.VISIBLE);
                finish();
            }
        }else {
            if (if_Show_Floatwindow){
                MyWindowManagerUtil.smallWindow.setVisibility(View.VISIBLE);
                finish();
            }else {
                if_Show_Floatwindow=true;
                startService(intent);
                finish();
            }
        }
    }

    /*请求悬浮窗权限*/
    private void askForpermission() {
        if(!Settings.canDrawOverlays(ServiceActivity.this)){
            methedUtil.showToast(ServiceActivity.this,"当前无权限，请授权");
            Intent intent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+getPackageName()));
            startActivityForResult(intent,1234);
        }
        else {
            /*开启悬浮窗*/
            if_Show_Floatwindow=true;
            startService(intent);
            finish();
        }
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1234){
            if(!Settings.canDrawOverlays(ServiceActivity.this)){
                methedUtil.showToast(ServiceActivity.this,"授权失败，无法开启悬浮窗");
            }else {
                methedUtil.showToast(ServiceActivity.this,"授权成功");
                /*开启悬浮窗*/
                if_Show_Floatwindow=true;
                startService(intent);
                finish();
            }
        }
    }

    /*选择支付方式*/
    private void show_Dialog() {
        LayoutInflater inflater=LayoutInflater.from(ServiceActivity.this);
        View view=inflater.inflate(R.layout.dialog_check_message_view,null);
        final Dialog dialog=new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        Button bt_VX_Pay=(Button)view.findViewById(R.id.bt_VX_Pay);
        Button bt_ZFB_Pay=(Button)view.findViewById(R.id.bt_ZFB_Pay) ;

        /*初始化变量*/
        EditText et_name=(EditText)view.findViewById(R.id.et_name);
        EditText et_phonenumber=(EditText)view.findViewById(R.id.et_phonenumber);
        EditText et_school=(EditText)view.findViewById(R.id.et_school);
        EditText et_address=(EditText)view.findViewById(R.id.et_address);
        EditText et_sendtype=(EditText)view.findViewById(R.id.et_sendtype);
        EditText et_getnumber=(EditText)view.findViewById(R.id.et_getnumber);

        /*初始化个人信息*/
        et_name.setText(str_Name);
        et_phonenumber.setText(str_Account);
        et_school.setText(str_School);
        et_address.setText(str_address);
        et_sendtype.setText(et_Sendtype.getText().toString());
        et_getnumber.setText(et_Getnumber.getText().toString());

        /*选择支付方式*/
        bt_ZFB_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPay=true;
                ifCheckThing();
                dialog.dismiss();
            }
        });
        bt_VX_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPay=false;
                ifCheckThing();
                dialog.dismiss();
            }
        });

    }

    /*是否当面验货，开启线程，为获取价格提供时间*/
    private void ifCheckThing() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(whichPay){
                    handler.sendEmptyMessage(1);
                }
                else{
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(final Message msg){
            LayoutInflater inflater=LayoutInflater.from(ServiceActivity.this);
            View dialogView=inflater.inflate(R.layout.dialog_ifcheckthing_view,null);
            final TextView tvDutyExplain=(TextView)dialogView.findViewById(R.id.tv_dutyExplain);
            RadioGroup rgCheck=(RadioGroup)dialogView.findViewById(R.id.rg_check);
            Button btSure=(Button)dialogView.findViewById(R.id.bt_sure);
            Button btCancle=(Button)dialogView.findViewById(R.id.bt_cancle);
            final Dialog dialog=new AlertDialog.Builder(ServiceActivity.this).create();
            dialog.show();
            dialog.getWindow().setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);

            rgCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId){
                        case R.id.rb_need:
                            tvDutyExplain.setText(R.string.neadcheckthing);
                            break;
                        case R.id.rb_noNeed:
                            tvDutyExplain.setText(R.string.noneadcheckthing);
                            break;
                    }
                }
            });

            btSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    save_Message_Bmob();

                }
            });
            btCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    };




    /**
     * 存储信息到Bmob
     */
    private void save_Message_Bmob() {
        Indent indent=new Indent();
        indent.setSchool(str_School);
        indent.setAccount(str_Account);
        indent.setAddress(str_address);
        indent.setName(str_Name);
        indent.setSex(str_Sex);
        indent.setThingType(tvThingType.getText().toString());
        indent.setPhonenumber(str_Account);
        indent.setGetnumber(et_Getnumber.getText().toString());
        indent.setSendtype(et_Sendtype.getText().toString());
        indent.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                MyUser user=new MyUser();
                user.setCount(String.valueOf(" "));
                user.update(id,new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        payDialog=new AlertDialog.Builder(ServiceActivity.this).setTitle("支付成功").setMessage("您的快递马上会送到您指定的地点，请等待验收。").setIcon(R.mipmap.logo_logos).create();
                        payDialog.show();
                    }
                });
            }
        });
    }

    /**
     * 检查某包名应用是否已经安装
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName,String browserUrl){
        try{
            /*检查是否安装支付宝客户端*/
            getPackageManager().getPackageInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            /*没有安装支付宝，跳转到应用市场*/
            try {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id="+packageName));
                startActivity(intent);
            }catch (Exception ee){
                    /*应用市场也没有，就去支付宝官网下载*/
                try {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                }
                catch (Exception eee){
                    methedUtil.showToast(ServiceActivity.this,"您的手机环境不适合使用支付宝");
                }
            }
        }
        return false;
    }

    /**
     * 安装APK
     * @param s 插件
     */
    private void installApk(String s){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            installBmobPayPlugin(s);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (permissions[0].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installBmobPayPlugin("bp.db");
                } else {
                    //提示没有权限，安装不了
                    Toast.makeText(ServiceActivity.this,"您拒绝了权限，这样无法安装支付插件",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /**
     * 安装assets里的apk文件
     * @param fileName
     */
    private void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*开启Dialog*/
    private void startPayDialog(int icon,String title,String message,boolean onTouch) {
        payDialog=new AlertDialog.Builder(ServiceActivity.this)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .create();
        payDialog.setCanceledOnTouchOutside(onTouch);
        payDialog.show();
    }

    //*显示退出PopWindow*/
    private void show_Exit_Pop() {
        View parent =(ImageView)findViewById(R.id.iv_exit);
        View popWindow= View.inflate(ServiceActivity.this,R.layout.popupwindnw_exit_service_activity,null);
        final PopupWindow pop=new PopupWindow(popWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(false);
        pop.setFocusable(true);
        ColorDrawable cd=new ColorDrawable();
        pop.setBackgroundDrawable(cd);
        pop.showAsDropDown(parent);
        pop.setTouchable(true);
        /*初始化控件*/
        Button cancle=(Button)popWindow.findViewById(R.id.bt_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                finish();
            }
        });
        ((Button)popWindow.findViewById(R.id.bt_switch_Account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*取消自动登陆*/
                SharedPreferences sp=getSharedPreferences("auto_Login",0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("account","");
                editor.putString("password","");
                editor.putString("head","");
                editor.putString("phonenumber","");
                editor.putString("address","");
                editor.putString("name","");
                editor.putString("school","");
                editor.commit();
                startActivity(new Intent(ServiceActivity.this,MainActivity.class));
                finish();
                pop.dismiss();
            }
        });
    }
    @Override
    public void initControl() {
        tvThingType=(TextView)findViewById(R.id.tv_thingType);
        llThingType=(LinearLayout)findViewById(R.id.ll_thingType);
        mLeftMenu = (SdeingMenu) findViewById(R.id.menu);
        fuction_lv = (ListView) findViewById(R.id.function_lv);
        tv_Slider_name=(TextView) findViewById(R.id.tv_name);
        riv_Slider_Head=(RoundImageView)findViewById(R.id.head_img);
        riv_Head=(RoundImageView)findViewById(R.id.riv_head);
        et_Sendtype=(AutoCompleteTextView) findViewById(R.id.et_sendtype);
        et_Detail=(EditText)findViewById(R.id.et_detail_Message);
        et_Getnumber=(EditText)findViewById(et_getnumber);
        bt_Service=(Button)findViewById(R.id.bt_service);
        bt_Window=(Button)findViewById(R.id.bt_window);
        iv_Add=(ImageView)findViewById(R.id.iv_add);
        iv_exit=(ImageView)findViewById(R.id.iv_exit);


        /*初始化侧拉菜单*/
        tv_Slider_name.setText(str_Name);
        riv_Slider_Head.setImageBitmap(methedUtil.covertStringToIcon(str_Head));
        riv_Head.setImageBitmap(methedUtil.covertStringToIcon(str_Head));
        adapter = new Fuction_adapter(this);
        fuction_lv.setAdapter(adapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        intent=new Intent(ServiceActivity.this, FloatWindowService.class);
    }

    @Override
    public void addControlListener() {
        llThingType.setOnClickListener(this);
        et_Sendtype.setOnClickListener(this);
        et_Detail.setOnClickListener(this);
        bt_Window.setOnClickListener(this);
        iv_Add.setOnClickListener(this);
        iv_exit.setOnClickListener(this);
        bt_Service.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add:
                selectSendType();   //选择快递类型
                break;
            case R.id.bt_service:
                //选择服务
                if(!TextUtils.isEmpty(et_Getnumber.getText().toString())&&!TextUtils.isEmpty(et_Sendtype.getText().toString())&&!TextUtils.isEmpty(tvThingType.getText().toString())){
                    if(methedUtil.check_Net_Available(ServiceActivity.this)){ show_Dialog(); }
                    else {methedUtil.showToast(ServiceActivity.this,"无网络连接");}
                }
                else methedUtil.showToast(ServiceActivity.this,"请填全信息");
                break;
            case R.id.bt_window:
                start_Float_Window();//选中悬浮窗
                break;
            case R.id.iv_exit:
                show_Exit_Pop();
                break;
            case R.id.ll_thingType:
                selectThingType();
                break;
        }
    }
    @Override
    protected void onStart(){
        initControl();
        ListItemClick();
        super.onStart();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    methedUtil.showToast(ServiceActivity.this,"再按一次退出程序");
                    firstTime=secondTime;
                    return true;
                }
                else {
                    if(MyWindowManagerUtil.smallWindow!=null){
                        MyWindowManagerUtil.smallWindow.setVisibility(View.VISIBLE);
                        finish();
                    }
                    else {
                        finish();
                    }
                }
                break;
        }
        return super.onKeyUp(keyCode,event);
    }


}
