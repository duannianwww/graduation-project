<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/29
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@ page import="com.gg.userlogin" %>
<%@ page import="com.gg.user" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>修改用户信息页</title>

    <style>
        .sea tr td
        {
            border:1px solid black;
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
        $(document).ready(function() {
            $("#submit").click(function() {
                $.ajax({
                    url:"userupdateVVServlet",
                    dataType:"json",
                    data:{
                        username:$("#username").val(),
                        useraccout:$("#useraccout").val(),
                        userpassword:$("#userpassword").val(),
                        usertype:$("#usertype").val(),
                    },
                    type:"post",
                    success:function (data) {
                        if(data.flag)
                        {
                            alert(data.msg);
                        }
                        else
                        {
                            alert(data.msg);
                        }
                    },
                    error:function (msg) {
                        alert(msg);
                    }
                });
            })
        })
        function res() {
            var inputs = $("input");
            for(var i=0;i <inputs.length;i++){
                if (inputs[i].type== "text"||inputs[i].type== "password")
                {
                    inputs[i].value = '';
                }
            }
        }
    </script>
</head>
<body style="position: relative;left:40%;margin-top: 200px;height:180px;margin-left: -130px ">
<table class="sea">
    <form style="margin-block-end: 0px">
        <%
            userlogin user=new userlogin();
            String username=request.getSession().getAttribute("username").toString();
            List<user> list =user.sea2(username);
            for(user user1:list)
            {%>
        <table>
            <tr>
                <td>用户名：</td>
                <td><input id="username" type="text" name="username" value="<%=user1.getUsername()%>" >
                </td>
            </tr>
            <tr>
                <td>用户账号：</td>
                <td><input id="useraccout" type="text" name="useraccout" value="<%=user1.getUseraccout()%>">
                </td>
            </tr>
            <tr>
                <td>用户密码：</td>
                <td><input id="userpassword" type="text" name="userpassword" value="<%=user1.getUserpassword()%>">
                </td>
            </tr>
            <tr>
                <td>用户类型：</td>
                <td><input id="usertype" type="text" name="usertype" value="<%=user1.getUsertybe()%>">
                </td>
            </tr>
        </table>
        <button type="button" id="submit">提交</button>
        <button type="button" onclick="res()">重置</button>
    </form>
    <%}
    %>
</table>
<button type="button" onclick="javascript :history.back(-1)">返回上一页</button>
</body>
</html>
