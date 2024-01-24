<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 31.10.2023
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@ include file="language.jsp"%>
<head>
    <title><fmt:message key="page.registration.title"/></title>
</head>
<body>
  <img src="${pageContext.request.contextPath}/images/IMG_4144.jpeg" alt="Image" height="720" width="1028" >
  <form action="<c:url value="/registration"/>" method="POST">
    <label for="first_name"><fmt:message key="page.registration.first.name"/>:
      <input type="text" name="first_name" id="first_name">
    </label> <br>
    <label for="last_name"><fmt:message key="page.registration.last.name"/>:
      <input type="text" name="last_name" id="last_name">
    </label> <br>
    <label for="phone_number"><fmt:message key="page.registration.phone.number"/>:
      <input type="text" name="phone_number" id="phone_number" maxlength="15">
    </label> <br>
    <label for="email"><fmt:message key="page.registration.email"/>:
      <input type="email" name="email" id="email">
    </label> <br>
    <label for="address"><fmt:message key="page.registration.address"/>:
      <input type="text" name="address" id="address">
    </label> <br>
    <label for="password"><fmt:message key="page.registration.password"/>:
      <input type="password" name="password" id="password">
    </label> <br>
    <button type="submit"><fmt:message key="page.registration.send.button"/></button>
  </form>
</body>
</html>
