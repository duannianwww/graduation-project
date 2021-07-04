package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/2/28.
 */

public class user {
    private String username;
    private String useraccout;
    private String userpassword;
    private String userpasswordsafe;//用户密保
    private String usertybe;
    private String usertablename;
    private String uservideotablename;
    private String usergerentablename;

    public user(String username, String useraccout, String userpassword,String usertybe, String usertablename, String uservideotablename, String usergerentablename,String userpasswordsafe) {
        this.username = username;
        this.useraccout = useraccout;
        this.userpassword = userpassword;
        this.userpasswordsafe = userpasswordsafe;
        this.usertybe = usertybe;
        this.usertablename = usertablename;
        this.uservideotablename = uservideotablename;
        this.usergerentablename = usergerentablename;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseraccout() {
        return useraccout;
    }

    public void setUseraccout(String useraccout) {
        this.useraccout = useraccout;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsertybe() {
        return usertybe;
    }

    public void setUsertybe(String usertybe) {
        this.usertybe = usertybe;
    }

    public String getUsertablename() {
        return usertablename;
    }

    public void setUsertablename(String usertablename) {
        this.usertablename = usertablename;
    }

    public String getUservideotablename() {
        return uservideotablename;
    }

    public void setUservideotablename(String uservideotablename) {
        this.uservideotablename = uservideotablename;
    }

    public String getUsergerentablename() {
        return usergerentablename;
    }

    public void setUsergerentablename(String usergerentablename) {
        this.usergerentablename = usergerentablename;
    }

    public String getUserpasswordsafe() {
        return userpasswordsafe;
    }

    public void setUserpasswordsafe(String userpasswordsafe) {
        this.userpasswordsafe = userpasswordsafe;
    }
}
