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
    <c:forEach var="items" items="${requestScope.items}">
        <li>
            <b><fmt:message key="page.items.items.name"/>:</b>
            <c:choose>
                <c:when test="${not empty sessionScope.customer and sessionScope.customer.email == 'admin'}">
                    <a href="/shop/orders?itemsId=${items.item_id}">${items.name}</a> <br>
                </c:when>
                <c:otherwise>
                    <span>${items.name}</span> <br>
                </c:otherwise>
            </c:choose>
            <b><fmt:message key="page.items.count"/>: </b> ${items.quantity_left} <br>
        </li>
    </c:forEach>
</ul>
    <%@ include file="logout.jsp"%>
</body>
</html>
