package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updatequestionServlet")
public class updatequestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        bookJDBC khz=new bookJDBC();
        String questionid=request.getParameter("questionid");
        int questionid1=Integer.parseInt(questionid);
        b=khz.seaquestion2(questionid1);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                request.getSession().setAttribute("questionid",questionid);
                jsonObject.put("flag",true);
                jsonObject.put("msg","进入习题修改页！");
                System.out.println("管理员进入习题修改页成功");
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
                jsonObject.put("msg","进入习题修改页失败！请联系管理员");
                System.out.println("管理员进入习题修改页失败");
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
