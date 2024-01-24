<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 31.10.2023
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="my/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@ include file="language.jsp"%>
<head>
    <title><fmt:message key="page.orders.customer.title"/></title>
</head>
<body>
<h1><fmt:message key="page.orders.customer.orders"/> ${requestScope.customerInfo.name}</h1>
<ol>
<c:forEach var="customerOrder" items="${requestScope.customerOrders}">
  <li>
    <b><fmt:message key="page.orders.customer.order.number"/></b>: ${customerOrder.order_id} <br>
    <b><fmt:message key="page.orders.customer.date.creation"/></b>:
          ${fun:formatLocalDateTime(customerOrder.order_date, "yyyy-MM-dd HH:mm:ss")} <br>
    <b><fmt:message key="page.orders.customer.status.order"/></b>: ${customerOrder.order_status}</li>
</c:forEach>
</ol>
<%@ include file="logout.jsp"%>
</body>
</html>
