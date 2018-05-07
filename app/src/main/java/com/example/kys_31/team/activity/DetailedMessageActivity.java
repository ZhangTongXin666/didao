package com.example.kys_31.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kys_31.team.R;

/**
 * @author 张同心
 * @function 详细信息
 * Created by kys_31 on 2017/4/1.
 */

public class DetailedMessageActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvDetailMessage;
    private TextView tvTime;
    private TextView tvUrl;
    private ImageView ivBack;
    private TextView tvBack;



    private String strTime;
    private String strContent;
    //标签
    private String TAG="DetailedMessage";
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(Double.valueOf(android.os.Build.VERSION.RELEASE.substring(0,3))>=6.0){
            getWindow().setStatusBarColor(getResources().getColor(R.color.skyblue));
        }
        setContentView(R.layout.activity_detailedmessage);
        ActionBar bar=getSupportActionBar();
        bar.hide();
        Intent intent=getIntent();
        strContent=intent.getStringExtra("content");
       strTime=intent.getStringExtra("time");

        initControl();

    }

    private void initControl() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvBack=(TextView)findViewById(R.id.tv_back);
        tvDetailMessage=(TextView)findViewById(R.id.tv_detailMessage);
        tvTime=(TextView)findViewById(R.id.tv_time);
        tvUrl=(TextView)findViewById(R.id.tv_url);

        ivBack.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvUrl.setOnClickListener(this);
        Log.i("Detailed",strContent.substring(strContent.indexOf("URL")+3,strContent.length()));

        tvTime.setText("接收时间："+strTime);
        if(strContent.contains("URL")){
            tvDetailMessage.setText(strContent.substring(0,strContent.indexOf("URL")));
            tvUrl.setText(standardUrl(strContent.substring(strContent.indexOf("URL")+3,strContent.length())));
        }
        else {
            tvDetailMessage.setText(strContent);
            tvUrl.setVisibility(View.GONE);
        }


    }
private String standardUrl(String url){
    StringBuilder sb=new StringBuilder();
    String strUrl=url;
    while (strUrl.contains("\\")){
        sb.append(strUrl.substring(0,strUrl.indexOf("\\")));
        Log.i("Detailed",sb.toString());
        strUrl=strUrl.substring(strUrl.indexOf("\\")+1,strUrl.length());
    }
    sb.append(strUrl);
    return sb.toString();
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
            case R.id.tv_url:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(tvUrl.getText().toString()));
                startActivity(intent);
        }
    }
}
