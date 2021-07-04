package com.gg;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/VideoUpdateServlet")
@MultipartConfig
public class VideoUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        String filename1="";
        String desc="";
        bookJDBC khz=new bookJDBC();
        String bookname=request.getSession().getAttribute("bookname").toString();
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
        String videoid=request.getSession().getAttribute("videoid").toString();
        String videotablename=request.getSession().getAttribute("videotablename").toString();
        try {
            //获取文件描述信息
            desc=request.getParameter("desc");
            //获取上传的文件
            Part part=request.getPart("file");
            //获取请求的信息
            String fname=part.getHeader("content-disposition");
            //System.out.println(name);//测试使用
            //System.out.println(desc);//

            //获取上传文件的目录
            String root=request.getServletContext().getRealPath("/video");
            System.out.println("测试上传文件的路径："+root);

            //获取文件的后缀
            String str=fname.substring(fname.lastIndexOf("."), fname.length()-1);
            System.out.println("测试获取文件的后缀："+str);

            //生成一个新的文件名，不重复，数据库存储的就是这个文件名，不重复的
            filename1= UUID.randomUUID().toString()+str;
            String filename=root+"\\"+filename1;
            System.out.println("测试产生新的文件名："+filename);

            //上传文件到指定目录，不想上传文件就不调用这个
            part.write(filename);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(videotablename+"2:"+desc+"3:"+filename1);
        b=khz.updatevideo(videoid,desc,filename1,videotablename);

        if(b)
        {
            System.out.println("管理员修改视频成功");
            response.getWriter().print("<script>alert('修改成功！');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {
            System.out.println("管理员修改视频失败");
            response.getWriter().print("<script>alert('修改失败！信息不能为空！');\n" +
                    "        history.back(-1);</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
