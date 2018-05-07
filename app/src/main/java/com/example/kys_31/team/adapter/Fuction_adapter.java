package com.example.kys_31.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys_31.team.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 张同心
 * Created by lenovo on 2017/2/16.
 */

public class Fuction_adapter extends BaseAdapter {
    //得到一个LayoutInflater对象来引入布局
    private LayoutInflater mInflater;
    private List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
    private List<Map<String,Object>> data1=new ArrayList<Map<String,Object>>();
    private int[] img=new int[]{R.drawable.person,R.drawable.customer_service,R.mipmap.icon_systemmessage,R.mipmap.message_green};
    private String[] text=new String[]{"个人信息","联系客服","系统消息","订单系统"};
    private int count=0;


    public Fuction_adapter(Context context) {
            count=img.length;
        //获得将要绑定到Data中的数据
        data=getData();
        data1=getData1();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return count;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_function_item_view, null);
            holder = new ViewHolder();
            //得到各个控件
            holder.edit_iv = (ImageView) convertView.findViewById(R.id.edit_iv);
            holder.edit_tv = (TextView) convertView.findViewById(R.id.edit_tv);

            convertView.setTag(holder);//绑定ViewHolder的对象
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.edit_iv.setBackgroundResource((Integer) data.get(position).get("img"));
        holder.edit_tv.setText((String) data1.get(position).get("text"));
        return convertView;
    }
    //设置静态变量ViewHolder
    static class ViewHolder {
        public ImageView edit_iv;
        public TextView edit_tv;
    }

    /**
     * 添加img数据
     * @return
     */
    public List<Map<String,Object>> getData() {
        List<Map<String,Object>> list_img=new ArrayList<Map<String, Object>>();
        for (int i=0;i<count;i++){
            Map<String,Object> map_img=new HashMap<String, Object>();
            map_img.put("img",img[i]);
            list_img.add(map_img);
        }
        return list_img;
    }

    /**
     * 添加text数据
     * @return
     */
    public List<Map<String,Object>> getData1(){
        List<Map<String,Object>> list_text=new ArrayList<Map<String, Object>>();
        for (int j=0;j<count;j++){
            Map<String,Object> map_text=new HashMap<String, Object>();
            map_text.put("text",text[j]);
            list_text.add(map_text);
        }
        return list_text;
    }
}
