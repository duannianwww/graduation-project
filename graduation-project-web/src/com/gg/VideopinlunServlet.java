package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/VideopinlunServlet")
public class VideopinlunServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        bookJDBC khz=new bookJDBC();
        String bookname=request.getParameter("bookname");
        String pinluntablename=request.getParameter("pinluntablename");
        b=khz.seabookpinlun(pinluntablename);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                request.getSession().setAttribute("bookname",bookname);
                request.getSession().setAttribute("pinluntablename",pinluntablename);
                jsonObject.put("flag",true);
                jsonObject.put("msg","课程名正确！进入评论浏览页");
                System.out.println("管理员进入评论浏览页成功");
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            response.getWriter().print(jsonObject);
        }
        else
        {
            try {
                jsonObject.put("flag",false);
                jsonObject.put("msg","课程名错误！请核对课程名");
                System.out.println("管理员进入视频浏览页失败");
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            response.getWriter().print(jsonObject);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
