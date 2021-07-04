<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/5
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>忘记密码页</title>

    <style>
        .sea
        {
            border:1px solid black;border-collapse: collapse;overflow: auto;
        }
    </style>
</head>
<body>

<div style="position: relative;left:50%;margin-top: 200px;width:300px;height:180px;margin-left: -130px ">
    <table class="sea">
        <th style="align:center" colspan="3">密码修改界面</th>
        <tr>
            <td>您的账号：</td>
            <td colspan="2"><input type="text" name="log" id="log"></td>
        </tr>
        <tr>
            <td>新密码：</td>
            <td colspan="2"><input type="password" name="pas" id="newpas"></td>
        </tr>
        <tr>
            <td>重复密码：</td>
            <td colspan="2"><input type="password" name="pas" id="pasagain"></td>
        </tr>
        <tr>
            <td><button id="submit">提交</button></td>
            <td colspan="2"><button type="button" onclick="res()">重置</button></td>
    </table>
    <button type="button" onclick="javascript :history.back(-1)">返回上一页</button>
</div>
</body>
</html>
