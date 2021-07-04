package com.gg;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/shijuanUploadServlet")
@MultipartConfig
public class shijuanUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean bool=false;
        boolean bool2=false;
        bookJDBC khz=new bookJDBC();
        String shijuanname=request.getParameter("shijuanname");
        String coursename=request.getParameter("coursename");
        String desc="";
        String filename="";//全路径
        String filename1="";//仅文件名
        try {
            //获取文件描述
            desc=request.getParameter("desc");
            //获取上传的文件

            Part part=request.getPart("file");
            //获取请求的信息
            String fname=part.getHeader("content-disposition");
            //System.out.println(name);//
            //System.out.println(desc);//

            //获取上传文件的目录
            String root=request.getServletContext().getRealPath("/shijuan");
            System.out.println("测试上传试卷的路径："+root);

            //获取文件的后缀
            String str=fname.substring(fname.lastIndexOf("."), fname.length()-1);
            System.out.println("测试获取试卷的后缀："+str);

            //生成一个新的文件名，不重复，数据库存储的就是这个文件名，不重复的
            filename1= UUID.randomUUID().toString()+str;
            filename=root+"\\"+filename1;
            System.out.println("测试产生新的试卷名："+filename);

            //上传文件到指定目录，不想上传文件就不调用这个
            part.write(filename);


        } catch (Exception e) {
            e.printStackTrace();
        }
        List<shiti> shitis=new ArrayList<shiti>();
        ExcelUtil excelUtil=new ExcelUtil();
        shitis=excelUtil.readExcel(filename);
        for(shiti book:shitis)
        {
            String questionquestion=book.getQuestion();
            String questiontype=book.getQuestiontype();
            String answer=book.getAnswer();
            String questionsnote=book.getQuestionsnote();
            String answer2=book.getAnswer2();
            String answer3=book.getAnswer3();
            String answer4=book.getAnswer4();
            String answer5=book.getAnswer5();
            String answer6=book.getAnswer6();
            String a=book.getA();
            String b=book.getB();
            String c=book.getC();
            String d=book.getD();
            String e=book.getE();
            String f=book.getF();
            String g=book.getG();
            String h=book.getH();
            bool=khz.addquestion(coursename,shijuanname,questiontype,questionquestion,answer,questionsnote,a,b,c,d,e,f,g,h,answer2,answer3,answer4,answer5,answer6);

        }
        List<kecheng> course1=new ArrayList<kecheng>();
        try {
            course1=khz.sea2(coursename);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String shijuantablename=course1.get(0).getShijuantablename();
        bool2=khz.addshijuan(shijuanname,shijuantablename);
        if(bool&&bool2)
        {
            System.out.println("管理员上传试卷成功");
            response.getWriter().print("<script>alert('上传试卷成功');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {

            System.out.println("管理员上传试卷失败");
            response.getWriter().print("<script>alert('上传试卷失败！请提交反馈信息给管理员！');\n" +
                    "        history.back(-1);</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
