package com.gg;

import java.sql.*;

public class adminlogin {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/kechengsheji?Unicode=true&characterEncoding=UTF8";
    static final String USER = "root";
    static final String PASS = "123456";
    public boolean isadmin (String id,String password)
    {
        boolean userlog =false;
        String sql="SELECT name, password FROM adminlogin WHERE name='"+id+"' and password='"+password+"'";
        try
        {
            Class.forName(JDBC_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
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
}
