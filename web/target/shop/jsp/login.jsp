<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 09.11.2023
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@ include file="language.jsp"%>
<head>
    <title> <fmt:message key="page.login.title"/> </title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
  <label for="email"> <fmt:message key="page.login.email"/>:
    <input type="text" name="email" id="email" value="${param.email}" required>
  </label><br>
  <label for="password"> <fmt:message key="page.login.password"/>:
    <input type="password" name="password" id="password" required>
  </label><br>
  <button type="submit">
    <fmt:message key="page.login.login.button"/>
  </button>
  <a href="${pageContext.request.contextPath}/registration">
      <button type="button"><fmt:message key="page.login.register.button"/></button>
  </a>
  <c:if test="${param.error != null}">
    <div style="color: red">
      <span><fmt:message key="page.login.error"/></span>
    </div>
  </c:if>
</form>
</body>
</html>
