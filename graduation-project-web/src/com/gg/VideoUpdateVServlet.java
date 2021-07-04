package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/VideoUpdateVServlet")
public class VideoUpdateVServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        bookJDBC khz=new bookJDBC();
        String videoid=request.getParameter("videoid");
        String videoname=request.getParameter("videoname");
        String videotablename=request.getParameter("videotablename");
        b=khz.seavideo2(videoname,videotablename);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                request.getSession().setAttribute("videoid",videoid);
                request.getSession().setAttribute("videoname",videoname);
                request.getSession().setAttribute("videotablename",videotablename);
                jsonObject.put("flag",true);
                jsonObject.put("msg","视频名正确！进入修改页");
                System.out.println("管理员进入修改页成功");
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
                jsonObject.put("msg","视频名错误！请检查视频名");
                System.out.println("管理员进入修改页失败");
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
