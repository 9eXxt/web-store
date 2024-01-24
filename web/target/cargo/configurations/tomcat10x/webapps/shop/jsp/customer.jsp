<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 30.10.2023
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@ include file="language.jsp"%>
<head>
    <title><fmt:message key="page.customer.title"/></title>
</head>
<body>
    <h1><fmt:message key="page.customer.list.customers"/>: </h1>
    <ol>
        <c:forEach var="customer" items="${requestScope.customers}">
            <li>
                <a href="${pageContext.request.contextPath}/orders?customerId=${customer.customer_id}&customerName=${customer.name}">
                        ${customer.name}</a>
            </li>
        </c:forEach>
    </ol>
    <%@ include file="logout.jsp"%>
</body>
</html>