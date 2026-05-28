<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Items</title>
</head>
<body>
<h1>Items List</h1>

<c:if test="${not empty success_msg}">
    <div style="color: green;">${success_msg}</div>
</c:if>

<c:if test="${not empty error_msg}">
    <div style="color: red;">${error_msg}</div>
</c:if>

<hr/>

<a href="${pageContext.request.contextPath}/pages/item/addItem.jsp">Add New Item</a>
<br/><br/>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Owner ID</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>${item.ownerId}</td>
            <td>
                <form action="${pageContext.request.contextPath}/controller" method="get" style="display:inline;">
                    <input type="hidden" name="command" value="get_item"/>
                    <input type="hidden" name="itemId" value="${item.id}"/>
                    <input type="submit" value="View"/>
                </form>
                <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
                    <input type="hidden" name="command" value="delete_item"/>
                    <input type="hidden" name="itemId" value="${item.id}"/>
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure?')"/>
                </form>
                <form action="${pageContext.request.contextPath}/pages/item/editItem.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="itemId" value="${item.id}"/>
                    <input type="hidden" name="itemName" value="${item.name}"/>
                    <input type="hidden" name="itemDescription" value="${item.description}"/>
                    <input type="submit" value="Edit"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty items}">
        <tr>
            <td colspan="5">No items found</td>
        </tr>
    </c:if>
    </tbody>
</table>

<br/>
<a href="${pageContext.request.contextPath}/index.jsp">Back to Main</a>
</body>
</html>