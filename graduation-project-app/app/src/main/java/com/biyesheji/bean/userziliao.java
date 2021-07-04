package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/3/4.
 */

public class userziliao {
    private int userid;
    private String nicheng;
    private String sex;
    private String intro;

    public userziliao()
    {}
    public userziliao(int userid, String nicheng,String sex, String intro) {
        this.userid = userid;
        this.nicheng = nicheng;
        this.sex = sex;
        this.intro = intro;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNicheng() {
        return nicheng;
    }

    public void setNicheng(String nicheng) {
        this.nicheng = nicheng;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
