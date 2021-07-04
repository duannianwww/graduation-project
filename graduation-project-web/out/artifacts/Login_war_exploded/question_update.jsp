<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/20
  Time: 6:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.gg.bookJDBC"%>
<%@ page import="com.gg.pinlun" %>
<%@ page import="com.gg.shiti" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>习题修改页</title>

    <style>
        .sea tr td
        {
            border:0px solid black;
        }
        .sea
        {
            border-collapse: collapse;overflow: auto;
        }
        .sea td
        {
            width: 200px;
        }
    </style>
    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function res() {
            var inputs = $("input");
            for(var i=0;i <inputs.length;i++){
                if (inputs[i].type== "text"||inputs[i].type== "password")
                {
                    inputs[i].value = '';
                }

            }
            var textareas=$("textarea");
            for(var i=0;i <textareas.length;i++){
                textareas[i].value = '';
            }
        }
    </script>
</head>
<body style="position: relative;left:40%;margin-top: 200px;height:180px;margin-left: -130px ">
<%
    bookJDBC book=new bookJDBC();
    String questionid=request.getSession().getAttribute("questionid").toString();
    int questionid1=Integer.parseInt(questionid);
    List<shiti> list =book.seaquestion3(questionid1);
    request.getSession().setAttribute("questionid",questionid);
    for(shiti book1:list)
    {
        if(book1.getQuestiontype().equals("选择题")||book1.getQuestiontype().equals("多选题"))
        {
%>
<form style="margin-block-end: 0px" action="shitiupdateServlet" method="post">
    <script type="text/javascript">
        function a()
        {
            if($("#<%=book1.getId()%>desc").val()=="")
            {
                alert("试题题干不可为空！");
            }
            else if($("#<%=book1.getId()%>answer").val()=="")
            {
                alert("试题答案不可为空！");
            }
            else
            {
                $('form').submit();
            }
        }
    </script>
    <table class="sea">
        <tr>
            <td></td>
            <td><h3>修改习题</h3></td>
        </tr>
        <tr>
            <td>试题题干:</td>
            <td>
                <textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>desc" type="reset" name="<%=book1.getId()%>desc"><%=book1.getQuestion()%></textarea></td>
        </tr>
        <tr>
            <td>试题类型:</td>
            <td><input id="<%=book1.getId()%>type" name="<%=book1.getId()%>type" value="<%=book1.getQuestiontype()%>" disabled></td>
        </tr>
        <%
            request.getSession().setAttribute("questiontype",book1.getQuestiontype());
        %>
        <tr>
            <td>选项A:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>a" type="reset" name="<%=book1.getId()%>a"><%=book1.getA()%></textarea></td>
        </tr>
        <tr>
            <td>选项B:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>b" type="reset" name="<%=book1.getId()%>b"><%=book1.getB()%></textarea></td>
        </tr>
        <tr>
            <td>选项C:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>c" type="reset" name="<%=book1.getId()%>c"><%=book1.getC()%></textarea></td>
        </tr>
        <tr>
            <td>选项D:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>d" type="reset" name="<%=book1.getId()%>d"><%=book1.getD()%></textarea></td>
        </tr>
        <tr>
            <td>选项E:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>e" type="reset" name="<%=book1.getId()%>e"><%=book1.getE()%></textarea></td>
        </tr>
        <tr>
            <td>选项F:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>f" type="reset" name="<%=book1.getId()%>f"><%=book1.getF()%></textarea></td>
        </tr>
        <tr>
            <td>选项G:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>g" type="reset" name="<%=book1.getId()%>g"><%=book1.getG()%></textarea></td>
        </tr>
        <tr>
            <td>选项H:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>h" type="reset" name="<%=book1.getId()%>h"><%=book1.getH()%></textarea></td>
        </tr>
        <tr>
            <td>答案:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer()%></textarea></td>
        </tr>
        <tr>
            <td>注解:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>introduction" type="reset" name="<%=book1.getId()%>introduction"><%=book1.getQuestionsnote()%></textarea></td>
        </tr>
        <tr>
            <td><button type="button" onclick="a()">提交</button>
                <button type="button" onclick="res()">重置</button></td>
        </tr>
    </table>
</form>
<%}
    if(book1.getQuestiontype().equals("判断题")||book1.getQuestiontype().equals("问答题"))
    {
%>
<form style="margin-block-end: 0px" action="shitiupdateServlet" method="post">
    <script type="text/javascript">
        function a()
        {
            if($("#<%=book1.getId()%>desc").val()=="")
            {
                alert("试题题干不可为空！");
            }
            else if($("#<%=book1.getId()%>answer").val()=="")
            {
                alert("试题答案不可为空！");
            }
            else
            {
                $('form').submit();
            }
        }
    </script>
    <table class="sea">
        <tr>
            <td></td>
            <td><h3>修改习题</h3></td>
        </tr>
        <tr>
            <td>试题题干:</td>
            <td>
                <textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>desc" type="reset" name="<%=book1.getId()%>desc"><%=book1.getQuestion()%></textarea></td>
        </tr>
        <tr>
            <td>试题类型:</td>
            <td><input id="<%=book1.getId()%>type" name="<%=book1.getId()%>type" value="<%=book1.getQuestiontype()%>" disabled></td>
        </tr>
        <%
            request.getSession().setAttribute("questiontype",book1.getQuestiontype());
        %>
        <tr>
            <td>答案:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer()%></textarea></td>
        </tr>
        <tr>
            <td>注解:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>introduction" type="reset" name="<%=book1.getId()%>introduction"><%=book1.getQuestionsnote()%></textarea></td>
        </tr>
        <tr>
            <td><button type="button" onclick="a()">提交</button>
                <button type="button" onclick="res()">重置</button></td>
        </tr>
    </table>
</form>
<%}
    if(book1.getQuestiontype().equals("填空题"))
    {
%>
<form style="margin-block-end: 0px" action="shitiupdateServlet" method="post">
    <script type="text/javascript">
        function a()
        {
            if($("#<%=book1.getId()%>desc").val()=="")
            {
                alert("试题题干不可为空！");
            }
            else if($("#<%=book1.getId()%>answer").val()=="")
            {
                alert("试题答案不可为空！");
            }
            else
            {
                $('form').submit();
            }
        }
    </script>
    <table class="sea">
        <tr>
            <td></td>
            <td><h3>修改习题</h3></td>
        </tr>
        <tr>
            <td>试题题干:</td>
            <td>
                <textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>desc" type="reset" name="<%=book1.getId()%>desc"><%=book1.getQuestion()%></textarea></td>
        </tr>
        <tr>
            <td>试题类型:</td>
            <td><input id="<%=book1.getId()%>type" name="<%=book1.getId()%>type" value="<%=book1.getQuestiontype()%>" disabled></td>
        </tr>
        <%
            request.getSession().setAttribute("questiontype",book1.getQuestiontype());
        %>
        <tr>
        <td>答案1:</td>
        <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer()%></textarea></td>
        </tr>
        <tr>
            <td>答案2:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer2" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer2()%></textarea></td>
        </tr>
        <tr>
            <td>答案3:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer3" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer3()%></textarea></td>
        </tr>
        <tr>
            <td>答案4:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer4" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer4()%></textarea></td>
        </tr>
        <tr>
            <td>答案5:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer5" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer5()%></textarea></td>
        </tr>
        <tr>
            <td>答案6:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>answer6" type="reset" name="<%=book1.getId()%>answer"><%=book1.getAnswer6()%></textarea></td>
        </tr>
        <tr>
            <td>注解:</td>
            <td><textarea maxlength="200" rows="" cols="" id="<%=book1.getId()%>introduction" type="reset" name="<%=book1.getId()%>introduction"><%=book1.getQuestionsnote()%></textarea></td>
        </tr>
        <tr>
            <td><button type="button" onclick="a()">提交</button>
                <button type="button" onclick="res()">重置</button></td>
        </tr>
    </table>
</form>
<%
    }
%>
<%
}
%>
<button type="button" onclick="javascript :history.back(-1)">返回上一页</button>
</body>
</html>
