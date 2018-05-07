package com.example.kys_31.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.kys_31.team.R;
import com.example.kys_31.team.adapter.MainServiceAdapter;
import com.example.kys_31.team.bean.Indent;
import com.example.kys_31.team.util.Init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.kys_31.team.variable.Variable.all_School;
import static com.example.kys_31.team.variable.Variable.indent_List;
import static com.example.kys_31.team.variable.Variable.methedUtil;

/**
 * @author 张同心
 * @function 服务端主界面
 * Created by kys_31 on 2017/3/8.
 */

public class MainServiceActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener ,Init{

    /*定义控件变量名称*/
    private TextView tv_User_Sum;
    private AutoCompleteTextView actv_School;
    private EditText et_Name;
    private ImageView iv_Search;
    private ListView lv_Indent;
    private RadioButton rb_Man;
    private RadioButton rb_Woman;
    private RadioButton rb_All;
    private SwipeRefreshLayout down_Refresh;

    /*定义变量名称*/
    private MainServiceAdapter adapter;

    /*标签*/
    private String TAG="MainServiceActivity";

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mainservice);
        initControl();
        addControlListener();

        if (all_School.size()==0){ initControl();}
        else {
            getIndentData();
            initSchoolData();
        }
    }

    /*初始化学校学术*/
    private void initSchoolData() {
        String[] arr=new String[all_School.size()];
        for (int i=0;i<arr.length;i++){
            arr[i]=all_School.get(i).getSchool();
        }
        ArrayAdapter adapter=new ArrayAdapter(MainServiceActivity.this,android.R.layout.simple_list_item_1,arr);
        actv_School.setAdapter(adapter);
    }

    /* 获得订单数据*/
    private void getIndentData() {
        if (methedUtil.check_Net_Available(MainServiceActivity.this)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<Indent> quert_Indent=new BmobQuery<Indent>();
                    quert_Indent.findObjects(new FindListener<Indent>() {
                        @Override
                        public void done(List<Indent> list, BmobException e) {
                            for (int i=0;i<list.size();i++){
                                HashMap<String,String> map=new HashMap<String, String>();
                                map.put("name",list.get(i).getName());
                                map.put("phonenumber",list.get(i).getPhonenumber());
                                map.put("sex",list.get(i).getSex());
                                map.put("sendtype",list.get(i).getSendtype());
                                map.put("getnumber",list.get(i).getGetnumber());
                                map.put("school",list.get(i).getSchool());
                                map.put("address",list.get(i).getAddress());
                                map.put("time",list.get(i).getCreatedAt());
                                indent_List.add(map);
                            }
                            /*显示用户详情*/
                            adapter=new MainServiceAdapter(MainServiceActivity.this,indent_List);
                            lv_Indent.post(new Runnable() {
                                @Override
                                public void run() {
                                    lv_Indent.setAdapter(adapter);
                                }
                            });
                            /*显示用户数量*/
                            tv_User_Sum.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv_User_Sum.setText(String.valueOf(indent_List.size()));
                                }
                            });
                        }
                    });
                }
            }).start();
        }
        else {methedUtil.showToast(MainServiceActivity.this,"网络不可用");}
    }

    /*性别查询*/
    private void search_Sex(String sex) {
        List<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
        for (int i =0;i<indent_List.size();i++){
            if(sex.equals(indent_List.get(i).get("sex"))){
                list.add(indent_List.get(i));
            }
        }
        lv_Indent.setAdapter(null);
        adapter=new MainServiceAdapter(MainServiceActivity.this,list);
        lv_Indent.setAdapter(adapter);
    }

    /*查询*/
    private void search() {
        String school=actv_School.getText().toString();
        String name=et_Name.getText().toString();
        List<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<indent_List.size();i++){
            if((TextUtils.isEmpty(school)||school.equals(indent_List.get(i).get("school"))) &&
                    (TextUtils.isEmpty(name)||name.equals(indent_List.get(i).get("name")))){
                list.add(indent_List.get(i));
            }
        }
        lv_Indent.setAdapter(null);//清空
        adapter=new MainServiceAdapter(MainServiceActivity.this,list);
        lv_Indent.setAdapter(adapter);
    }

    @Override
    public void initControl() {
        down_Refresh=(SwipeRefreshLayout)findViewById(R.id.down_refresh);
        tv_User_Sum=(TextView)findViewById(R.id.tv_user_Sum);
        actv_School=(AutoCompleteTextView) findViewById(R.id.actv_school);
        et_Name=(EditText)findViewById(R.id.et_name);
        iv_Search=(ImageView)findViewById(R.id.iv_search);
        lv_Indent=(ListView)findViewById(R.id.lv_indent);
        rb_All=(RadioButton)findViewById(R.id.rb_all);
        rb_Woman=(RadioButton)findViewById(R.id.rb_woman);
        rb_Man=(RadioButton)findViewById(R.id.rb_man);

        if(methedUtil.check_Net_Available(MainServiceActivity.this)){methedUtil.get_School_Data();}
        else {methedUtil.showToast(MainServiceActivity.this,"网络不可用");}
    }

    @Override
    public void addControlListener() {
        down_Refresh.setOnRefreshListener(this);
        rb_Man.setOnClickListener(this);
        rb_Woman.setOnClickListener(this);
        iv_Search.setOnClickListener(this);
        rb_All.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                rb_Man.setChecked(false);
                rb_Woman.setChecked(false);
                rb_All.setChecked(false);
                search();//查询
                break;
            case R.id.rb_man:
                search_Sex("男");
                break;
            case R.id.rb_woman:
                search_Sex("女");
                break;
            case R.id.rb_all:
                lv_Indent.setAdapter(null);
                adapter=new MainServiceAdapter(MainServiceActivity.this,indent_List);
                lv_Indent.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void onRefresh() {
        indent_List.clear();
        lv_Indent.setAdapter(null);
        getIndentData();
        rb_All.setChecked(false);
        rb_Woman.setChecked(false);
        rb_Man.setChecked(false);
        down_Refresh.setRefreshing(false);
    }
}
