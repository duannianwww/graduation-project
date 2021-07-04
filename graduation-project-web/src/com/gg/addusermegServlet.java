package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/addusermegServlet")
public class addusermegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b=false;
        userlogin khz=new userlogin();
        String name=request.getParameter("username");
        String author=request.getParameter("useraccout");
        String press=request.getParameter("userpassword");
        String presstime=request.getParameter("usertype");
        String userpasswordsafe=request.getParameter("userpasswordsafe");
        String usertyblename= UUID.randomUUID().toString();
        String uservideotablename=UUID.randomUUID().toString();
        String usergerentablename=UUID.randomUUID().toString();
        b=khz.add(name,author,press,presstime,usertyblename,uservideotablename,usergerentablename,userpasswordsafe);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","添加用户信息成功！");
                System.out.println("管理员插入用户信息成功！");
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
                jsonObject.put("msg","添加用户信息失败！");
                System.out.println("管理员插入用户信息失败！");
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            response.getWriter().print(jsonObject);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        doPost(request, response);
    }
}
