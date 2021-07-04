package com.gg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/validateRegister")
public class RegisterVaildateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean b=false;
        System.out.println("有用户提交了注册信息");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        userlogin MyPOJN=new userlogin();
        String name=request.getParameter("name");
        String log=request.getParameter("log");
        String pas=request.getParameter("pas");
        JSONObject jsonObject=new JSONObject();
        b=MyPOJN.userre(name,log,pas);
        if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","注册成功！");
                System.out.println("用户注册成功");
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
                jsonObject.put("msg","注册失败！账号或密码不能为空！");
                System.out.println("用户注册失败");
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
