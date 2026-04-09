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
           action="${pageContext.request.contextPath}/web/employees"
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
    <fieldset style="display: inline-block">
        <legend>Учётная запись</legend>
        <div>
            <form:input path="email" placeholder="Email"/>
            <form:errors path="email" cssStyle="color:red"/>
        </div>
        <div>
            <form:password path="password" placeholder="Пароль"/>
            <form:errors path="password" cssStyle="color:red"/>
        </div>
    </fieldset>
    <div>
        <form:radiobuttons
                path="gender"
                items="${genders}"
                itemValue="name"
                itemLabel="label"/>
        <form:errors path="gender" cssStyle="color:red"/>
    </div>
    <div>
        <form:select path="birthYear">
            <form:option value="" label="Выберите год"/>
            <form:options items="${birthYears}"/>
        </form:select>
        <form:errors path="birthYear" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="address" placeholder="Адрес"/>
        <form:errors path="address" cssStyle="color:red"/>
    </div>
    <div>
        <form:select path="educationId">
            <form:option value="" label="Выберите образование"/>
            <form:options items="${educations}" itemValue="id" itemLabel="label"/>
        </form:select>
        <form:errors path="educationId" cssStyle="color:red"/>
    </div>
    <div>
        <form:select path="type">
            <form:option value="" label="Выберите тип"/>
            <form:options items="${types}" itemValue="name" itemLabel="label"/>
        </form:select>
        <form:errors path="type" cssStyle="color:red"/>
    </div>
    <div>
        <form:checkbox path="chiefEditor"/> Главный редактор
    </div>
    <div>
        <button type="submit">Добавить</button>
    </div>
</form:form>
</body>
</html>