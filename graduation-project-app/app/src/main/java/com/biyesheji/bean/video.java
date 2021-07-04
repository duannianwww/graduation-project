package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/2/28.
 */

public class video {

    private int id;
    private String videoname;
    private String desc;

    public video()
    {}

    public video(int id, String videoname, String desc) {
        this.id = id;
        this.videoname = videoname;
        this.desc = desc;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
