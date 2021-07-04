package com.gg;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookJDBC {

    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/kechengsheji?useUnicode=true&characterEncoding=UTF8";
    static final String USER = "root";
    static final String PASS = "123456";
    Connection conn = null;
    Statement stmt = null;
    public boolean add(String name, String author, String press, String publishtime, String introduct,double price,String stock,String imagename,String filename,String filenameshijuan,String filenamepinlun) throws FileNotFoundException {//增
        boolean userlog =false;
        String sql="SELECT * FROM book WHERE bookname='"+name+"'";

        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()) {
                sql = "INSERT INTO book(bookname,author,press,publishtime,introduct,price,stock,bookimg,filename,filenameshijuan,filenamepinlun) values('"+name+"','"+author+"','"+press+"','"+publishtime+"','"+introduct+"','"+price+"','"+stock+"','"+imagename+"','"+filename+"','"+filenameshijuan+"','"+filenamepinlun+"')";
                stmt.execute(sql);
                sql="create table `"+filename+"` ( " +
                        "  `coursevideoid` int(20) NOT NULL AUTO_INCREMENT COMMENT '视频id'," +
                        "  `coursevideoname` char(255) NOT NULL DEFAULT '' COMMENT '视频名字'," +
                        "  `coursemiaosu` char(255) NOT NULL DEFAULT '' COMMENT '视频描述'," +
                        "  PRIMARY KEY (`coursevideoid`))" +
                        " ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
                stmt.execute(sql);
                sql="create table `"+filenameshijuan+"` ( " +
                        "  `shijuanid` int(20) NOT NULL AUTO_INCREMENT COMMENT '试卷id'," +
                        "  `shijuanname` char(255) NOT NULL DEFAULT '' COMMENT '试卷名字'," +
                        "  PRIMARY KEY (`shijuanid`))" +
                        " ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";

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
    public boolean delete(String id)//删
    {
        boolean userlog =false;
        String sql="SELECT * FROM book WHERE bookid='"+id+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                sql="delete from book where bookid='"+id+"'";
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
    public List select() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所有课程，返回一个数组
        List<kecheng> list =new ArrayList<kecheng>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
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

    public List sea2(String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//根据课程名查找课程，返回数组
        List<kecheng> list =new ArrayList<kecheng>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT * FROM book WHERE bookname='"+name+"'";
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
    public List seavideo(String name) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {//查找所属课程所有视频，返回数组
        List<video> list =new ArrayList<video>();
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
            String sql= "SELECT  *  FROM  `"+name+"`";//表名有特殊字符时要在表名前后加` `
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
    public List seapinlun(String videoname) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException
    {
        List<pinlun> list =new ArrayList<pinlun>();
        String sql= "SELECT  *  FROM  `"+videoname+"`";//表名有特殊字符时要在表名前后加` `
        Class.forName(JDBC_DRIVER).newInstance();
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        try
        {
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
    public boolean seabookpinlun(String pinlunname){
        boolean userlog =false;
        String sql="SELECT * FROM book WHERE filenamepinlun='"+pinlunname+"'";
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
    public boolean updatevideo(String coursevideoid,String descc,String filename,String videotablename)
    {
        boolean userlog =false;
        String sql="update `"+videotablename+"` set coursevideoname='"+filename+"',coursemiaosu='"+descc+"' WHERE coursevideoid='"+coursevideoid+"'";
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
    public boolean addpinlun(String username,String introduction,String sendtime,String pinluntable)
    {
        boolean userlog =false;
        String sql="";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            sql = "INSERT INTO `"+pinluntable+"`(pinlunname,introduction,pinluntime) values('"+username+"','"+introduction+"','"+sendtime+"')";
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
    public boolean addvideo(String descc,String filename,String videotablename)
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
                sql = "INSERT INTO `"+videotablename+"`(coursevideoname,coursemiaosu) values('"+filename+"','"+descc+"')";
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

    public boolean update(String name, String author, String press, String publishtime, String introduct,double price,int stock,String imagename)//改
    {
        boolean userlog =false;
        String sql="update book set bookname='"+name+"',author='"+author+"',press='"+press+"',publishtime='"+publishtime+"',introduct='"+introduct+"',price='"+price+"',stock='"+stock+"',bookimg='"+imagename+"' WHERE bookname='"+name+"'";
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
