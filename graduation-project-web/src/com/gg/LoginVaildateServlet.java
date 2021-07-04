package com.gg;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/validateLogin")
public class LoginVaildateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean b=false;
    System.out.println("有用户提交了登录信息");
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    userlogin MyPOJN=new userlogin();
    String log=request.getParameter("log");
    String pas=request.getParameter("pas");
    request.getSession().setAttribute("useraccout",log);
    JSONObject jsonObject=new JSONObject();
    b=MyPOJN.isuser(log,pas);
    if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","登录成功！");
                System.out.println("用户登录成功");
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
                System.out.println("用户登录失败");
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
