package com.biyesheji.SQL;

import android.provider.Settings;
import android.util.Log;

import com.biyesheji.bean.kecheng;
import com.biyesheji.bean.pinlun;
import com.biyesheji.bean.shijuan;
import com.biyesheji.bean.shiti;
import com.biyesheji.bean.video;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/2/3.
 */

public class MySQL {
    static final String ip="10.0.2.2";
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://"+ip+":3306/kechengsheji?useUnicode=true&characterEncoding=UTF8&connectTimeout=3000&socketTimeout=60000";
    static final String USER = "root";
    static final String PASS = "123456";
    static boolean flag=false;
    private static boolean flag2;
    Connection conn = null;
    java.sql.Statement stmt = null;

    public static void callback()
    {
        System.out.println("子线程执行结束");
        flag=true;
    }
    class MyThreadcoursesea extends Thread //子线程查询数据库
    {
        private List<kecheng> cc;
        public MyThreadcoursesea(List<kecheng> c)
        {
            this.cc=c;
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
                String sql= "SELECT * FROM book ";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("bookid");
                    String bookname = rs.getString("bookname");
                    String author = rs.getString("author");
                    String press = rs.getString("press");
                    String pulishtime = rs.getString("publishtime");
                    String introduct = rs.getString("introduct");
                    double price=rs.getDouble("price");
                    int stock=rs.getInt("stock");
                    String imagename=rs.getString("bookimg");
                    String filename=rs.getString("filename");
                    String shijuantablename=rs.getString("filenameshijuan");
                    String filenamepinlun=rs.getString("filenamepinlun");
                    kecheng book1=new kecheng(bookid,bookname,author,press,pulishtime,introduct,price,stock,imagename,filename,shijuantablename,filenamepinlun);
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

    public List select() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所有书
        flag=false;
        final List<kecheng> list=new ArrayList<kecheng>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadcoursesea(list);
        chaxun.start();
        while (!flag);
        return list;
    }
    public List<kecheng> selectwhereuser(String coursename)
    {
        flag=false;
        final List<kecheng> list=new ArrayList<kecheng>();
        return list;
    }
    class MyThreadcoursebuttonsea extends Thread //子线程查询数据库
    {
        private List<kecheng> cc;
        private String coursetype;
        public MyThreadcoursebuttonsea(List<kecheng> c,String courseType)
        {
            this.cc=c;
            this.coursetype=courseType;
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
                String sql= "SELECT * FROM book WHERE press='"+coursetype+"'";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("bookid");
                    String bookname = rs.getString("bookname");
                    String author = rs.getString("author");
                    String press = rs.getString("press");
                    String pulishtime = rs.getString("publishtime");
                    String introduct = rs.getString("introduct");
                    double price=rs.getDouble("price");
                    int stock=rs.getInt("stock");
                    String imagename=rs.getString("bookimg");
                    String filename=rs.getString("filename");
                    String shijuantablename=rs.getString("filenameshijuan");
                    String filenamepinlun=rs.getString("filenamepinlun");
                    kecheng book1=new kecheng(bookid,bookname,author,press,pulishtime,introduct,price,stock,imagename,filename,shijuantablename,filenamepinlun);
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

    public List selectbutton(String type) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所有书
        flag=false;
        final List<kecheng> list=new ArrayList<kecheng>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadcoursebuttonsea(list,type);
        chaxun.start();
        while (!flag);
        return list;
    }
    class MyThreadusercourse extends Thread //子线程查询数据库
    {
        private List<kecheng> cc;
        private String coursename;
        public MyThreadusercourse(List<kecheng> c,String coursename)
        {
            this.cc=c;
            this.coursename=coursename;
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
                String sql= "SELECT * FROM book WHERE bookname='"+coursename+"'";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("bookid");
                    String bookname = rs.getString("bookname");
                    String author = rs.getString("author");
                    String press = rs.getString("press");
                    String pulishtime = rs.getString("publishtime");
                    String introduct = rs.getString("introduct");
                    double price=rs.getDouble("price");
                    int stock=rs.getInt("stock");
                    String imagename=rs.getString("bookimg");
                    String filename=rs.getString("filename");
                    String shijuantablename=rs.getString("filenameshijuan");
                    String filenamepinlun=rs.getString("filenamepinlun");
                    kecheng book1=new kecheng(bookid,bookname,author,press,pulishtime,introduct,price,stock,imagename,filename,shijuantablename,filenamepinlun);
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

    public List selectusercourse(String coursename) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所有书
        flag=false;
        final List<kecheng> list=new ArrayList<kecheng>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadusercourse(list,coursename);
        chaxun.start();
        while (!flag);
        return list;
    }

