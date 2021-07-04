<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/16
  Time: 5:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.gg.bookJDBC,com.gg.kecheng,com.gg.video"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>增加评论页</title>

    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function res() {
            var inputs = $("input");
            for(var i=0;i <inputs.length;i++){
                if (inputs[i].type== "text")
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
            if($("#username").val()=="")
            {
                alert("发送用户不可为空！");
            }
            else if($("#introduction").val()=="")
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
<body style="position: relative;left:50%;margin-top: 100px;height:180px;margin-left: -130px ">
<div>
    <p><h3>添加评论信息页</h3></p>
    <form style="margin-block-end: 0px" action="addpinlunServlet" method="post">
        <table>
            <tr>
                <td>发送用户:</td>
                <td><input type="text" id="username" name="username"/></td>

            </tr>
            <tr>
                <td>评论内容:</td>
                <td><textarea maxlength="200" rows="" cols="" id="introduction" type="reset" name="introduction"></textarea></td>
            </tr>
            <tr>
                <td colspan="0">
                    <button type="button" onclick="a()">提交</button>
                    <button type="button" onclick="res()">重置</button>
                </td>
            </tr>
        </table>
    </form>
    <a href="video_pinlunliulan.jsp"><button type="button">返回上一页</button></a>
</div>
</body>
</html>
