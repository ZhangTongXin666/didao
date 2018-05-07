package com.example.kys_31.team.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author 张同心
 * @function 订单信息
 * Created by kys_31 on 2017/3/7.
 */

public class Indent extends BmobObject {
    private String name,phonenumber,account,school,address,sendtype,getnumber,sex,thingType;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAccount() {
        return account;
    }

    public String getAddress() {
        return address;
    }

    public String getGetnumber() {
        return getnumber;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGetnumber(String getnumber) {
        this.getnumber = getnumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public void setThingType(String thingType){
        this.thingType=thingType;
    }

    public String getThingType() {
        return thingType;
    }
}