    public boolean sea22(String name)//根据课程名查找课程，返回布尔值
    {
        boolean userlog =false;
        String sql="SELECT * FROM book WHERE bookname='"+name+"'";
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
    class MyThreadcoursesea2 extends Thread //子线程查询数据库
    {
        private List<kecheng> cc;
        private String coursename;
        public MyThreadcoursesea2(List<kecheng> c,String coursename)
        {
            this.cc=c;
            this.coursename=coursename;
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
                String sql= "SELECT * FROM book WHERE bookname='"+coursename+"'";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("bookid");
                    String bookname = rs.getString("bookname");
                    String author = rs.getString("author");
                    String press = rs.getString("press");
                    String pulishtime = rs.getString("publishtime");
                    String introduct = rs.getString("introduct");
                    double price=rs.getDouble("price");
                    int stock=rs.getInt("stock");
                    String imagename=rs.getString("bookimg");
                    String filename=rs.getString("filename");
                    String shijuantablename=rs.getString("filenameshijuan");
                    String filenamepinlun=rs.getString("filenamepinlun");
                    kecheng book1=new kecheng(bookid,bookname,author,press,pulishtime,introduct,price,stock,imagename,filename,shijuantablename,filenamepinlun);
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
    public kecheng sea2(String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//根据课程名查找课程，返回数组
        flag=false;
        List<kecheng> list =new ArrayList<kecheng>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadcoursesea2(list,name);
        chaxun.start();
        while (!flag);
        kecheng book=list.get(0);
        return book;
    }
    class MyThreadvideosea extends Thread //子线程查询数据库
    {
        private List<video> cc;
        private String viedeotablename;
        public MyThreadvideosea(List<video> c,String coursename1)
        {
            this.cc=c;
            this.viedeotablename=coursename1;
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
                String sql= "SELECT  *  FROM  `"+viedeotablename+"`";//表名有特殊字符时要在表名前后加` `
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("coursevideoid");
                    String bookname = rs.getString("coursevideoname");
                    String author = rs.getString("coursemiaosu");
                    video book1=new video(bookid,bookname,author);
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
    public List seavideo(String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所属课程所有视频，返回数组
        flag=false;
        List<video> list =new ArrayList<video>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadvideosea(list,name);
        chaxun.start();
        while (!flag);
        return list;
    }
    class MyThreadshijuannumsea extends Thread //子线程查询数据库
    {
        private List<shiti> cc;
        private String coursename;
        private String shijuanname;
        public MyThreadshijuannumsea(List<shiti> c,String coursename1,String shijuanname1)
        {
            this.cc=c;
            this.coursename=coursename1;
            this.shijuanname=shijuanname1;
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
                String sql= "SELECT  *  FROM question WHERE coursename='"+coursename+"' AND shijuanname='"+shijuanname+"'";//表名有特殊字符时要在表名前后加` `
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int id=rs.getInt("id");
                    String shijuanname = rs.getString("shijuanname");
                    String questiontype = rs.getString("questiontype");
                    Log.i("类型:",questiontype);
                    String questionquestion = rs.getString("questionquestion");
                    Log.i("题干:",questionquestion);
                    String answer=rs.getString("answer");
                    String questionsnote=rs.getString("questionsnote");
                    String a=rs.getString("a");
                    String b=rs.getString("b");
                    String c=rs.getString("c");
                    String d=rs.getString("d");
                    String e=rs.getString("e");
                    String f=rs.getString("f");
                    String g=rs.getString("g");
                    String h=rs.getString("h");
                    String answer2=rs.getString("answer2");
                    String answer3=rs.getString("answer3");
                    String answer4=rs.getString("answer4");
                    String answer5=rs.getString("answer5");
                    String answer6=rs.getString("answer6");
                    String coursename=rs.getString("coursename");
                    shiti book1=new shiti(id,coursename,shijuanname,questiontype,questionquestion,answer,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6,questionsnote);
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
    public List<shiti> seaquestioncourse(String coursename,String shijuanname) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException{
        flag=false;
        List<shiti> list =new ArrayList<shiti>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadshijuannumsea(list,coursename,shijuanname);
        chaxun.start();
        while (!flag);
        return list;
    }
    public List seaquestion() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所属试卷所有试题，返回数组
        List<shiti> list =new ArrayList<shiti>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM question";//表名有特殊字符时要在表名前后加` `
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id=rs.getInt("id");
                String shijuanname = rs.getString("shijuanname");
                String questiontype = rs.getString("questiontype");
                String questionquestion = rs.getString("questionquestion");
                String answer=rs.getString("answer");
                String questionsnote=rs.getString("questionsnote");
                String a=rs.getString("a");
                String b=rs.getString("b");
                String c=rs.getString("c");
                String d=rs.getString("d");
                String e=rs.getString("e");
                String f=rs.getString("f");
                String g=rs.getString("g");
                String h=rs.getString("h");
                String answer2=rs.getString("answer2");
                String answer3=rs.getString("answer3");
                String answer4=rs.getString("answer4");
                String answer5=rs.getString("answer5");
                String answer6=rs.getString("answer6");
                String coursename=rs.getString("coursename");
                shiti book1=new shiti(id,coursename,shijuanname,questiontype,questionquestion,answer,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6,questionsnote);
                list.add(book1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public List seaquestion3(int questionid) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//根据id查找所属试卷所有试题，返回数组
        List<shiti> list =new ArrayList<shiti>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM question where id='"+questionid+"'";//表名有特殊字符时要在表名前后加` `
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id=rs.getInt("id");
                String shijuanname = rs.getString("shijuanname");
                String questiontype = rs.getString("questiontype");
                String questionquestion = rs.getString("questionquestion");
                String answer=rs.getString("answer");
                String questionsnote=rs.getString("questionsnote");
                String a=rs.getString("a");
                String b=rs.getString("b");
                String c=rs.getString("c");
                String d=rs.getString("d");
                String e=rs.getString("e");
                String f=rs.getString("f");
                String g=rs.getString("g");
                String h=rs.getString("h");
                String answer2=rs.getString("answer2");
                String answer3=rs.getString("answer3");
                String answer4=rs.getString("answer4");
                String answer5=rs.getString("answer5");
                String answer6=rs.getString("answer6");
                String coursename=rs.getString("coursename");
                shiti book1=new shiti(id,coursename,shijuanname,questiontype,questionquestion,answer,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6,questionsnote);
                list.add(book1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public List seaxuanzhe() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所属课程所有视频，返回数组
        List<shiti> list =new ArrayList<shiti>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM question WHERE questiontype='"+"选择题"+"' OR questiontype='"+"多选题"+"'";//表名有特殊字符时要在表名前后加` `
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id=rs.getInt("id");
                String shijuanname = rs.getString("shijuanname");
                String questiontype = rs.getString("questiontype");
                String questionquestion = rs.getString("questionquestion");
                String answer=rs.getString("answer");
                String questionsnote=rs.getString("questionsnote");
                String a=rs.getString("a");
                String b=rs.getString("b");
                String c=rs.getString("c");
                String d=rs.getString("d");
                String e=rs.getString("e");
                String f=rs.getString("f");
                String g=rs.getString("g");
                String h=rs.getString("h");
                String answer2=rs.getString("answer2");
                String answer3=rs.getString("answer3");
                String answer4=rs.getString("answer4");
                String answer5=rs.getString("answer5");
                String answer6=rs.getString("answer6");
                String coursename=rs.getString("coursename");
                shiti book1=new shiti(id,coursename,shijuanname,questiontype,questionquestion,answer,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6,questionsnote);
                list.add(book1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public List seatiankong(String type) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所属课程所有视频，返回数组
        List<shiti> list =new ArrayList<shiti>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM question WHERE questiontype='"+type+"'";//表名有特殊字符时要在表名前后加` `
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id=rs.getInt("id");
                String shijuanname = rs.getString("shijuanname");
                String questiontype = rs.getString("questiontype");
                String questionquestion = rs.getString("questionquestion");
                String answer=rs.getString("answer");
                String questionsnote=rs.getString("questionsnote");
                String a=rs.getString("a");
                String b=rs.getString("b");
                String c=rs.getString("c");
                String d=rs.getString("d");
                String e=rs.getString("e");
                String f=rs.getString("f");
                String g=rs.getString("g");
                String h=rs.getString("h");
                String answer2=rs.getString("answer2");
                String answer3=rs.getString("answer3");
                String answer4=rs.getString("answer4");
                String answer5=rs.getString("answer5");
                String answer6=rs.getString("answer6");
                String coursename=rs.getString("coursename");
                shiti book1=new shiti(id,coursename,shijuanname,questiontype,questionquestion,answer,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6,questionsnote);
                list.add(book1);
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public boolean updatexuanzheorduoxuan(int questionid,String question,String a,String b,String c,String d,String xuane,String f,String g,String h,String answer,String introduction)
    {
        boolean userlog =false;
        String sql="update question set questionquestion='"+question+"',a='"+a+"',b='"+b+"',c='"+c+"',d='"+d+"',e='"+xuane+"',f='"+f+"',g='"+g+"',h='"+h+"',answer='"+answer+"',questionsnote='"+introduction+"' WHERE id='"+questionid+"'";
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
    public boolean updatewendaorpanduan(int questionid,String question,String answer,String introduction)
    {
        boolean userlog =false;
        String sql="update question set questionquestion='"+question+"',answer='"+answer+"',questionsnote='"+introduction+"' WHERE id='"+questionid+"'";
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
    public boolean updatetiankong(int questionid,String question,String answer,String answer2,String answer3,String answer4,String answer5,String answer6,String introduction)
    {
        boolean userlog =false;
        String sql="update question set questionquestion='"+question+"',answer='"+answer+"',answer2='"+answer2+"',answer3='"+answer3+"',answer4='"+answer4+"',answer5='"+answer5+"',answer6='"+answer6+"',questionsnote='"+introduction+"' WHERE id='"+questionid+"'";
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
    public List seavideo3(String tablename,String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//根据视频名查找视频，返回数组
        List<video> list =new ArrayList<video>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM  `"+tablename+"` WHERE coursevideoname='"+name+"'";//表名有特殊字符时要在表名前后加` `
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int bookid=rs.getInt("coursevideoid");
                String bookname = rs.getString("coursevideoname");
                String author = rs.getString("coursemiaosu");
                video book1=new video(bookid,bookname,author);
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
    class MyThreadpinlunsea extends Thread //子线程查询数据库
    {
        private List<pinlun> cc;
        private String pinluntablename;
        public MyThreadpinlunsea(List<pinlun> c,String coursename1)
        {
            this.cc=c;
            this.pinluntablename=coursename1;
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
                String sql= "SELECT  *  FROM  `"+pinluntablename+"`";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("pinlunid");
                    String bookname = rs.getString("pinlunname");
                    String author = rs.getString("introduction");
                    String press = rs.getString("pinluntime");
                    pinlun book1=new pinlun(bookid,bookname,author,press);
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
    public List seapinlun(String videoname) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        flag=false;
        List<pinlun> list =new ArrayList<pinlun>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadpinlunsea(list,videoname);
        chaxun.start();
        while (!flag);
        return list;
    }
    class MyThreadshijuansea extends Thread //子线程查询数据库
    {
        private List<shijuan> cc;
        private String pinluntablename;
        public MyThreadshijuansea(List<shijuan> c,String coursename1)
        {
            this.cc=c;
            this.pinluntablename=coursename1;
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
                String sql= "SELECT  *  FROM  `"+pinluntablename+"`";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int bookid=rs.getInt("shijuanid");
                    String bookname = rs.getString("shijuanname");
                    shijuan book1=new shijuan(bookid,bookname);
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
    public List seashijuan(String shijuantablename) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        flag=false;
        List<shijuan> list =new ArrayList<shijuan>();
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadshijuansea(list,shijuantablename);
        chaxun.start();
        while (!flag);
        return list;
    }
    public List seapinlun3(String videoname,String pinlunid) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        List<pinlun> list =new ArrayList<pinlun>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM  `"+videoname+"` WHERE pinlunid='"+pinlunid+"'";//表名有特殊字符时要在表名前后加` `
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int bookid=rs.getInt("pinlunid");
                String bookname = rs.getString("pinlunname");
                String author = rs.getString("introduction");
                String press = rs.getString("pinluntime");
                pinlun book1=new pinlun(bookid,bookname,author,press);
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
    public boolean seapinlun2(String pinlunid,String pinluntablename)//搜索某个视频，返回布尔值，表示是否存在
    {
        boolean userlog =false;
        String sql="SELECT * FROM `"+pinluntablename+"` WHERE pinlunid='"+pinlunid+"'";
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
    public boolean seaquestion2(int id)//搜索某个视频，返回布尔值，表示是否存在
    {
        boolean userlog =false;
        String sql="SELECT * FROM question WHERE id='"+id+"'";
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
    public boolean seavideo2(String videoname,String videotablename)//搜索某个视频，返回布尔值，表示是否存在
    {
        boolean userlog =false;
        String sql="SELECT * FROM `"+videotablename+"` WHERE coursevideoname='"+videoname+"'";
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
    public boolean updatevideo(String descc,String filename,String filenamepinlun,String videotablename)
    {
        boolean userlog =false;
        String sql="update `"+videotablename+"` set coursevideoname='"+filename+"',coursemiaosu='"+descc+"',videopinluntablename='"+filenamepinlun+"' WHERE coursevideoname='"+filename+"'";
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
    public boolean updatepinlun(String descc,String pinlunid,String pinlunintroduction,String videotablename)
    {
        boolean userlog =false;
        String sql="update `"+videotablename+"` set pinlunname='"+descc+"',introduction='"+pinlunintroduction+"' WHERE pinlunid='"+pinlunid+"'";
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
    public boolean deletequestion(int id){
        boolean userlog =false;
        String sql="SELECT * FROM  question WHERE id='"+id+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                sql="delete from question where id='"+id+"'";
                stmt.execute(sql);
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
    public boolean deletevideo(String videoname,String del)
    {
        boolean userlog =false;
        String sql="SELECT  *  FROM  `"+videoname+"`  WHERE coursevideoname='"+del+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                String videopinluntablename = rs.getString("videopinluntablename");
                sql="drop table `"+videopinluntablename+"`";
                stmt.execute(sql);
                sql="delete from  `"+videoname+"`  where coursevideoname='"+del+"'";
                stmt.execute(sql);

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
    public boolean deletepinlun(String videoname,int del)
    {
        boolean userlog =false;
        String sql="SELECT  *  FROM  `"+videoname+"`  WHERE pinlunid='"+del+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                sql="delete from  `"+videoname+"`  where pinlunid='"+del+"'";
                stmt.execute(sql);
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
    class MyThreadaddpinlun extends Thread //子线程查询数据库
    {
        private boolean cc;
        private String username;
        private String introduction;
        private String sendtime;
        private String pinluntable;
        public MyThreadaddpinlun(boolean c,String username,String introduction,String sendtime,String pinluntable)
        {
            this.cc=c;
            this.username=username;
            this.introduction=introduction;
            this.sendtime=sendtime;
            this.pinluntable=pinluntable;
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
                String sql = "INSERT INTO `"+pinluntable+"`(pinlunname,introduction,pinluntime) values('"+username+"','"+introduction+"','"+sendtime+"')";
                stmt = conn.createStatement();
                stmt.execute(sql);
                cc=true;
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
    public boolean addpinlun(String username,String introduction,String sendtime,String pinluntable) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        boolean userlog =false;
        flag=false;
        flag2=false;
        Class.forName(JDBC_DRIVER).newInstance();
        Thread chaxun=new MyThreadaddpinlun(userlog,username,introduction,sendtime,pinluntable);
        chaxun.start();
        while (!flag);
        userlog=flag2;
        return userlog;
    }
    public static void istrue()
    {
        flag2 =true;
    }

    public boolean addshijuan(String shijuanname,String shijuantablename)
    {
        boolean userlog =false;
        String sql="";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            sql = "INSERT INTO `"+shijuantablename+"`(shijuanname) value('"+shijuanname+"')";
            stmt.execute(sql);
            userlog  = true;
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return userlog;
    }
    public boolean addquestion(String coursename,String shijuanname,String questiontype,String questionquestion,String answer,String questionsnote,String xuana,String xuanb,String xuanc,String xuand,String xuane,String xuanf,String xuang,String xuanh,String answer2,String answer3,String answer4,String answer5,String answer6)
    {
        boolean userlog =false;
        String sql="";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            sql = "INSERT INTO question(coursename,shijuanname,questiontype,questionquestion,answer,questionsnote,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6)" +
                    " values('"+coursename+"','"+shijuanname+"','"+questiontype+"','"+questionquestion+"','"+answer+"','"+questionsnote+"'," +
                    "'"+xuana+"','"+xuanb+"','"+xuanc+"','"+xuand+"','"+xuane+"','"+xuanf+"','"+xuang+"','"+xuanh+"'," +
                    "'"+answer2+"','"+answer3+"','"+answer4+"','"+answer5+"','"+answer6+"')";
            stmt.execute(sql);
            userlog  = true;
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return userlog;
    }
    public boolean addvideo(String descc,String filename,String filenamepinlun,String videotablename)
    {
        boolean userlog =false;
        String sql="SELECT * FROM `"+videotablename+"` WHERE coursevideoname='"+filename+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()) {
                sql = "INSERT INTO `"+videotablename+"`(coursevideoname,coursemiaosu,videopinluntablename) values('"+filename+"','"+descc+"','"+filenamepinlun+"')";
                stmt.execute(sql);
                sql="create table `"+filenamepinlun+"` ( " +
                        "  `pinlunid` int(20) NOT NULL AUTO_INCREMENT COMMENT '评论id'," +
                        "  `pinlunname` char(255) NOT NULL DEFAULT '' COMMENT '发表人名字'," +
                        "  `introduction` char(255) NOT NULL DEFAULT '' COMMENT '评论内容'," +
                        "  `pinluntime` char(255) NOT NULL DEFAULT '' COMMENT '评论时间'," +
                        "  PRIMARY KEY (`pinlunid`))" +
                        " ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
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

    public boolean update(String name, String author, String press, String publishtime, String introduct,int stock,String imagename)//改
    {
        boolean userlog =false;
        String sql="update book set bookname='"+name+"',author='"+author+"',press='"+press+"',publishtime='"+publishtime+"',introduct='"+introduct+"',stock='"+stock+"',bookimg='"+imagename+"' WHERE bookname='"+name+"'";
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
    public void close()//关闭数据库
    {
        try
        {
            if(stmt!=null) stmt.close();
        }
        catch (SQLException se2)
        {
            try
            {
                if (conn != null) conn.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        System.out.println("Good bye!");
    }
}
