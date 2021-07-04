package com.biyesheji.SQL;

import android.util.Log;

import com.biyesheji.bean.user;
import com.biyesheji.bean.uservideo;
import com.biyesheji.bean.userziliao;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/2/28.
 */

public class UserSQL {

    static final String ip="10.0.2.2";
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://"+ip+":3306/kechengsheji?useUnicode=true&characterEncoding=UTF8&connectTimeout=3000&socketTimeout=60000";
    static final String USER = "root";
    static final String PASS = "123456";
    static boolean flag=false;
    static boolean flag2=false;
    Connection conn = null;
    java.sql.Statement stmt = null;
    public static void callback()
    {
        System.out.println("子线程执行结束");
        flag=true;
    }
    public static void istrue()
    {
        flag2=true;
    }
    class MyThreadisuser extends Thread //子线程查询数据库
    {
        private boolean cc;
        private String id;
        private String password;
        public MyThreadisuser(boolean c,String useraccount,String userpassword)
        {
            this.cc=c;
            this.id=useraccount;
            this.password=userpassword;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                String sql="SELECT useraccout, userpassword FROM userlogin WHERE useraccout='"+id+"' and userpassword='"+password+"'";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()){
                    cc  = true;
                }
                rs.close();
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            Log.i("cc",cc+"");
            callback();
        }
    }
    public boolean isuser (String id,String password) throws ClassNotFoundException, IllegalAccessException, InstantiationException//根据账号密码查找数据库
    {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadisuser(userlog,id,password);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        Log.i("userlog",userlog+"");
        return userlog;
    }
    class MyThreadadduser extends Thread //子线程添加数据库用户
    {
        private boolean cc;
        private String username;
        private String id;
        private String password;
        private String usertybe;
        private String usertyblename;
        private String uservideotablename;
        private String usergerentablename;
        private String userpasswordsafe;

        public MyThreadadduser(boolean c,String name, String author, String press, String publishtime,String usertyblename,String uservideotablename,String usergerentablename,String userpasswordsafe)
        {
            this.cc=c;
            this.username=name;
            this.id=author;
            this.password=press;
            this.usertybe=publishtime;
            this.usertyblename=usertyblename;
            this.uservideotablename=uservideotablename;
            this.usergerentablename=usergerentablename;
            this.userpasswordsafe=userpasswordsafe;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                String sql = "SELECT * FROM userlogin WHERE useraccout='" + id + "'";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                if (!rs.next()) {
                    sql = "create table `" + usertyblename + "` ( " +     //个人课程表
                            "  `coursevideoid` int(20) NOT NULL AUTO_INCREMENT COMMENT '课程id'," +
                            "  `usercoursename` char(255) NOT NULL DEFAULT '课程名'," +
                            "  PRIMARY KEY (`coursevideoid`))" +
                            " ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;";
                    stmt.execute(sql);
                    sql = "create table `" + usergerentablename + "` ( " +              //个人资料表
                            "  `userid` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id'," +
                            "  `nicheng` char(255) NOT NULL DEFAULT '昵称'," +
                            "  `sex` char(255) NOT NULL DEFAULT '性别'," +
                            "  `intro` char(255) NOT NULL DEFAULT '签名'," +
                            "  PRIMARY KEY (`userid`))" +
                            " ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;";
                    stmt.execute(sql);
                    sql = "create table `" + uservideotablename + "` ( " +              //播放记录表
                            "  `videoid` int(20) NOT NULL AUTO_INCREMENT COMMENT '视频id'," +
                            "  `title` char(255) NOT NULL DEFAULT '课程名'," +
                            "  `videopath` char(255) NOT NULL DEFAULT '视频流'," +
                            "  `videoname` char(255) NOT NULL DEFAULT '视频名'," +
                            "  PRIMARY KEY (`videoid`))" +
                            " ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;";
                    stmt.execute(sql);
                    sql = "INSERT INTO userlogin(username,useraccout,userpassword,userpasswordsafe,usertybe,usertablename,uservideotablename,usergerentablename) values('" + username + "','" + id + "','" + password + "','" + userpasswordsafe + "','" + usertybe + "','" + usertyblename + "','" + uservideotablename + "','" + usergerentablename + "')";
                    stmt.execute(sql);
                    cc = true;
                }
                rs.close();
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean add(String name, String author, String press, String publishtime,String usertyblename,String uservideotablename,String usergerentablename,String userpasswordsafe) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {//��
        boolean userlog = false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadadduser(userlog,name,author,press,publishtime,usertyblename,uservideotablename,usergerentablename,userpasswordsafe);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        Log.i("userlog",userlog+"");
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

    class MyThreadseauserziliao extends Thread //子线程查询数据库用户全部资料
    {
        private List<userziliao> cc;
        private String userziliaotable;
        public MyThreadseauserziliao(List<userziliao> c,String userziliaotable)
        {
            this.cc=c;
            this.userziliaotable=userziliaotable;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try
            {
                String sql= "SELECT * FROM `"+userziliaotable+"`";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int userid=rs.getInt("userid");
                    String nicheng = rs.getString("nicheng");
                    String sex = rs.getString("sex");
                    String intro = rs.getString("intro");
                    userziliao user1=new userziliao(userid,nicheng,sex,intro);
                    cc.add(user1);
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
            callback();
        }
    }
    public List seauserziliao(String userziliaotable) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        List<userziliao> userziliaoList=new ArrayList<userziliao>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadseauserziliao(userziliaoList,userziliaotable);
        chaxun.start();
        while (!flag);
        return userziliaoList;
    }
    class MyThreadadduserziliao extends Thread //子线程添加数据库用户资料
    {
        private boolean cc;
        private String userziliaotable;
        private String nicheng;
        private String sex;
        private String intro;

        public MyThreadadduserziliao(boolean c, String userziliaotable, String nicheng, String sex,String intro)
        {
            this.cc=c;
            this.userziliaotable=userziliaotable;
            this.nicheng=nicheng;
            this.sex=sex;
            this.intro=intro;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                stmt = conn.createStatement();
                String sql = "INSERT INTO `"+userziliaotable+"`(nicheng,sex,intro) values('" + nicheng + "','" + sex + "','" + intro + "')";
                stmt.execute(sql);
                cc = true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }

    public boolean adduserziliao(String userziliaotable,String nicheng,String sex,String intro) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadadduserziliao(userlog,userziliaotable,nicheng,sex,intro);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    class MyThreadupdateuserintro extends Thread //子线程查询数据库用户简介
    {
        private boolean cc;
        private String userziliaotable;
        private String intro;

        public MyThreadupdateuserintro(boolean c, String userziliaotable,String intro)
        {
            this.cc=c;
            this.userziliaotable=userziliaotable;
            this.intro=intro;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                stmt = conn.createStatement();
                String sql = "update `"+userziliaotable+"` set intro='"+intro+"'";
                stmt.execute(sql);
                cc = true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean updateintro(String userziliaotable,String intro) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadupdateuserintro(userlog,userziliaotable,intro);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    class MyThreadupdateusernickname extends Thread //子线程查询数据库用户昵称
    {
        private boolean cc;
        private String userziliaotable;
        private String nickname;

        public MyThreadupdateusernickname(boolean c, String userziliaotable,String nickname)
        {
            this.cc=c;
            this.userziliaotable=userziliaotable;
            this.nickname=nickname;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                stmt = conn.createStatement();
                String sql = "update `"+userziliaotable+"` set nicheng='"+nickname+"'";
                stmt.execute(sql);
                cc = true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean updatenickname(String userziliaotable,String nickname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadupdateusernickname(userlog,userziliaotable,nickname);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    class MyThreadupdateusersex extends Thread //子线程查询数据库用户性别
    {
        private boolean cc;
        private String userziliaotable;
        private String sex;

        public MyThreadupdateusersex(boolean c, String userziliaotable,String sex)
        {
            this.cc=c;
            this.userziliaotable=userziliaotable;
            this.sex=sex;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                stmt = conn.createStatement();
                String sql = "update `"+userziliaotable+"` set sex='"+sex+"'";
                stmt.execute(sql);
                cc = true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean updatesex(String userziliaotable,String sex) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadupdateusersex(userlog,userziliaotable,sex);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    public boolean userre (String name,String accout,String password)//管理员添加用户
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
    class MyThreadupdatepassword extends Thread //子线程查询数据库用户密码
    {
        private boolean cc;
        private String id;
        private String password;
        public MyThreadupdatepassword(boolean c,String useraccount,String userpassword)
        {
            this.cc=c;
            this.id=useraccount;
            this.password=userpassword;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                String sql="update userlogin set userpassword='"+password+"' WHERE useraccout='"+id+"'";
                stmt = conn.prepareStatement(sql);
                stmt.execute(sql);
                cc=true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.i("cc2",cc+"");
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean updatepassword(String useraccout,String userpassword) throws ClassNotFoundException, IllegalAccessException, InstantiationException//修改密码
    {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadupdatepassword(userlog,useraccout,userpassword);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    class MyThreadupdatesafe extends Thread //子线程查询数据库用户密保
    {
        private boolean cc;
        private String id;
        private String passwordsafe;
        public MyThreadupdatesafe(boolean c,String useraccount,String userpasswordsafe)
        {
            this.cc=c;
            this.id=useraccount;
            this.passwordsafe=userpasswordsafe;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                String sql="update userlogin set userpasswordsafe='"+passwordsafe+"' WHERE useraccout='"+id+"'";
                stmt = conn.prepareStatement(sql);
                stmt.execute(sql);
                cc=true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.i("cc2",cc+"");
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean updatesafe(String useraccout,String userpasswordsafe) throws ClassNotFoundException, IllegalAccessException, InstantiationException//修改密保
    {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadupdatesafe(userlog,useraccout,userpasswordsafe);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
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
    class MyThreadseauser extends Thread //子线程查询数据库用户信息
    {
        private boolean cc;
        private String id;
        public MyThreadseauser(boolean c,String useraccount)
        {
            this.cc=c;
            this.id=useraccount;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                String sql="SELECT * FROM userlogin WHERE useraccout='"+id+"'";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()){
                    cc  = true;
                }
                rs.close();
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.i("cc2",cc+"");
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }


    public boolean sea22(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException//根据账号查找用户，返回布尔值
    {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadseauser(userlog,name);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        Log.i("userlog2",userlog+"");
        return userlog;
    }
    class MyThreadseausercourse extends Thread //子线程查询数据库全部课程
    {
        private List<String> cc;
        private String usercoursetablename;
        public MyThreadseausercourse(List<String> c,String usercoursetablename)
        {
            this.cc=c;
            this.usercoursetablename=usercoursetablename;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try
            {
                String sql= "SELECT * FROM `"+usercoursetablename+"`";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String usercoursename = rs.getString("usercoursename");
                    cc.add(usercoursename);
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
            callback();
        }
    }
    public List<String> seausercourse(String usercoursetablename) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找并修改
        flag=false;
        final List<String> list=new ArrayList<String>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadseausercourse(list,usercoursetablename);
        chaxun.start();
        while (!flag);
        return list;
    }
    class MyThreadaddusercouse extends Thread //子线程添加数据库用户课程
    {
        private boolean cc;
        private String usercoursetablename;
        private String coursename;

        public MyThreadaddusercouse(boolean c, String usercoursetablename, String coursename)
        {
            this.cc=c;
            this.usercoursetablename=usercoursetablename;
            this.coursename=coursename;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                stmt = conn.createStatement();
                String sql = "INSERT INTO `"+usercoursetablename+"`(usercoursename) values('" + coursename + "')";
                stmt.execute(sql);
                cc = true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean addusercourse(String usercoursetablename,String coursename) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadaddusercouse(userlog,usercoursetablename,coursename);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    class MyThreadadduservideo extends Thread //子线程添加数据库用户播放记录
    {
        private boolean cc;
        private String uservideotablename;
        private String title;
        private String videopath;
        private String videoname;

        public MyThreadadduservideo(boolean c, String uservideotablename,String title,String videopath, String videoname)
        {
            this.cc=c;
            this.uservideotablename=uservideotablename;
            this.title=title;
            this.videopath=videopath;
            this.videoname=videoname;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                stmt = conn.createStatement();
                String sql = "INSERT INTO `"+uservideotablename+"`(title,videopath,videoname) values('" + title + "','" + videopath + "','" + videoname + "')";
                stmt.execute(sql);
                cc = true;
                stmt.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cc)
            {
                istrue();
            }
            callback();
        }
    }
    public boolean adduservideo(String uservideotablename,String title,String videopath,String videoname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadadduservideo(userlog,uservideotablename,title,videopath,videoname);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    class MyThreadseauservideo extends Thread //子线程查询数据库用户全部播放记录
    {
        private List<uservideo> cc;
        private String uservideotablename;
        public MyThreadseauservideo(List<uservideo> c,String uservideotablename)
        {
            this.cc=c;
            this.uservideotablename=uservideotablename;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try
            {
                String sql= "SELECT * FROM `"+uservideotablename+"`";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {

                    int videoid = rs.getInt("videoid");
                    String title= rs.getString("title");
                    String videopath= rs.getString("videopath");
                    String videoname= rs.getString("videoname");
                    uservideo uservideo=new uservideo(videoid,title,videopath,videoname);
                    cc.add(uservideo);
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
            callback();
        }
    }
    public List<uservideo> seauservideo(String uservideotablename) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找并修改
        flag=false;
        final List<uservideo> list=new ArrayList<uservideo>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadseauservideo(list,uservideotablename);
        chaxun.start();
        while (!flag);
        return list;
    }
    class MyThreadcoursesea2 extends Thread //子线程查询数据库用户单个课程
    {
        private List<user> cc;
        private String id;
        public MyThreadcoursesea2(List<user> c,String id)
        {
            this.cc=c;
            this.id=id;
        }
        public void run()
        {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try
            {
                String sql= "SELECT * FROM userlogin WHERE useraccout='"+id+"'";
                stmt = conn.prepareStatement(sql);
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
                    cc.add(book1);
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
            callback();
        }
    }
    public List<user> sea2(String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找并修改
        flag=false;
        final List<user> list=new ArrayList<user>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadcoursesea2(list,name);
        chaxun.start();
        while (!flag);
        return list;
    }
}
