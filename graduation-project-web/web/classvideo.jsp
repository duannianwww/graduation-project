<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/12
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程视频上传页</title>

</head>
<body>
<script type="text/javascript">
    $(document).ready(function() {
        $("#submit").click(function() {
                    alert("${info}");})
    })
</script>
<form action="upload" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td></td>
            <td><h1>文件上传</h1></td>

        </tr>
        <tr>
            <td>文件描述:</td>
            <td><input type="text" name="desc"/></td>

        </tr>
        <tr>
            <td>上传文件:</td>
            <td><input type="file" name="file" accept="video/mp4"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="上传文件"/></td>
        </tr>
        <tr>
            <td colspan="2">本系统仅支持MP4，其他类型的视频文件将在后续进行开发</td>
        </tr>
    </table>
</form>
</body>
</html>
