<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 12.05.2026
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <h1>Register</h1>
    <hr/>
    <input type="hidden" name="command" value="register"/>
    Name: <input type="text" name="username" value=""/>
    <br/>
    Password: <input type="password" name="password" value=""/>
    <br/>
    ${error_msg}
    <hr/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>
