<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/16
  Time: 5:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.gg.bookJDBC"%>
<%@ page import="com.gg.pinlun" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>修改评论页</title>

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
        function a()
        {
            if($("#desc").val()=="")
            {
                alert("发送用户不可为空！");
            }
            else if($("#file").val()=="")
            {
                alert("评论内容不可为空！");
            }
            else
            {
                $('form').submit();
            }
        }
    </script>
</head>
<body style="position: relative;left:40%;margin-top: 200px;height:180px;margin-left: -130px ">
<%
    bookJDBC book=new bookJDBC();
    String pinlunid=request.getSession().getAttribute("pinlunid").toString();
    String pinluntablename=request.getSession().getAttribute("pinluntablename").toString();
    List<pinlun> list =book.seapinlun3(pinluntablename,pinlunid);
    for(pinlun book1:list)
    {
%>
<form style="margin-block-end: 0px" action="pinlunupdateVServlet" method="post">
    <table class="sea">
        <tr>
            <td></td>
            <td><h3>修改评论</h3></td>
        </tr>
        <tr>
            <td>发送用户:</td>
            <td><input type="text" name="desc" id="desc" value="<%=book1.getUsername()%>"/></td>
        </tr>
        <tr>
            <td>评论内容:</td>
            <td><textarea maxlength="200" rows="" cols="" id="introduction" type="reset" name="introduction"><%=book1.getComment()%></textarea></td>
        </tr>
        <tr>
            <td><button type="button" onclick="a()">提交</button>
                <button type="button" onclick="res()">重置</button></td>
        </tr>
    </table>
</form>
<%}
%>
<button type="button" onclick="javascript :history.back(-1)">返回上一页</button>
</body>
</html>
