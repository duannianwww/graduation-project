package com.gg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/addpinlunServlet")
public class addpinlunServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b=false;
        bookJDBC khz=new bookJDBC();
        String username=request.getParameter("username");
        String introduction=request.getParameter("introduction");
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String sendtime=df.format(new Date());
        String pinluntablename=request.getSession().getAttribute("pinluntablename").toString();
        b=khz.addpinlun(username,introduction,sendtime,pinluntablename);
        if(b)
        {
            System.out.println("管理员添加评论成功");
            response.getWriter().print("<script>alert('添加评论成功');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {

            System.out.println("管理员添加评论失败");
            response.getWriter().print("<script>alert('添加评论失败！可能出现了异常，请提交给管理员');\n" +
                    "        history.back(-1);</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
