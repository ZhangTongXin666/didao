package com.example.kys_31.team.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author 张同心
 * @funtion 动态添加学校
 * Created by kys_31 on 2017/3/9.
 */

public class School extends BmobObject {
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
