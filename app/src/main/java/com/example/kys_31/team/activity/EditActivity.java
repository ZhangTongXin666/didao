package com.example.kys_31.team.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kys_31.team.R;
import com.example.kys_31.team.bean.MyUser;
import com.example.kys_31.team.custom.CustomProgressBar;
import com.example.kys_31.team.custom.RoundImageView;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.util.MyWindowManagerUtil;
import com.example.kys_31.team.variable.Variable;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.kys_31.team.R.id.add;
import static com.example.kys_31.team.R.id.edit_head_img;
import static com.example.kys_31.team.variable.Variable.database;
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
 * 编辑个人信息
 * Created by lenovo on 2017/2/25.
 */

public class EditActivity extends Activity implements Init,View.OnClickListener{

    /*声明变量名称*/
    private Button back_bt;
    private RoundImageView riv_head_img;
    private Button edit_change_password_bt;
    private Button edit_save_bt;
    private PopupWindow menuWindow;
    private TextView adlum_tv;
    private TextView camera_tv;
    private TextView cancel_tv;
    private EditText edit_name_et;
    private EditText edit_address_et;
    private EditText edit_phone_et;
    private RadioGroup rg_Sex;
    private RadioButton rb_Man;
    private RadioButton rb_Woman;
    private CustomProgressBar progressBar;

    //设置一个临时路径，保存拍摄的图片
    private File file;
    private  Bitmap bitmap_Head=null;

