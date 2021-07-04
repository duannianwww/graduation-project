<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/21
  Time: 6:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查找试卷修改页</title>

    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function() {
                $.ajax({
                    url:"updateVServlet",
                    dataType:"json",
                    data:{
                        shijuanname:$("#shijuanname").val(),
                    },
                    type:"post",
                    success:function (data) {
                        if(data.flag)
                        {
                            alert(data.msg);
                            window.location.href ="question_updatefix.jsp"
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
<body style="position: relative;left:45%;margin-top: 200px;height:180px;margin-left: -130px ">
<table>
    <tr>
        请输入要修改的目标试卷名：<input type="text"name="shijuanname" id="shijuanname">
    </tr>
    <button type="button" id="submit">提交</button>
    <button type="button" onclick="res()">重置</button><br/>
</table>
</body>
</html>
