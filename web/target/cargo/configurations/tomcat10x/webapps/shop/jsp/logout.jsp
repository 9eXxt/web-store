<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 10.11.2023
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="translations"/>

<div>
  <c:if test="${sessionScope.customer != null}">
    <form action="${pageContext.request.contextPath}/logout" method="post">
      <button type="submit"><fmt:message key="button.logout"/></button>
    </form>
  </c:if>
</div>