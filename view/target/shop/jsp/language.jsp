<%--
  Created by IntelliJ IDEA.
  User: bogdanromanenko
  Date: 15.11.2023
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'en_GB')}"/>
<fmt:setBundle basename="translations"/>

<div>
  <form action="${pageContext.request.contextPath}/locale" method="post">
    <button type="submit" name="lang" value="en_GB">EN</button>
    <button type="submit" name="lang" value="uk_UA">UA</button>
  </form>
</div>