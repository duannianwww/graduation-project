package com.gg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/shitiupdateServlet")
public class shitiupdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        boolean b=false;
        bookJDBC khz=new bookJDBC();
        String questionid=request.getSession().getAttribute("questionid").toString();
        int questionid1=Integer.parseInt(questionid);
        String questiontype=request.getSession().getAttribute("questiontype").toString();
        if(questiontype.equals("选择题")||questiontype.equals("多选题"))
        {
            String question=request.getParameter(questionid+"desc");
            String xuana=request.getParameter(questionid+"a");
            String xuanb=request.getParameter(questionid+"b");
            String xuanc=request.getParameter(questionid+"c");
            String xuand=request.getParameter(questionid+"d");
            String xuane=request.getParameter(questionid+"e");
            String xuanf=request.getParameter(questionid+"f");
            String xuang=request.getParameter(questionid+"g");
            String xuanh=request.getParameter(questionid+"h");
            String answer=request.getParameter(questionid+"answer");
            String introduction=request.getParameter(questionid+"introduction");
            b=khz.updatexuanzheorduoxuan(questionid1,question,xuana,xuanb,xuanc,xuand,xuane,xuanf,xuang,xuanh,answer,introduction);
        }
        if(questiontype.equals("判断题")||questiontype.equals("问答题"))
        {
            String question=request.getParameter(questionid+"desc");
            String answer=request.getParameter(questionid+"answer");
            String introduction=request.getParameter(questionid+"introduction");
            b=khz.updatewendaorpanduan(questionid1,question,answer,introduction);
        }
        if(questiontype.equals("填空题"))
        {
            String question=request.getParameter(questionid+"desc");
            String answer=request.getParameter(questionid+"answer");
            String answer2=request.getParameter(questionid+"answer2");
            String answer3=request.getParameter(questionid+"answer3");
            String answer4=request.getParameter(questionid+"answer4");
            String answer5=request.getParameter(questionid+"answer5");
            String answer6=request.getParameter(questionid+"answer6");
            String introduction=request.getParameter(questionid+"introduction");
            b=khz.updatetiankong(questionid1,question,answer,answer2,answer3,answer4,answer5,answer6,introduction);
        }
        if(b)
        {
            System.out.println("管理员修改习题成功");
            response.getWriter().print("<script>alert('修改成功！');\n" +
                    "        history.back(-1);</script>");
        }
        else
        {
            System.out.println("管理员修改习题失败");
            response.getWriter().print("<script>alert('修改失败！信息不能为空！');\n" +
                    "        history.back(-1);</script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
