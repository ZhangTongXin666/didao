package com.example.kys_31.team.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kys_31.team.R;
import com.example.kys_31.team.util.Init;
import com.example.kys_31.team.variable.Variable;

import java.util.HashMap;
import java.util.List;

/**
 * @author 张同心
 * Created by kys_31 on 2017/4/1.
 */

public class SystemMessageActivity extends AppCompatActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,Init{

    private ListView lvSystemMessage=null;
    private ImageView ivBace;
    private TextView tvBack;
    private List<HashMap<String,String>> listSystemMessage=null;
    private SwipeRefreshLayout downRefresh=null;

    //标签
    private String TAG="SystemMessageActivity";

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){  getWindow().setStatusBarColor(getResources().getColor(R.color.systemmessage,null));  }
        setContentView(R.layout.activity_systemmessage);
        Variable.methedUtil.hideTitle(SystemMessageActivity.this);

        initControl();
        addControlListener();
        showSystemMessage();

    }

    private void showSystemMessage() {
        listSystemMessage= Variable.database.query_DataTo_SystemMessage();
        SystemMessageAdapter adapter=new SystemMessageAdapter(SystemMessageActivity.this);
        lvSystemMessage.setAdapter(adapter);
    }

    class SystemMessageAdapter extends BaseAdapter{
            private Context context;
        public SystemMessageAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return listSystemMessage.size();
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
            ViewHolder viewHolder = null;
            if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(context);
                convertView=inflater.inflate(R.layout.adapter_systemmessage_item,null);
                viewHolder=new ViewHolder();
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.tvPosition=(TextView)convertView.findViewById(R.id.tv_position);
            viewHolder.tvMessage=(TextView)convertView.findViewById(R.id.tv_content);
            viewHolder.lookMessage=(LinearLayout)convertView.findViewById(R.id.ll_lookMessage);
            viewHolder.deleteMessage=(LinearLayout)convertView.findViewById(R.id.ll_delete);
            viewHolder.deleteMessage.setTag(position);
            viewHolder.lookMessage.setTag(position);

            viewHolder.tvMessage.setText(String.valueOf(position+1)+"."+listSystemMessage.get(position).get("content"));

            viewHolder.lookMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=(int)v.getTag();
                    Intent intent=new Intent(SystemMessageActivity.this,DetailedMessageActivity.class);
                    intent.putExtra("content",listSystemMessage.get(position).get("content"));
                    intent.putExtra("time",listSystemMessage.get(position).get("time"));
                    startActivity(intent);
                }
            });
            viewHolder.deleteMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int position=(int)v.getTag();
                    Variable.database.delete_DataTo_SystemMessage(listSystemMessage.get(position).get("time"));
                    listSystemMessage=Variable.database.query_DataTo_SystemMessage();
                    notifyDataSetInvalidated();
                }
            });
            return convertView;
        }

        class ViewHolder{
            TextView tvPosition;
            TextView tvMessage;
            LinearLayout lookMessage;
            LinearLayout deleteMessage;
        }
    }

    @Override
    public void initControl() {
        downRefresh=(SwipeRefreshLayout)findViewById(R.id.sr_downRefresh);
        lvSystemMessage=(ListView)findViewById(R.id.lv_systemMessage);
        ivBace=(ImageView)findViewById(R.id.iv_back);
        tvBack=(TextView)findViewById(R.id.tv_back);
    }

    @Override
    public void addControlListener() {
        ivBace.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        downRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
    @Override
    public void onRefresh() {
        listSystemMessage.clear();
        showSystemMessage();
        downRefresh.setRefreshing(false);
    }
}
