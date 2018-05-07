package com.example.kys_31.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kys_31.team.R;

import java.util.List;

/**
 * @author  张同心
 * @function 服务界面Popupwindow中ListView适配器
 * Created by kys_31 on 2017/3/6.
 */

public class ServicePopAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;


    /*构造方法*/
    public ServicePopAdapter(Context context,List<String> list){
        inflater=LayoutInflater.from(context);
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
        if (convertView==null){
            control=new Control();
            convertView=inflater.inflate(R.layout.popupwindow_sendtype_item_service_view,null);
            convertView.setTag(control);
        }
        else {control=(Control)convertView.getTag();}
        control.tv_Typename=(TextView)convertView.findViewById(R.id.tv_typename);
        control.tv_Typename.setText(list.get(position));
        return convertView;
    }

    static class  Control{
        public TextView tv_Typename=null;
    }

}
