package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validateAdminLogin")
public class AdminVaildateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        boolean b=false;

        System.out.println("有管理员提交了登录请求");

        adminlogin MyPOJN=new adminlogin();
        String log=request.getParameter("log");
        String pas=request.getParameter("pas");
        request.getSession().setAttribute("adminusername",log);
        JSONObject jsonObject=new JSONObject();
        b=MyPOJN.isadmin(log,pas);
        if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","登录成功!");
                System.out.println("管理员登录成功");
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
                jsonObject.put("msg","登录失败！请检查账号或密码");
                System.out.println("管理员登录失败");
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
        doPost(request, response);
    }
}
