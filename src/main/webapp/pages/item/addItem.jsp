<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 12.05.2026
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
  <h1>Add New Item</h1>
  <hr/>

  <form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="addItem"/>

    <div class="form-group">
      <label>Item Name:</label>
      <input type="text" name="itemName" required/>
    </div>

    <div class="form-group">
      <label>Description:</label>
      <textarea name="itemDescription" rows="5" cols="40"></textarea>
    </div>

    <div class="form-group">
      <span class="error">${error_msg}</span>
      <span class="success">${success_msg}</span>
    </div>

    <button type="submit">Add Item</button>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/pages/user/items.jsp'">Cancel</button>
  </form>

  <hr/>
  <a href="${pageContext.request.contextPath}/pages/user/items.jsp">Back to Items</a>
  <br/>
  <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
</div>
</body>
</html>
