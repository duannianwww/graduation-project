package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pinlunupdateServlet")
public class pinlunupdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        bookJDBC khz=new bookJDBC();
        String pinlunid=request.getSession().getAttribute("pinlunid").toString();
        String pinluntablename=request.getSession().getAttribute("pinluntablename").toString();
        b=khz.seapinlun2(pinlunid,pinluntablename);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                request.getSession().setAttribute("pinlunid",pinlunid);
                request.getSession().setAttribute("pinluntablename",pinluntablename);
                jsonObject.put("flag",true);
                jsonObject.put("msg","进入评论修改页成功");
                System.out.println("管理员进入评论修改页成功");
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
                jsonObject.put("msg","进入评论修改页失败，请联系管理员！");
                System.out.println("管理员进入评论修改页失败");
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
