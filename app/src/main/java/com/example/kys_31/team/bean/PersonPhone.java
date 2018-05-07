package com.example.kys_31.team.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author 张同心
 * Created by kys_31 on 2017/3/7.
 */

public class PersonPhone extends BmobObject {
    private String str_One_Phone,str_Two_Phone,str_Three_Phone,str_Four_Phone;

    public String getStr_One_Phone() {
        return str_One_Phone;
    }

    public String getStr_Two_Phone() {
        return str_Two_Phone;
    }

    public String getStr_Three_Phone() {
        return str_Three_Phone;
    }

    public String getStr_Four_Phone() {
        return str_Four_Phone;
    }

    public void setStr_One_Phone(String str_One_Phone) {
        this.str_One_Phone = str_One_Phone;
    }

    public void setStr_Two_Phone(String str_Two_Phone) {
        this.str_Two_Phone = str_Two_Phone;
    }

    public void setStr_Three_Phone(String str_Three_Phone) {
        this.str_Three_Phone = str_Three_Phone;
    }

    public void setStr_Four_Phone(String str_Four_Phone) {
        this.str_Four_Phone = str_Four_Phone;
    }
}