    /*标签*/
    private String TAG="EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){ getWindow().setStatusBarColor(getResources().getColor(R.color.edittitlesame,null)); }
        setContentView(R.layout.activity_edit);
        initControl();
        addControlListener();
        initMessage();
    }

    /*初始化信息*/
    private void initMessage() {
        if (!TextUtils.isEmpty(str_address)){
            edit_address_et.setText(str_address);
            edit_name_et.setText(str_Name);
            edit_phone_et.setText(str_Account);
            riv_head_img.setImageBitmap(methedUtil.covertStringToIcon(str_Head));
            if(str_Sex.equals("男")){
                rb_Man.setChecked(true);
            }else {
                rb_Woman.setChecked(true);
            }
            edit_name_et.setSelection(edit_name_et.length());
        }
        else {
            SharedPreferences sp = getSharedPreferences("auto_Login", 0);
            str_Account = sp.getString("account", "");
            if (!TextUtils.isEmpty(str_Account)) {
                head = methedUtil.covertStringToIcon(sp.getString("head", " "));
                str_address = sp.getString("address", " ");
                str_Name = sp.getString("name", " ");
                str_Sex = sp.getString("sex", " ");
                str_Head = sp.getString("head", " ");
                str_Phonenumber = sp.getString("phonenumber", " ");
                str_School = sp.getString("school", " ");
                str_Password = sp.getString("password", " ");
                initMessage();
            }else {
                methedUtil.showToast(EditActivity.this,"异常操作，请重新登录。");
                startActivity(new Intent(EditActivity.this,MainActivity.class));
                finish();
            }
        }
    }

    /*保存*/
    private void save() {
        if(!TextUtils.isEmpty(edit_name_et.getText().toString()) &&!TextUtils.isEmpty(edit_address_et.getText().toString())){
            if(methedUtil.check_Net_Available(EditActivity.this)){
                             /*保存头像到本地*/
                if(bitmap_Head!=null){
                    str_Head=methedUtil.convertBitmapToString(bitmap_Head);
                    if(MyWindowManagerUtil.smallWindow!=null){
                        MyWindowManagerUtil.smallWindow.setHead(bitmap_Head);
                    }
                }
                progressBar=new CustomProgressBar(EditActivity.this,"保存中。。。");
                progressBar.start();

                BmobQuery<MyUser> query_User=new BmobQuery<MyUser>();
                query_User.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(List<MyUser> list, BmobException e) {
                        for (int i=0;i<list.size();i++){
                            if(list.get(i).getUsername().equals(str_Account)){
                                String id=list.get(i).getObjectId();
                                MyUser user=new MyUser();
                                user.setHead(methedUtil.convertBitmapToString(methedUtil.covertStringToIcon(str_Head)));
                                user.setAddress(edit_address_et.getText().toString());
                                user.setTrue_name(edit_name_et.getText().toString());
                                user.setPhone(edit_phone_et.getText().toString());
                                user.setSex(((RadioButton)findViewById(rg_Sex.getCheckedRadioButtonId())).getText().toString());
                                user.update(id, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                        HashMap<String,String> map=new HashMap<String, String>();
                                        str_Name=edit_name_et.getText().toString();
                                        str_Phonenumber=edit_phone_et.getText().toString();
                                        str_address=edit_address_et.getText().toString();
                                        str_Sex=((RadioButton)findViewById(rg_Sex.getCheckedRadioButtonId())).getText().toString();

                                        map.put("head",str_Head);
                                        map.put("name",str_Name);
                                        map.put("sex",str_Sex);
                                        map.put("phonenumber",str_Phonenumber);
                                        map.put("address",str_address);
                                        map.put("password",str_Password);
                                        database.updata_Data(str_Account,map);

                                        SharedPreferences sp=getSharedPreferences("auto_Login",0);
                                        SharedPreferences.Editor editor=sp.edit();
                                        editor.putString("password",str_Password);
                                        editor.putString("head",str_Head);
                                        editor.putString("phonenumber",str_Phonenumber);
                                        editor.putString("address",str_address);
                                        editor.putString("name",str_Name);
                                        editor.putString("sex",str_Sex);
                                        editor.commit();

                                        progressBar.stop();
                                        methedUtil.showToast(EditActivity.this,"保存成功");
                                    }
                                });
                            }
                        }
                    }
                });
            }
            else {methedUtil.showToast(EditActivity.this,"网络不可用");}
        }
        else {methedUtil.showToast(EditActivity.this,"请填写完整信息");}
    }

   /*选择头像来源*/
    private void ShowPopuWindow(){
        View contentView= LayoutInflater.from(EditActivity.this).inflate(R.layout.picture_source_edit_activity,null);
        //必须要设置高和宽，否则要报错
        menuWindow=new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        menuWindow.setContentView(contentView);
        adlum_tv= (TextView) contentView.findViewById(R.id.aldum_tv);
        camera_tv=(TextView) contentView.findViewById(R.id.camera_tv);
        cancel_tv=(TextView) contentView.findViewById(R.id.cancel_tv);
        menuWindow.setOutsideTouchable(true);
        menuWindow.setTouchable(true);

        //相册
        adlum_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                // 打开手机相册,设置请求码
                startActivityForResult(intent, 0);
                menuWindow.dismiss();
            }
        });
        //相机
        camera_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent=new Intent("android.media.action.IMAGE_CAPTURE");//CAPTURE 捕获
                file=new File(Environment.getExternalStorageDirectory(),"Team_Head.jpg");
                Uri register_Team_Head_Uri=Uri.fromFile(file);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,register_Team_Head_Uri);
                startActivityForResult(camera_intent,1);
                menuWindow.dismiss();
            }
        });
        //取消
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap_Head=BitmapFactory.decodeResource(getResources(),R.mipmap.logo_logos);
                riv_head_img.setImageBitmap(bitmap_Head);
                menuWindow.dismiss();
            }
        });

        //显示Popuwindow
        View rootview=LayoutInflater.from(EditActivity.this).inflate(R.layout.picture_source_edit_activity,null);
        //底部弹出
        menuWindow.showAtLocation(rootview, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }
    /**
     * startActivityForResult()的回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        //加Try catch 防止未选择图片直接退出。
        try {
            if(requestCode==1){
                crop_Head(Uri.fromFile(file));
            }
            else if(requestCode==0){
                Uri uri= data.getData();
                crop_Head(uri);
            }
            else if(requestCode == 2){
                bitmap_Head=data.getParcelableExtra("data");
                riv_head_img.setImageBitmap(bitmap_Head);//设置头像
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     * @param uri 图片的Uri
     */
    private void crop_Head(Uri uri){
        int dp = 500;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", dp);//输出X方向的像素
        intent.putExtra("outputY", dp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);//设置为不返回数据
        startActivityForResult(intent, 2);
    }

    @Override
    public void initControl() {
        rg_Sex=(RadioGroup)findViewById(R.id.rg_sex);
        rb_Man=(RadioButton)findViewById(R.id.rb_man);
        rb_Woman=(RadioButton)findViewById(R.id.rb_woman);
        back_bt= (Button) findViewById(R.id.back_bt);
        riv_head_img= (RoundImageView) findViewById(edit_head_img);
        edit_change_password_bt= (Button) findViewById(R.id.edit_change_password_bt);
        edit_save_bt= (Button) findViewById(R.id.edit_save_bt);
        edit_name_et= (EditText) findViewById(R.id.edit_name_et);
        edit_address_et=(EditText)findViewById(R.id.edit_address_et);
        edit_phone_et= (EditText) findViewById(R.id.edit_phone_et);
    }

    @Override
    public void addControlListener() {
        edit_save_bt.setOnClickListener(this);
        edit_change_password_bt.setOnClickListener(this);
        riv_head_img.setOnClickListener(this);
        back_bt.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_save_bt:
                save();
                break;
            case R.id.edit_change_password_bt:
                Intent changed_password_intent=new Intent(EditActivity.this,ChangepasswardActivity.class);
                startActivity(changed_password_intent);
                break;
            case R.id.back_bt:
                finish();
                break;
            case R.id.edit_head_img:
                ShowPopuWindow();
                break;
        }
    }
}
