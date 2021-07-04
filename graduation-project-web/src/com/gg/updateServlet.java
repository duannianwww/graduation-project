package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@WebServlet("/updateServlet")
@MultipartConfig
public class updateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b=false;
        String imagename1="";
        bookJDBC khz=new bookJDBC();
        String name=request.getParameter("name");
        String author=request.getParameter("author");
        String press=request.getParameter("press");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String presstime=df.format(new Date());
        String introduction=request.getParameter("introduction");
        String price=request.getParameter("price");
        Double price1=Double.valueOf(price);
        String stock1=request.getParameter("stock");
        try {
            //获取上传的文件
            Part image=request.getPart("image");
            //获取请求的信息
            String fname=image.getHeader("content-disposition");
            //System.out.println(name);//测试使用
            //System.out.println(desc);//

            //获取上传文件的目录
            String root=request.getServletContext().getRealPath("/img");
            System.out.println("测试上传图片的路径："+root);

            //获取文件的后缀
            String str=fname.substring(fname.lastIndexOf("."), fname.length()-1);
            System.out.println("测试获取图片的后缀："+str);

            //生成一个新的文件名，不重复，数据库存储的就是这个文件名，不重复的
            imagename1=UUID.randomUUID().toString()+str;
            String imagename=root+"\\"+imagename1;
            System.out.println("测试产生新的图片名："+imagename);

            //上传文件到指定目录，不想上传文件就不调用这个
            image.write(imagename);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String filename=UUID.randomUUID().toString();
        String filenameshijuan=UUID.randomUUID().toString();
        String filenamepinlun=UUID.randomUUID().toString();
        b=khz.add(name,author,press,presstime,introduction,price1,stock1,imagename1,filename,filenameshijuan,filenamepinlun);
        JSONObject jsonObject=new JSONObject();
        System.out.println(b);
        if(b)
        {
            System.out.println("管理员插入课程信息成功");
            response.getWriter().print("<script>alert('添加课程信息成功！');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {

            System.out.println("管理员插入课程信息失败");
            response.getWriter().print("<script>alert('添加课程信息失败！数据库中已有相同名字课程');\n" +
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
