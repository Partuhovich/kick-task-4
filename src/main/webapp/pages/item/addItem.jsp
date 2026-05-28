<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Add Item</title>
</head>
<body>
<h1>Add New Item</h1>

<c:if test="${not empty error_msg}">
  <div style="color: red;">${error_msg}</div>
</c:if>

<c:if test="${not empty success_msg}">
  <div style="color: green;">${success_msg}</div>
</c:if>

<hr/>

<form action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="add_item"/>

  <label for="itemName">Item Name:</label>
  <input type="text" id="itemName" name="itemName" required="required"/>
  <br/><br/>

  <label for="itemDescription">Description:</label><br/>
  <textarea id="itemDescription" name="itemDescription" rows="5" cols="40"></textarea>
  <br/><br/>

  <input type="submit" value="Add Item"/>
  <input type="button" value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/controller?command=get_all_items'"/>
</form>

<br/>
<a href="${pageContext.request.contextPath}/controller?command=get_all_items">Back to Items</a>
</body>
</html>