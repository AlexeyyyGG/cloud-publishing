<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title>Добавить сотрудника</title>
</head>
<body>
<h2>Добавить сотрудника</h2>

<%--@elvariable id="employeeRequest" type="dto"--%>
<form:form method="post"
           action="${pageContext.request.contextPath}/web/employees/add"
           modelAttribute="employeeRequest">
    <div>
        <form:input path="firstName" placeholder="Имя"/>
        <form:errors path="firstName" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="lastName" placeholder="Фамилия"/>
        <form:errors path="lastName" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="middleName" placeholder="Отчество"/>
        <form:errors path="middleName" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="email" placeholder="Email"/>
        <form:errors path="email" cssStyle="color:red"/>
    </div>
    <div>
        <form:password path="password" placeholder="Пароль"/>
        <form:errors path="password" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="birthYear" placeholder="Год рождения"/>
        <form:errors path="birthYear" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="address" placeholder="Адрес"/>
        <form:errors path="address" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="education" placeholder="Образование"/>
        <form:errors path="education" cssStyle="color:red"/>
    </div>
    <div>
        <form:checkbox path="isChiefEditor"/> Главный редактор
    </div>
    <div>
        <button type="submit">Добавить</button>
    </div>
</form:form>
</body>
</html>