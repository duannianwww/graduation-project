<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/11
  Time: 6:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.gg.bookJDBC,com.gg.kecheng"%>
<%@ page import="com.gg.video" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>修改视频信息页</title>

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
                    alert("视频描述不可为空！");
            }
            else if($("#file").val()=="")
            {
                    alert("视频文件不可为空！");
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

        String videoname=request.getSession().getAttribute("videoname").toString();
        String videotablename=request.getSession().getAttribute("videotablename").toString();
        List<video> list =book.seavideo3(videotablename,videoname);
        for(video book1:list)
        {
    %>
    <form style="margin-block-end: 0px" action="VideoUpdateServlet" method="post" enctype="multipart/form-data">
        <table class="sea">
        <tr>
            <td></td>
            <td><h3>重新上传视频</h3></td>
        </tr>
        <tr>
            <td>文件描述:</td>
            <td><input type="text" name="desc" id="desc" value="<%=book1.getDesc()%>"/></td>
        </tr>
        <tr>
            <td>上传文件:</td>
            <td><input type="file" name="file" id="file" accept="video/mp4" value="<%=book1.getVideoname()%>"/></td>
        </tr>
        <tr>
            <td colspan="2">本系统仅支持MP4，其他类型的视频文件将在后续进行开发</td>
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
