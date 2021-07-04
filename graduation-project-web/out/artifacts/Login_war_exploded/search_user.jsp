<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/7
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.gg.bookJDBC,com.gg.user"%>
<%@ page import="com.gg.userlogin" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>搜索用户信息页</title>

    <style>
        *{margin: 0;padding: 0;}
        table {
            *border-collapse: collapse; /* IE7 and lower */
            border-spacing: 0;
            width: 70%;
        }
        /*========bordered table========*/
        .sea {
            border: solid #ccc 1px;
            -moz-border-radius: 6px;
            -webkit-border-radius: 6px;
            border-radius: 6px;
            -webkit-box-shadow: 0 1px 1px #ccc;
            -moz-box-shadow: 0 1px 1px #ccc;
            box-shadow: 0 1px 1px #ccc;
        }

        .sea tr {
            -o-transition: all 0.1s ease-in-out;
            -webkit-transition: all 0.1s ease-in-out;
            -moz-transition: all 0.1s ease-in-out;
            -ms-transition: all 0.1s ease-in-out;
            transition: all 0.1s ease-in-out;
        }
        .sea .highlight,
        .sea tr:hover {
            background: #fbf8e9;
        }
        .sea td,
        .sea th {
            border-left: 1px solid #ccc;
            border-top: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        .sea th {
            background-color: #dce9f9;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
            background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image: linear-gradient(top, #ebf3fc, #dce9f9);
            filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0, startColorstr=#ebf3fc, endColorstr=#dce9f9);
            -ms-filter: "progid:DXImageTransform.Microsoft.gradient (GradientType=0, startColorstr=#ebf3fc, endColorstr=#dce9f9)";
            -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
            -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;
            box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
            border-top: none;
            text-shadow: 0 1px 0 rgba(255,255,255,.5);
        }
        .sea td:first-child,
        .sea th:first-child {
            border-left: none;
        }
        .sea th:first-child {
            -moz-border-radius: 6px 0 0 0;
            -webkit-border-radius: 6px 0 0 0;
            border-radius: 6px 0 0 0;
        }
        .sea th:last-child {
            -moz-border-radius: 0 6px 0 0;
            -webkit-border-radius: 0 6px 0 0;
            border-radius: 0 6px 0 0;
        }
        .sea tr:last-child td:first-child {
            -moz-border-radius: 0 0 0 6px;
            -webkit-border-radius: 0 0 0 6px;
            border-radius: 0 0 0 6px;
        }
        .sea tr:last-child td:last-child {
            -moz-border-radius: 0 0 6px 0;
            -webkit-border-radius: 0 0 6px 0;
            border-radius: 0 0 6px 0;
        }
        /*----------------------*/
        .zebra td,
        .zebra th {
            padding: 10px;
            border-bottom: 1px solid #f2f2f2;
        }
        .zebra .alternate,
        .zebra tbody tr:nth-child(even) {
            background: #f5f5f5;
            -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
            -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;
            box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
        }
        .zebra th {
            text-align: left;
            text-shadow: 0 1px 0 rgba(255,255,255,.5);
            border-bottom: 1px solid #ccc;
            background-color: #eee;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#f5f5f5), to(#eee));
            background-image: -webkit-linear-gradient(top, #f5f5f5, #eee);
            background-image: -moz-linear-gradient(top, #f5f5f5, #eee);
            background-image: -ms-linear-gradient(top, #f5f5f5, #eee);
            background-image: -o-linear-gradient(top, #f5f5f5, #eee);
            background-image: linear-gradient(top, #f5f5f5, #eee);
            filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0, startColorstr=#f5f5f5, endColorstr=#eeeeee);
            -ms-filter: "progid:DXImageTransform.Microsoft.gradient (GradientType=0, startColorstr=#f5f5f5, endColorstr=#eeeeee)";
        }
        .zebra th:first-child {
            -moz-border-radius: 6px 0 0 0;
            -webkit-border-radius: 6px 0 0 0;
            border-radius: 6px 0 0 0;
        }
        .zebra th:last-child {
            -moz-border-radius: 0 6px 0 0;
            -webkit-border-radius: 0 6px 0 0;
            border-radius: 0 6px 0 0;
        }
        .zebra tfoot td {
            border-bottom: 0;
            border-top: 1px solid #fff;
            background-color: #f1f1f1;
        }
        .zebra tfoot td:first-child {
            -moz-border-radius: 0 0 0 6px;
            -webkit-border-radius: 0 0 0 6px;
            border-radius: 0 0 0 6px;
        }
        .zebra tfoot td:last-child {
            -moz-border-radius: 0 0 6px 0;
            -webkit-border-radius: 0 0 6px 0;
            border-radius: 0 0 6px 0;
        }
        #sumpagestyle{
            margin: 0px;
        }
        #pageStyle{
            display:inline-block;
            width:32px;
            height:32px;
            border:1px solid #CCC;
            line-height:32px;
            text-align:center;
            color:#999;
            margin-top:20px;
            text-decoration:none;

        }
        #pageStyle:hover{
            background-color:#CCC;
        }
        #pageStyle.active{
            background-color:#0CF;
            color:#ffffff;
        }
    </style>
    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function(){//页码函数
            var $table = $('table');
            var currentPage = 0;//当前页默认值为0
            var pageSize = 5;//每一页显示的数目
            $table.bind('paging',function(){
                $table.find('tbody tr').hide().slice(currentPage*pageSize,(currentPage+1)*pageSize).show();
            });
            var sumRows = $table.find('tbody tr').length;
            var sumPages = Math.ceil(sumRows/pageSize);//总页数

            var $pager = $('<div class="page"></div>');  //新建div，放入a标签,显示底部分页码
            $('<span id="sumpagestyle">'+"总共有"+sumPages+"页"+'</span>').appendTo($pager)
            $pager.append(" ");
            for(var pageIndex = 0 ; pageIndex<sumPages ; pageIndex++){

                $('<a href="#" id="pageStyle" onclick="changCss(this)"><span>'+(pageIndex+1)+'</span></a>').bind("click",{"newPage":pageIndex},function(event){
                    currentPage = event.data["newPage"];
                    $table.trigger("paging");
                    //触发分页函数
                }).appendTo($pager);
                $pager.append(" ");
            }
            $pager.insertAfter($table);
            $table.trigger("paging");

            //默认第一页的a标签效果
            var $pagess = $('#pageStyle');
            $pagess[0].style.backgroundColor="#006B00";
            $pagess[0].style.color="#ffffff";
        });

        //a链接点击变色，再点其他回复原色
        function changCss(obj){
            var arr = document.getElementsByTagName("a");
            for(var i=0;i<arr.length;i++){
                if(obj==arr[i]){       //当前页样式
                    obj.style.backgroundColor="#006B00";
                    obj.style.color="#ffffff";
                }
                else
                {
                    arr[i].style.color="";
                    arr[i].style.backgroundColor="";
                }
            }
        }
    </script>
</head>
<body style="position: relative;left:20%;margin-top: 200px;height:180px;margin-left: -130px ">
<table class="sea">
    <thead>
    <tr>
        <th>用户名</th>
        <th>用户账号</th>
        <th>用户密码</th>
        <th>用户类型</th>
        <th>功能</th>
        <th>功能</th>
    </tr>
    </thead>
    <tbody>


    <%
        userlogin book=new userlogin();
        List<user> list = book.select();
        for(user book1:list)
        {%>
    <tr>
        <td><%=book1.getUsername()%></td>
        <td><%=book1.getUseraccout()%></td>
        <td><%=book1.getUserpassword()%></td>
        <td><%=book1.getUsertybe()%></td>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#<%=book1.getUsername()%>update").click(function() {
                    $.ajax({
                        url:"updateusermegServlet",
                        dataType:"json",
                        data:{
                            username:"<%=book1.getUsername()%>",
                        },
                        type:"post",
                        success:function (data) {
                            if(data.flag)
                            {
                                alert(data.msg);
                                window.location.href ="updatefix_user.jsp"
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
            $(document).ready(function() {
                $("#<%=book1.getUsername()%>delete").click(function() {
                    $.ajax({
                        type:"post",
                        url:"deleteusermegServlet",
                        data:{
                            del:"<%=book1.getUsername()%>",
                        },
                        dataType:"json",
                        success:function (data) {
                            alert(data.msg);
                            location.reload();
                        },
                        error:function (msg) {
                            alert(msg);
                        }
                    });
                })
            })

        </script>
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
        <td><button type="button" class="update" id="<%=book1.getUsername()%>update">修改</button></td>
        <td><button type="button" class="delete" id="<%=book1.getUsername()%>delete">删除</button></td>
    </tr>
    <%}
    %>
    </tbody>
</table>
</body>
</html>
