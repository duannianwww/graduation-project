<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/11
  Time: 7:23
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
    <title>视频上传页</title>

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
<body style="position: relative;left:50%;margin-top: 100px;height:180px;margin-left: -130px ">
<div>
    <p><h3>添加视频信息页</h3></p>
    <form style="margin-block-end: 0px" action="VideoUploadServlet" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td><h3>文件上传</h3></td>
            </tr>
            <tr>
                <td>文件描述:</td>
                <td><input type="text" id="desc" name="desc"/></td>

            </tr>
            <tr>
                <td>上传文件:</td>
                <td><input type="file" id="file" name="file" accept="video/mp4"/></td>
            </tr>
            <tr>
                <td colspan="2">本系统仅支持MP4，其他类型的视频文件将在后续进行开发</td>
            </tr>
            <tr>
                <td colspan="0">
                    <button type="button" onclick="a()">提交</button>
                    <button type="button" onclick="res()">重置</button>
                </td>
            </tr>
        </table>
    </form>
    <a href="video_search.jsp"><button type="button">返回上一页</button></a>

</div>
</body>
</html>
