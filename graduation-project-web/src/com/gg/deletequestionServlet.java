package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deletequestionServlet")
public class deletequestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        bookJDBC khz=new bookJDBC();
        String del=request.getParameter("del");
        int del1=Integer.parseInt(del);
        b=khz.deletequestion(del1);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","删除试题成功！");
                System.out.println("管理员删除试题成功");
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
                jsonObject.put("msg","删除试题失败！请提交反馈信息给管理员");
                System.out.println("管理员删除试题失败");
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
