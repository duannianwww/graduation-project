<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2020/06/13
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>

    <title>添加课程信息页</title>
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
    // $(document).ready(function() {
    //     $("#submit").click(function() {
    //         $.ajax({
    //             url:"updateServlet",
    //             dataType:"json",
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
<body style="position: relative;left:50%;margin-top: 100px;height:180px;margin-left: -130px ">
<div>
    <p><h3>添加课程信息页</h3></p>
    <form style="margin-block-end: 0px" action="updateServlet" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>课程名：</td>
                <td><input id="name" type="text" name="name">
                </td>
            </tr>
            <tr>
                <td>授课老师：</td>
                <td><input id="author" type="text" name="author">
                </td>
            </tr>
            <tr>
                <td>课程类型：</td>
                <td>
                    <select id="press" name="press">
                        <option value="期末不挂">期末不挂</option>
                        <option value="22考研">22考研</option>
                        <option value="计算机">计算机</option>
                        <option value="金融学">金融学</option>
                        <option value="心理学">心理学</option>
                        <option value="期中满分">期中满分</option>
                        <option value="大学英语">大学英语</option>
                        <option value="就业考证">就业考证</option>
                        <option value="精品课程">精品课程</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>课程简介：</td>
                <td><textarea  maxlength="1000" rows="10" cols="" id="introduction" type="reset" name="introduction"></textarea>
                </td>
            </tr>

            <tr>
                <td>课程节数：</td>
                <td><input id="stock" type="text" name="stock">
                </td>
            </tr>
            <tr>
                <td>课程价格：</td>
                <td><input id="price" type="text" name="price">
                </td>
            </tr>
            <tr>
                <td>上传课程封面:</td>
                <td><input type="file" name="image" id="image" onchange="showImg(this)"></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <img id="img" style="height: 150px;width: 150px">
                </td>
            </tr>
            <tr>
                <td colspan="0">
                    <button type="button" onclick="a()">提交</button>
                    <button type="button" onclick="res()">重置</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
