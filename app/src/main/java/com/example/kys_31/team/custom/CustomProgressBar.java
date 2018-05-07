package com.example.kys_31.team.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kys_31.team.R;

/**
 * @author 张同心
 * @function 自定义ProgressBar
 * Created by kys_31 on 2017/4/12.
 */

public class CustomProgressBar {

    private ProgressBar bar;
    private Dialog dialog;
    private Context context;
    private String strHiit;

    public CustomProgressBar(Context context,String strHiit){
        this.context=context;
        this.strHiit=strHiit;
    }
    /**
     * 显示进度条
     */
    public void start() {
        LayoutInflater inflater=LayoutInflater.from(context);
        View progressBarView=inflater.inflate(R.layout.dialog_progressbar_view,null);
        dialog=new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setContentView(progressBarView);
        bar=(ProgressBar)progressBarView.findViewById(R.id.pb_circle);
        TextView tvLogin=(TextView)progressBarView.findViewById(R.id.tv_operation);
        tvLogin.setText(strHiit);
        bar.setVisibility(View.VISIBLE);
    }
    public void stop(){
        dialog.dismiss();
        bar.setVisibility(View.GONE);
    }
}
