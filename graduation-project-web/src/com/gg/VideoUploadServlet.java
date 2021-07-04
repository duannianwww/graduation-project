package com.gg;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/VideoUploadServlet")
@MultipartConfig

public class VideoUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b=false;
        bookJDBC khz=new bookJDBC();
        String desc="";
        String bookname=request.getSession().getAttribute("bookname").toString();
        String videoname="";
        String filename1="";
        String filename2="";
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
        try {
            //获取文件描述
            desc=request.getParameter("desc");
            //获取上传的文件
            Part part=request.getPart("file");
            //获取请求的信息
            String fname=part.getHeader("content-disposition");
            //System.out.println(name);//测试使用
            //System.out.println(desc);//

            //获取上传文件的目录
            String root=request.getServletContext().getRealPath("/video");
            System.out.println("测试上传视频的路径："+root);

            //获取文件的后缀
            String str=fname.substring(fname.lastIndexOf("."), fname.length()-1);
            System.out.println("测试获取视频的后缀："+str);

            //生成一个新的文件名，不重复，数据库存储的就是这个文件名，不重复的
            filename2= UUID.randomUUID().toString();
            filename1=filename2+str;
            String filename=root+"\\"+filename1;
            System.out.println("测试产生新的视频名："+filename);

            //上传文件到指定目录，不想上传文件就不调用这个

            part.write(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        b=khz.addvideo(desc,filename1,videoname);
        if(b)
        {
            System.out.println("管理员上传视频成功");
            response.getWriter().print("<script>alert('上传视频成功');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {

            System.out.println("管理员上传视频失败");
            response.getWriter().print("<script>alert('上传视频失败！请提交错误给管理员');\n" +
                    "        history.back(-1);</script>");
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
