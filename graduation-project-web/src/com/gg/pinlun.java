package com.gg;

public class pinlun {
    private int pinlunid;
    private String username;
    private String sendtime;
    private String comment;

    public pinlun(int pinlunid, String username, String comment, String sendtime) {
        this.pinlunid = pinlunid;
        this.username = username;
        this.sendtime = sendtime;
        this.comment = comment;
    }

    public int getPinlunid() {
        return pinlunid;
    }

    public void setPinlunid(int pinlunid) {
        this.pinlunid = pinlunid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
