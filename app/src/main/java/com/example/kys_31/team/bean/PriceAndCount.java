package com.example.kys_31.team.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author 张同心
 * @function 价格、使用次数、版本、学校。
 * Created by kys_31 on 2017/3/10.
 */

public class PriceAndCount extends BmobObject {
    private String price,count,school;

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }
}
