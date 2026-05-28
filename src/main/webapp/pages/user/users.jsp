<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Users List</h1>

<c:if test="${not empty success_msg}">
    <div style="color: green;">${success_msg}</div>
</c:if>

<c:if test="${not empty error_msg}">
    <div style="color: red;">${error_msg}</div>
</c:if>

<hr/>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Admin</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.isAdmin ? 'Yes' : 'No'}</td>
            <td>
                <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
                    <input type="hidden" name="command" value="delete_user"/>
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete user: ${user.username}?')"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty users}">
        <tr>
            <td colspan="4">No users found</td>
        </tr>
    </c:if>
    </tbody>
</table>

<br/>
<a href="${pageContext.request.contextPath}/index.jsp">Back to Main</a>
</body>
</html>