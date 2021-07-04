package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/deleteVideoServlet")
public class deleteVideoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b=false;
        bookJDBC khz=new bookJDBC();
        String bookname=request.getParameter("bookname");
        String del=request.getParameter("del");
        String videoname="";
        List<kecheng> list =new ArrayList<kecheng>();
        try {
            list=khz.select();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(kecheng book1:list)
        {
            if(book1.getBookname().equals(bookname))
            {
                videoname=book1.getFilename();
            }
        }
        b=khz.deletevideo(videoname,del);
        JSONObject jsonObject=new JSONObject();
        if(b)
        {
            try {
                jsonObject.put("flag",true);
                jsonObject.put("msg","删除视频成功！");
                System.out.println("管理员删除视频成功");
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
                jsonObject.put("msg","删除视频失败！请检查视频名是否正确");
                System.out.println("管理员删除视频失败");
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
