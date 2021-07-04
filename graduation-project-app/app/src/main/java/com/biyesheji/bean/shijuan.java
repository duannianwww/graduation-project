package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/3/2.
 */

public class shijuan {
    int shijuanid;
    String shiuanname;

    public shijuan()
    {}
    public shijuan(int shijuanid, String shiuanname) {
        this.shijuanid = shijuanid;
        this.shiuanname = shiuanname;
    }

    public int getShijuanid() {
        return shijuanid;
    }

    public void setShijuanid(int shijuanid) {
        this.shijuanid = shijuanid;
    }

    public String getShiuanname() {
        return shiuanname;
    }

    public void setShiuanname(String shiuanname) {
        this.shiuanname = shiuanname;
    }
}
