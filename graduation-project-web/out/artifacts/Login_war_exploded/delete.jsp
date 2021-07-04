<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2020/06/14
  Time: 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>删除图书信息页</title>

    <script type="text/javascript" src="resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#submit").click(function() {
                $.ajax({
                    type:"post",
                    url:"deleteServlet",
                    data:{
                        del:$("#del").val(),
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
        function res() {
            var inputs = $("input");
            for(var i=0;i <inputs.length;i++){
                if (inputs[i].type== "text"||inputs[i].type== "file")
                {
                    inputs[i].value = '';
                }
            }
        }
    </script>
</head>
<body style="position: relative;left:50%;margin-top: 200px;height:180px;margin-left: -130px ">
<form>
    <table>
        <tr>
            <td>
                请输入您要删除图书的图书名:<input type="text" name="del" id="del">
            </td>
        </tr>
    </table>
    <button type="button" id="submit">提交</button>
    <button type="button" onclick="res()">重置</button><br/>
</form>
</body>
</html>
