<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/5
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>首页</title>

</head>
<body>
<%String adminusername=request.getSession().getAttribute("adminusername").toString();%>
欢迎登录！<%=adminusername%><br/>
</body>
</html>
