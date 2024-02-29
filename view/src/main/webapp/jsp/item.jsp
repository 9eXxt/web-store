<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 30.10.2023
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%@ include file="language.jsp"%>
<head>
    <title><fmt:message key="page.items.title"/></title>
</head>
<body>
<h1><fmt:message key="page.items.items"/>:</h1>
<ul>
    <c:forEach var="item" items="${requestScope.items}">
        <li>
            <b><fmt:message key="page.items.items.name"/>:</b>
            <c:choose>
                <c:when test="${not empty sessionScope.customer and sessionScope.customer.email == 'admin'}">
                    <a href="/shop/orders?itemId=${item.item_id}">${item.name}</a> <br>
                </c:when>
                <c:otherwise>
                    <span>${item.name}</span> <br>
                </c:otherwise>
            </c:choose>
            <b><fmt:message key="page.items.count"/>: </b> ${item.quantity_left} <br>
        </li>
    </c:forEach>
</ul>
</body>
</html>
