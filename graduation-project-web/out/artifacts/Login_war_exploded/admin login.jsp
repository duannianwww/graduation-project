<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2020/06/11
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>教师登录页面</title>
    <style>
        <%--            修改按钮样式--%>
        .update {
            box-shadow:inset 0px 1px 0px 0px #cf866c;
            background:linear-gradient(to bottom, #7892c2 5%, #476e9e 100%);
            background-color:#7892c2;
            border-radius:3px;
            border:1px solid #4e6096;
            display:inline-block;
            cursor:pointer;
            color:#ffffff;
            font-family:Arial;
            font-size:13px;
            padding:6px 6px;
            text-decoration:none;
            text-shadow:0px 1px 0px #283966;
        }
        .update:hover {
            background:linear-gradient(to bottom, #476e9e 5%, #7892c2 100%);
            background-color:#476e9e;
        }
        .update:active {
            position:relative;
            top:1px;
        }
        /*删除按钮样式*/
        .delete {
            box-shadow:inset 0px 1px 0px 0px #cf866c;
            background:linear-gradient(to bottom, #d0451b 5%, #bc3315 100%);
            background-color:#d0451b;
            border-radius:3px;
            border:1px solid #942911;
            display:inline-block;
            cursor:pointer;
            color:#ffffff;
            font-family:Arial;
            font-size:13px;
            padding:6px 6px;
            text-decoration:none;
            text-shadow:0px 1px 0px #854629;
        }
        .delete:hover {
            background:linear-gradient(to bottom, #bc3315 5%, #d0451b 100%);
            background-color:#bc3315;
        }
        .delete:active {
            position:relative;
            top:1px;
        }

    </style>
    <link rel="shortcut icon"  href="./img/mk.ico"/>
    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function() {
                $.ajax({
                    url:"validateAdminLogin",
                    dataType:"json",
                    data:{
                        log:$("#log").val(),
                        pas:$("#pas").val(),
                    },
                    type:"post",
                    success:function (data) {
                        if(data.flag)
                        {
                            alert(data.msg);
                            window.location.href ="admin UI.jsp"
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
    <style>

        * {
            padding: 0;
            margin: 0;
        }

        html {
            height: 100%;
        }

        body {
            height: 100%;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            /*实现块级元素垂直居中*/
            justify-content: center;
            align-items: center;
        }

        .image img {
            /*实现图片自适应屏幕大小*/
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: -10;
        }

        .login {
            width: 300px;
            background-color: rgba(41, 45, 62, 0.7);
            color: aliceblue;
            border-radius: 8px;
            padding: 50px;
        }

        .login .header {
            text-align: center;
            font-size: 30px;
            line-height: 100px;
        }

        .login .input input {
            background-color: rgb(41, 45, 62);
            border: 0px;
            width: 100%;
            text-align: center;
            font-size: 15px;
            color: aliceblue;
            outline: none;
        }

        .login .input input::placeholder {
            text-transform: uppercase;
        }

        .login .input-border {
            /*实现颜色从左到右渐变效果*/
            background-image: linear-gradient(to right, #e8198b, #3B65BB);
            height: 45px;
            width: 100%;
            margin-bottom: 20px;
            border-radius: 25px;
            display: flex;
            justify-content: center;
            align-items: center;
            transition: .3s;
        }

        .login .input-border .border {
            /*这里使用了css3的calc()方法用于自动计算宽高*/
            height: calc(100% - 4px);
            width: calc(100% - 4px);
            border-radius: 25px;
            font-size: 14px;
        }

        .login .btn-login {
            width: 60%;
            border: 2px solid #3B65BB;
            margin: 0 auto;
            text-align: center;
            line-height: 40px;
            margin-bottom: 20px;
            text-transform: uppercase;
            border-radius: 25px;
            cursor: pointer;
            transition: .3s;
        }

        .login .btn-login:hover {
            background-color: #3B65BB;
        }

    </style>
</head>
<body>
<div class="image">
    <!-- 图片大家可以自选电脑里任意，以写好调整图片大小css代码 -->
    <img src="img/new_login_background_1475f40.jpg" alt="">
</div>
<div class="login">
    <div class="header">
        教师登录页面
    </div>
    <div class="input">
        <div class="input-border">
            <input type="text" class="border" name="log" id="log" placeholder="请输入账号">
        </div>
        <div class="input-border">
            <input type="password" class="border" name="pas" id="pas" placeholder="请输入密码">
        </div>
    </div>
    <div class="action" action="login" method="post">
        <div class="btn-login"><p id="submit">登录</p></div>
    </div>
    <a href="index.jsp" style="text-decoration-line: none;color:#ffffff;margin: 30px">
    <div class="action">
        <div class="btn-login">返回首页</div>
    </div>
    </a>
</div>
</body>
</html>
