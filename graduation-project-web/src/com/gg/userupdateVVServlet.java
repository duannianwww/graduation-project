package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/userupdateVVServlet")
public class userupdateVVServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        userlogin khz=new userlogin();
        String username=request.getParameter("username");
        String useraccout=request.getParameter("useraccout");
        String userpassword=request.getParameter("userpassword");
        String usertype=request.getParameter("usertype");

        b=khz.update(username,useraccout,userpassword,usertype);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","修改成功");
                System.out.println("管理员修改用户成功");
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
                jsonObject.put("msg","修改失败！信息不能为空！");
                System.out.println("管理员修改用户失败");
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
