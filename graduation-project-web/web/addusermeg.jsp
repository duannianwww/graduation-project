<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/13
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加用户信息</title>

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
        $(document).ready(function() {
            $("#submit").click(function() {
                $.ajax({
                    type:"post",
                    url:"addusermegServlet",
                    data:{
                        username:$("#username").val(),
                        useraccout:$("#accout").val(),
                        userpassword:$("#password").val(),
                        userpasswordsafe:$("#passwordsafe").val(),
                        usertype:$("#type").val()
                    },
                    dataType:"json",
                    success:function (data) {
                        alert(data.msg);
                    },
                    error:function (msg) {
                        alert(msg);
                    }
                });
            })

        })
    </script>
</head>
<body style="position: relative;left:50%;margin-top: 200px;height:180px;margin-left: -130px ">
<div>
    <p><h3>添加用户信息页</h3></p>
    <form style="margin-block-end: 0px">
        <table>
            <tr>
                <td>用户名：</td>
                <td><input id="username" type="text" name="name">
                </td>
            </tr>
            <tr>
                <td>用户账号：</td>
                <td><input id="accout" type="text" name="accout">
                </td>
            </tr>
            <tr>
                <td>用户密码：</td>
                <td><input id="password" type="text" name="password">
                </td>
            </tr>
            <tr>
                <td>用户密保：</td>
                <td><input id="passwordsafe" type="text" name="passwordsafe">
                </td>
            </tr>
            <tr>
                <td>用户类型：</td>
                <td><input id="type" type="text" name="type">
                </td>
            </tr>
            <tr>
                <td colspan="0">
                    <button type="button" id="submit">提交</button>
                    <button type="button" onclick="res()">重置</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
