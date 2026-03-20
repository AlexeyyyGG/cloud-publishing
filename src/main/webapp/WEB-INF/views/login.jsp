<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title>Вход</title>
</head>
<body>
<h2>Вход в систему</h2>

<%--@elvariable id="loginRequest" type="dto"--%>
<form:form method="post" action="${pageContext.request.contextPath}/login" modelAttribute="loginRequest">
    <div>
        <form:input path="username" placeholder="Email"/>
        <form:errors path="username" cssStyle="color:red"/>
    </div>
    <div>
        <form:password path="password" placeholder="Пароль"/>
        <form:errors path="password" cssStyle="color:red"/>
    </div>
    <button type="submit">Войти</button>
</form:form>

</body>
</html>