package com.example.kys_31.team.bean;

import cn.bmob.v3.BmobUser;

/**
 * @author 杨贺
 * Created by kys_7 on 2017/3/3.
 */

public class MyUser extends BmobUser{
    private String true_name;
    private String phone;
    private String head;
    private String address;
    private String school;
    private String sex;
    private String count;

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
