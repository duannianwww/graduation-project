package com.gg;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userlogin {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/kechengsheji?Unicode=true&characterEncoding=UTF8";
    static final String USER = "root";
    static final String PASS = "123456";
    public boolean isuser (String id,String password)
    {
        boolean userlog =false;
        String sql="SELECT useraccout, userpassword FROM userlogin WHERE useraccout='"+id+"' and userpassword='"+password+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                userlog  = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        if(userlog)
        {
            return true;
        }
        else return false;
    }
    public boolean add(String name, String author, String press, String publishtime,String usertyblename,String uservideotablename,String usergerentablename,String userpasswordsafe) throws FileNotFoundException {//��
        boolean userlog =false;
        String sql="SELECT * FROM userlogin WHERE useraccout='"+name+"'";

        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()) {
                sql="create table `"+usertyblename+"` ( " +     //个人课程表
                        "  `coursevideoid` int(20) NOT NULL AUTO_INCREMENT COMMENT '课程id'," +
                        "  `usercoursename` char(255) NOT NULL DEFAULT '课程名'," +
                        "  PRIMARY KEY (`coursevideoid`))" +
                        " ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;";
                stmt.execute(sql);
                sql="create table `"+usergerentablename+"` ( " +              //个人资料表
                        "  `userid` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id'," +
                        "  `nicheng` char(255) NOT NULL DEFAULT '昵称'," +
                        "  `sex` char(255) NOT NULL DEFAULT '性别'," +
                        "  `intro` char(255) NOT NULL DEFAULT '签名'," +
                        "  PRIMARY KEY (`userid`))" +
                        " ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;";
                stmt.execute(sql);
                sql="create table `"+uservideotablename+"` ( " +              //播放记录表
                        "  `videoid` int(20) NOT NULL AUTO_INCREMENT COMMENT '视频id'," +
                        "  `title` char(255) NOT NULL DEFAULT '课程名'," +
                        "  `videopath` char(255) NOT NULL DEFAULT '视频流'," +
                        "  `videoname` char(255) NOT NULL DEFAULT '视频名'," +
                        "  PRIMARY KEY (`videoid`))" +
                        " ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;";
                stmt.execute(sql);
                sql = "INSERT INTO userlogin(username,useraccout,userpassword,userpasswordsafe,usertybe,usertablename,uservideotablename,usergerentablename) values('"+name+"','"+author+"','"+press+"','"+userpasswordsafe+"','"+publishtime+"','"+usertyblename+"','"+uservideotablename+"','"+usergerentablename+"')";
                stmt.execute(sql);
                userlog  = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return userlog;
    }
    public boolean delete(String name)//删
    {
        boolean userlog = false;
        String sql = "SELECT * FROM userlogin WHERE username='" + name + "'";
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sql = "delete from userlogin where username='" + name + "'";
                stmt.execute(sql);
                userlog = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userlog;
    }
    public List<user> select() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//����������
        List<user> list =new ArrayList<user>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT * FROM userlogin ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString("username");
                String useraccout = rs.getString("useraccout");
                String userpassword = rs.getString("userpassword");
                String usertype = rs.getString("usertybe");
                String usertablename=rs.getString("usertablename");
                String uservideotablename=rs.getString("uservideotablename");
                String usergerentablename=rs.getString("usergerentablename");
                String userpasswordsafe=rs.getString("userpasswordsafe");
                user user1=new user(username,useraccout,userpassword,usertype,usertablename,uservideotablename,usergerentablename,userpasswordsafe);
                list.add(user1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public boolean userre (String name,String accout,String password)
    {
        boolean userlog =false;
        String sql="SELECT * FROM userlogin WHERE useraccout='"+accout+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()){
                sql="INSERT INTO userlogin value('"+name+"','"+accout+"','"+password+"','user')";
                stmt.execute(sql);
                userlog  = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        if(userlog)
        {
            return true;
        }
        else return false;
    }
    public boolean update(String name, String accout, String password, String usertype)//改
    {
        boolean userlog =false;
        String sql="update userlogin set username='"+name+"',useraccout='"+accout+"',userpassword='"+password+"',usertybe='"+usertype+"' WHERE username='"+name+"'";

        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            userlog =true;
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return userlog;
    }

    public boolean sea22(String name)//根据名字查找书
    {
        boolean userlog =false;
        String sql="SELECT * FROM userlogin WHERE username='"+name+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                userlog  = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return userlog;
    }
    public List sea2(String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找并修改
        List<user> list =new ArrayList<user>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT * FROM userlogin WHERE username='"+name+"'";
            Statement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String username = rs.getString("username");
                String useraccout = rs.getString("useraccout");
                String userpassword = rs.getString("userpassword");
                String usertype = rs.getString("usertybe");
                String usertablename=rs.getString("usertablename");
                String uservideotablename=rs.getString("uservideotablename");
                String usergerentablename=rs.getString("usergerentablename");
                String userpasswordsafe=rs.getString("userpasswordsafe");
                user book1=new user(username,useraccout,userpassword,usertype,usertablename,uservideotablename,usergerentablename,userpasswordsafe);
                list.add(book1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
