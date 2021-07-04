package com.biyesheji.bean;

/**
 * Created by Administrator on 2020/12/3.
 */
public class SearchItem {
    int id;
    String account;
    String searchtext;

    public SearchItem(int id, String account, String searchtext) {
        this.id = id;
        this.account = account;
        this.searchtext = searchtext;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchtext() {
        return searchtext;
    }

    public void setSearchtext(String searchtext) {
        this.searchtext = searchtext;
    }
    public SearchItem(){}
    @Override
    public String toString() {
        return id +" " + searchtext  ;
    }
}
