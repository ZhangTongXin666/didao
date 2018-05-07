package com.example.kys_31.team.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys_31.team.R;
import com.example.kys_31.team.activity.MainActivity;
import com.example.kys_31.team.custom.RoundImageView;
import com.example.kys_31.team.variable.Variable;

import java.util.HashMap;
import java.util.List;

import static com.example.kys_31.team.variable.Variable.user_Important_Message_List;

/**
 * @author 张同心
 * @function 登录界面Popupwindow中ListView适配器
 * Created by kys_31 on 2017/2/28.
 */

public class MainPopAdapter extends BaseAdapter {

    /*定义变量名称*/
    private Context context=null;
    private List<HashMap<String,String>> list=null;

    /*标签*/
    private String TAG="popupwindowadapter";

    public MainPopAdapter(Context context, List<HashMap<String,String>> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Control control=null;
        if(convertView==null){
            LayoutInflater inflater =LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.popupwindow_quest_login_activity,null);
            control=new Control();
            convertView.setTag(control);
        }
        else {control=(Control)convertView.getTag();}
        /*初始化控件*/
        control.iv_Delete=(ImageView)convertView.findViewById(R.id.iv_delete);
        control.riv_Head=(RoundImageView)convertView.findViewById(R.id.riv_head);
        control.tv_Account=(TextView)convertView.findViewById(R.id.tv_account);
        control.tv_Account.setText(list.get(position).get("account"));
        control.riv_Head.setImageBitmap(Variable.methedUtil.covertStringToIcon(list.get(position).get("head")));

        control.iv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.database.delete_Data(user_Important_Message_List.get(position).get("account"));
                Log.i(TAG,"查看账号："+user_Important_Message_List.get(position).get("account")+" 角标"+position);

                user_Important_Message_List.remove(position);
                MainPopAdapter adapter=new MainPopAdapter(context, user_Important_Message_List);
                MainActivity.lv_User_List.setAdapter(adapter);
            }
        });
        return convertView;
    }

    static class Control{
        public RoundImageView riv_Head=null;
        public TextView tv_Account =null;
        public ImageView iv_Delete=null;
    }

}
