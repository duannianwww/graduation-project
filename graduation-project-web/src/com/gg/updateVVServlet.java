package com.gg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/updateVVServlet")
@MultipartConfig
public class updateVVServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b;
        String imagename1="";
        bookJDBC khz=new bookJDBC();
        String name=request.getParameter("name");
        String author=request.getParameter("author");
        String press=request.getParameter("press");
        String presstime=request.getParameter("presstime");
        String introduction=request.getParameter("introduction");
        String stock1=request.getParameter("stock");
        String price1=request.getParameter("price");
        Double price=Double.valueOf(price1);
        int stock=Integer.parseInt(stock1);
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
            imagename1= UUID.randomUUID().toString()+str;
            String imagename=root+"\\"+imagename1;
            System.out.println("测试产生新的图片名："+imagename);

            //上传文件到指定目录，不想上传文件就不调用这个
            image.write(imagename);

        } catch (Exception e) {
            e.printStackTrace();
        }
        b=khz.update(name,author,press,presstime,introduction,price,stock,imagename1);
        if(b)
        {
            System.out.println("管理员修改课程成功");
            response.getWriter().print("<script>alert('修改成功！');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {
            System.out.println("管理员修改课程失败");
            response.getWriter().print("<script>alert('修改失败！信息不能为空！');\n" +
                    "        history.back(-1);</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
