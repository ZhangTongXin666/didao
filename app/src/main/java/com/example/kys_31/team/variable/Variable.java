package com.example.kys_31.team.variable;

import android.graphics.Bitmap;

import com.example.kys_31.team.bean.School;
import com.example.kys_31.team.database.Sqldatabase;
import com.example.kys_31.team.util.MethedUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 张同心
 * @function 变量
 * Created by kys_31 on 2017/2/28.
 */

public class Variable {

    public static MethedUtil methedUtil=new MethedUtil();
    public static Sqldatabase database=null;
    public static List<HashMap<String,String>> user_Important_Message_List =null;
    public static Bitmap head=null;
    /*定义变量名称*/
    public static String str_Account,str_Password,str_Name,str_address,str_School,str_Sex="男";
    public static String str_Head=" ";
    public static String str_Phonenumber=" ";
    public static List<String> sendtype_List=new ArrayList<String>();
    public static boolean if_Show_Floatwindow=false;
    public static List<HashMap<String,String>> indent_List=new ArrayList<HashMap<String, String>>();
    public static List<School> all_School=new ArrayList<>();
    public static String notificationMessage=null;
}
