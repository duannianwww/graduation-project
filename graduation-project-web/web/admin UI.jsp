<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2020/06/11
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>

    <title>教师管理界面</title>
    <link rel="shortcut icon"  href="./img/mk.ico"/>
    <style>
        .login-button { /* 按钮美化 */
            width: 120px; /* 宽度 */
            height: 40px; /* 高度 */
            border-width: 0px; /* 边框宽度 */
            border-radius: 3px; /* 边框半径 */
            background: #1E90FF; /* 背景颜色 */
            cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
            outline: none; /* 不显示轮廓线 */
            font-family: Microsoft YaHei; /* 设置字体 */
            color: white; /* 字体颜色 */
            font-size: 17px; /* 字体大小 */
        }
        .login-button:hover { /* 鼠标移入按钮范围时改变颜色 */
            background: #5599FF;
        }
        .dropbtn {
            width: 120px; /* 宽度 */
            height: 40px; /* 高度 */
            border-width: 0px; /* 边框宽度 */
            border-radius: 3px; /* 边框半径 */
            background: #1E90FF; /* 背景颜色 */
            cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
            outline: none; /* 不显示轮廓线 */
            font-family: Microsoft YaHei; /* 设置字体 */
            color: white; /* 字体颜色 */
            font-size: 17px; /* 字体大小 */
         }

        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        }
        .dropdown-content a {
            color: black;
            text-decoration: none;
            display: block;
        }
        .dropdown-content a:hover {background-color: #f1f1f1}

        .dropdown:hover .dropdown-content {

            display: block;
        }
        .dropdown:hover .dropbtn {
            background: #5599FF;
        }

    </style>
    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
</head>
<body style="position: relative;width:100%;height:100%;margin: 0px">
<div style="display: table-cell;text-align: center;vertical-align: middle;">
    <script type="text/javascript">
        function changeFrameHeight(){
            var ifm= document.getElementById("mainFrame");
            ifm.height=document.documentElement.clientHeight-56;
        }
        window.onresize=function(){ changeFrameHeight();}
        $(function(){changeFrameHeight();});
    </script>
    <img  onclick="location.reload()" style="width: 500px;height: 100px" src="./img/logo.png"/>
    <div class="dropdown">
        <button class="dropbtn">课程管理</button>
        <div class="dropdown-content">
            <a href="addbookmeg.jsp" target="mainFrame"><button class="login-button" type="button">添加课程信息</button></a>
            <a href="update.jsp" target="mainFrame"><button class="login-button" type="button">修改课程信息</button></a>
            <a href="search.jsp" target="mainFrame"><button class="login-button" type="button">浏览课程列表</button></a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">班级学生管理</button>
        <div class="dropdown-content">
            <a href="addusermeg.jsp" target="mainFrame"><button class="login-button" type="button">添加学生信息</button></a>
            <a href="update_user.jsp" target="mainFrame"><button class="login-button" type="button">修改学生信息</button></a>
            <a href="search_user.jsp" target="mainFrame"><button class="login-button" type="button">浏览学生信息</button></a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">试题管理</button>
        <div class="dropdown-content">
            <a href="question_add.jsp" target="mainFrame"><button class="login-button" type="button">添加试题信息</button></a>
            <a href="question_updateVV.jsp" target="mainFrame"><button class="login-button" type="button">修改试题信息</button></a>
            <a href="question_search.jsp" target="mainFrame"><button class="login-button" type="button">浏览试题信息</button></a>
            </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">联系我们</button>
        <div class="dropdown-content">
            <a href="#" target="mainFrame"><button class="login-button" type="button">开发者资料</button></a>
            <a href="#" target="mainFrame"><button class="login-button" type="button">关于我们</button></a>
        </div>
    </div>
        <div class="dropdown">
            <a href="shijuan/Excel模板.xls" download="Excel模板.xls" target="mainFrame"><button class="login-button" type="button" style="width: 150px;">下载Excel模板</button></a>
        </div>
    <a href="admin%20login.jsp"> <button class="login-button"  type="button">返回登录页</button></a>
</div>
<iframe name="mainFrame" scrolling="auto" width="100%" style="border-bottom: 2px;border-right: 2px;border-left: 2px" height="100%" src="home.jsp"></iframe>

</body>
</html>
