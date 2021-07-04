package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pinlunupdateVServlet")
public class pinlunupdateVServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        bookJDBC khz=new bookJDBC();
        String pinlunid=request.getSession().getAttribute("pinlunid").toString();
        String pinluntablename=request.getSession().getAttribute("pinluntablename").toString();
        String pinlunusername=request.getParameter("desc");
        String pinlunintroduction=request.getParameter("introduction");
        b=khz.updatepinlun(pinlunusername,pinlunid,pinlunintroduction,pinluntablename);
        if(b)
        {
            System.out.println("管理员修改评论成功");
            response.getWriter().print("<script>alert('修改成功！');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {
            System.out.println("管理员修改评论失败");
            response.getWriter().print("<script>alert('修改失败！信息不能为空！');\n" +
                    "        history.back(-1);</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
