package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/3/4.
 */

public class usercourse {
    private int courseid;
    private String usercoursename;

    public usercourse(int courseid, String usercoursename) {
        this.courseid = courseid;
        this.usercoursename = usercoursename;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getUsercoursename() {
        return usercoursename;
    }

    public void setUsercoursename(String usercoursename) {
        this.usercoursename = usercoursename;
    }
}
