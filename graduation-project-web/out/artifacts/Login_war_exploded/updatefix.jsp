<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2020/06/14
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.gg.bookJDBC,com.gg.kecheng"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>修改图书信息页</title>

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
        function showImg(obj) {
            var file = $(obj)[0].files[0]; //获取文件信息
            var imgdata = '';
            if (file) {
                var reader = new FileReader(); //调用FileReader
                reader.readAsDataURL(file); //将文件读取为 DataURL(base64)
                reader.onload = function (evt) { //读取操作完成时触发。
                    $("#img").attr('src', evt.target.result) //将img标签的src绑定为DataURL
                };
            } else {
                alert("上传失败");
            }
        }
        // $(document).ready(function() {
        //     $("#submit").click(function() {
        //         $.ajax({
        //             url:"updateVVServlet",
        //             dataType:"json",
        //             mimeType: "multipart/form-data",
        //             data:{
        //                 name:$("#name").val(),
        //                 author:$("#author").val(),
        //                 press:$("#press").val(),
        //                 presstime:$("#presstime").val(),
        //                 introduction:$("#introduction").val(),
        //                 stock:$("#stock").val(),
        //                 desc:$("#desc").val(),
        //                 image:$("#image").val(),
        //                 file:$("#file").val(),
        //             },
        //             type:"post",
        //             success:function (data) {
        //                 if(data.flag)
        //                 {
        //                     alert(data.msg);
        //                 }
        //                 else
        //                 {
        //                     alert(data.msg);
        //                 }
        //             },
        //             error:function (msg) {
        //                 alert(msg);
        //             }
        //         });
        //     })
        // })
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
            if($("#name").val()=="")
            {
                alert("课程名不可为空！");
            }
            else if($("#author").val()=="")
            {
                alert("授课老师不可为空！");
            }
            else if($("#press").val()=="")
            {
                alert("课程类型不可为空！");
            }
            else if($("#presstime").val()=="")
            {
                alert("创建日期不可为空！");
            }
            else if($("#introduction").val()=="")
            {
                alert("课程简介不可为空！");
            }
            else if($("#stock").val()=="")
            {
                alert("课程节数不可为空！");
            }
            else if($("#price").val()=="")
            {
                alert("课程价格不可为空！");
            }
            // else if($("#desc").val()=="")
            // {
            //     alert("视频描述不可为空！");
            // }
            else if($("#image").val()=="")
            {
                alert("课程封面不可为空！");
            }
            // else if($("#file").val()=="")
            // {
            //     alert("视频文件不可为空！");
            // }
            else
            {
                $('form').submit();
            }
        }
    </script>
</head>
<body style="position: relative;left:40%;margin-top: 200px;height:180px;margin-left: -130px ">
<table class="sea">
    <form style="margin-block-end: 0px" action="updateVVServlet" method="post" enctype="multipart/form-data">
        <%
            bookJDBC book=new bookJDBC();
            String bookname=request.getSession().getAttribute("bookname").toString();
            List<kecheng> list =book.sea2(bookname);
            for(kecheng book1:list)
            {%>
        <table>
            <tr>
                <td>课程名：</td>
                <td><input id="name" type="text" name="name" value="<%=book1.getBookname()%>" >
                </td>
            </tr>
            <tr>
                <td>授课老师：</td>
                <td><input id="author" type="text" name="author" value="<%=book1.getAuthor()%>">
                </td>
            </tr>
            <tr>
                <td>课程类型：</td>
                    <td>
                        <select id="press" name="press" value="<%=book1.getPress()%>">
                            <%
                                if(book1.getPress().equals("期末不挂")){
                                    %>
                            <option value="期末不挂" selected>期末不挂</option>
                            <%}else {
                                    %>
                            <option value="期末不挂">期末不挂</option>
                            <%
                            }
                            %>
                            <%
                                if(book1.getPress().equals("22考研")){
                            %>
                            <option value="22考研" selected>22考研</option>
                            <%}else {
                            %>
                            <option value="22考研">22考研</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("计算机")){
                            %>
                            <option value="计算机" selected>计算机</option>
                            <%}else {
                            %>
                            <option value="计算机">计算机</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("金融学")){
                            %>
                            <option value="金融学" selected>金融学</option>
                            <%}else {
                            %>
                            <option value="金融学">金融学</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("心理学")){
                            %>
                            <option value="心理学" selected>心理学</option>
                            <%}else {
                            %>
                            <option value="心理学">心理学</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("期中满分")){
                            %>
                            <option value="期中满分" selected>期中满分</option>
                            <%}else {
                            %>
                            <option value="期中满分">期中满分</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("大学英语")){
                            %>
                            <option value="大学英语" selected>大学英语</option>
                            <%}else {
                            %>
                            <option value="大学英语">大学英语</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("就业考证")){
                            %>
                            <option value="就业考证" selected>就业考证</option>
                            <%}else {
                            %>
                            <option value="就业考证">就业考证</option>
                            <%
                                }
                            %>
                            <%
                                if(book1.getPress().equals("精品课程")){
                            %>
                            <option value="精品课程" selected>精品课程</option>
                            <%}else {
                            %>
                            <option value="精品课程">精品课程</option>
                            <%
                                }
                            %>
                        </select>
                </td>
            </tr>
            <tr>
                <td>创建日期：</td>
                <td><input id="presstime" type="text" name="presstime" value="<%=book1.getPulishtime()%>">
                </td>
            </tr>
            <tr>
                <td>课程简介：</td>
                <td><textarea maxlength="1000" rows="10" cols="" id="introduction" type="reset" name="introduction"><%=book1.getIntroduct()%></textarea>
                </td>
            </tr>
            <tr>
                <td>课程节数:</td>
                <td><input id="stock" type="text" name="stock" value="<%=book1.getStock()%>"></td>
            </tr>
            <tr>
                <td>课程价格:</td>
                <td><input id="price" type="text" name="price" value="<%=book1.getPrice()%>"></td>
            </tr>
            <tr>
                <td>重新上传课程封面:</td>
                <td><input type="file" name="image" id="image" onchange="showImg(this)" value="<%=book1.getImagename()%>"></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <img id="img" src="img/<%=book1.getImagename()%>" style="height: 150px;width: 150px">
                </td>
            </tr>
<%--            <tr>--%>
<%--                <td></td>--%>
<%--                <td><h3>重新上传视频</h3></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>文件描述:</td>--%>
<%--                <td><input type="text" name="desc" id="desc"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>上传文件:</td>--%>
<%--                <td><input type="file" name="file" id="file" accept="video/mp4" value="<%=book1.getFilename()%>"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td colspan="2">本系统仅支持MP4，其他类型的视频文件将在后续进行开发</td>--%>
<%--            </tr>--%>
        </table>
        <button type="button" onclick="a()">提交</button>
        <button type="button" onclick="res()">重置</button>
    </form>
    <%}
    %>
</table>
<button type="button" onclick="javascript :history.back(-1)">返回上一页</button>
</body>
</html>

