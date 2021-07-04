package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/3/4.
 */

public class uservideo {
    private int videoid;
    private String title;
    private String videopath;
    private String videoname;

    public uservideo()
    {}
    public uservideo(int videoid, String title, String videopath, String videoname) {
        this.videoid = videoid;
        this.title = title;
        this.videopath = videopath;
        this.videoname = videoname;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }
}
