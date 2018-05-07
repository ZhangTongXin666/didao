package com.example.kys_31.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kys_31.team.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kys_31 on 2017/3/8.
 */

public class MainServiceAdapter extends BaseAdapter {

    /*定义变量名称*/
    private Context context=null;
    private List<HashMap<String,String>> list=null;

    public MainServiceAdapter(Context context,List<HashMap<String,String>> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Control control=null;
        if(convertView==null){
            LayoutInflater inflater =LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.mainservice_listview_item_view,null);
            control=new Control();
            convertView.setTag(control);
        }
        else {control=(Control)convertView.getTag();}
        /*初始化控件*/
        control.tv_Position=(TextView)convertView.findViewById(R.id.tv_position);
        control.tv_Name=(TextView)convertView.findViewById(R.id.tv_name);
        control.tv_Phonenumber=(TextView)convertView.findViewById(R.id.tv_phonenumber);
        control.tv_School=(TextView)convertView.findViewById(R.id.tv_school);
        control.tv_Address=(TextView)convertView.findViewById(R.id.tv_address);
        control.tv_Sendtype=(TextView)convertView.findViewById(R.id.tv_sendtype);
        control.tv_Getnumber=(TextView)convertView.findViewById(R.id.tv_getnumber);
        control.tv_Time=(TextView)convertView.findViewById(R.id.tv_date);
        control.tv_Sex=(TextView)convertView.findViewById(R.id.tv_sex);
        /*添加数据*/
        control.tv_Position.setText((position+1)+"");
        control.tv_Name.setText(list.get(position).get("name"));
        control.tv_Phonenumber.setText(list.get(position).get("phonenumber"));
        control.tv_Sendtype.setText(list.get(position).get("sendtype"));
        control.tv_Getnumber.setText(list.get(position).get("getnumber"));
        control.tv_School.setText(list.get(position).get("school"));
        control.tv_Address.setText(list.get(position).get("address"));
        control.tv_Time.setText(list.get(position).get("time"));
        control.tv_Sex.setText(list.get(position).get("sex"));
        return convertView;
    }

    static  class Control{
        public TextView tv_Position=null;
        public TextView tv_Name=null;
        public TextView tv_Phonenumber=null;
        public TextView tv_Sendtype=null;
        public TextView tv_Getnumber=null;
        public TextView tv_School=null;
        public TextView tv_Address=null;
        public TextView tv_Time=null;
        public TextView tv_Sex=null;
    }
}
