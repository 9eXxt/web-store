<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 31.10.2023
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="my/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<%@ include file="language.jsp"%>
<head>
    <title><fmt:message key="page.orders.item.title"/></title>
</head>
<body>
<h1><fmt:message key="page.orders.item.orders.item"/> ${requestScope.itemInfo.get(param.itemId).name}</h1>
<ol>
    <c:forEach var="itemOrder" items="${requestScope.itemOrders}">
    <li>
        <b><fmt:message key="page.orders.item.full.name"/>:</b>
            ${requestScope.customerInfo.get(itemOrder.customer_id).name} <br>
        <b><fmt:message key="page.orders.item.order.number"/></b>: ${itemOrder.order_id} <br>
        <b><fmt:message key="page.orders.item.date.creation"/></b>:
            ${fun:formatLocalDateTime(itemOrder.order_date,"yyyy-MM-dd HH:mm:ss")}<br>
        <b><fmt:message key="page.orders.item.status.order"/></b>: ${itemOrder.order_status} <br>
    </li>
    </c:forEach>
</ol>
<%@ include file="logout.jsp"%>
</body>
</html>
