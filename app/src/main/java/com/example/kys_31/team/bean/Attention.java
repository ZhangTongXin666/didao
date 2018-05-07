package com.example.kys_31.team.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author 张同心
 * Created by kys_31 on 2017/3/30.
 */

public class Attention extends BmobObject{

    public String textContent;
    private String strUri;

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setStrUri(String strUri) {
        this.strUri = strUri;
    }

    public String getStrUri() {
        return strUri;
    }
}
