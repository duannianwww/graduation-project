package com.biyesheji.bean;

/**
 * Created by Administrator on 2021/2/3.
 */

public class kecheng {
    private int id;
    private String bookname;
    private String author;
    private String press;
    private String pulishtime;
    private String introduct;
    private double price;
    private int stock;
    private String imagename;
    private String filename;
    private String shijuantablename;
    private String filenamepinlun;

    public kecheng(int id, String bookname, String author, String press, String pulishtime, String introduct, double price, int stock, String imagename, String filename, String shijuantablename, String filenamepinlun) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.press = press;
        this.pulishtime = pulishtime;
        this.introduct = introduct;
        this.price = price;
        this.stock = stock;
        this.imagename = imagename;
        this.filename = filename;
        this.shijuantablename = shijuantablename;
        this.filenamepinlun = filenamepinlun;
    }


    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPulishtime() {
        return pulishtime;
    }

    public void setPulishtime(String pulishtime) {
        this.pulishtime = pulishtime;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShijuantablename() {
        return shijuantablename;
    }

    public void setShijuantablename(String shijuantablename) {
        this.shijuantablename = shijuantablename;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFilenamepinlun() {
        return filenamepinlun;
    }

    public void setFilenamepinlun(String filenamepinlun) {
        this.filenamepinlun = filenamepinlun;
    }
}
