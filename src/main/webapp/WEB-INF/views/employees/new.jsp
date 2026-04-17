<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
<%--@elvariable id="employeeRequest" type="dto"--%>
<%--@elvariable id="genders" type="java.util.List"--%>
<%--@elvariable id="minBirthYear" type="java.lang.Integer"--%>
<%--@elvariable id="maxBirthYear" type="java.lang.Integer"--%>
<%--@elvariable id="educations" type="java.util.List"--%>
<%--@elvariable id="types" type="java.util.List"--%>
<form:form method="post"
           action="${pageContext.request.contextPath}/web/employees"
           modelAttribute="employeeRequest">
    <div>
        <label for="firstName">Имя</label>
        <form:input path="firstName" id="firstName"/>
        <form:errors path="firstName" cssStyle="color:red"/>
    </div>
    <div>
        <label for="lastName">Фамилия</label>
        <form:input path="lastName" id="lastName"/>
        <form:errors path="lastName" cssStyle="color:red"/>
    </div>
    <div>
        <label for="middleName">Отчество</label>
        <form:input path="middleName" id="middleName"/>
        <form:errors path="middleName" cssStyle="color:red"/>
    </div>
    <fieldset style="display: inline-block">
        <legend>Учетная запись</legend>
        <div>
            <label for="email">E-mail</label>
            <form:input path="email" id="email"/>
            <form:errors path="email" cssStyle="color:red"/>
        </div>

        <div>
            <label for="password">Пароль</label>
            <form:password path="password" id="password"/>
            <form:errors path="password" cssStyle="color:red"/>
        </div>

        <div>
            <label for="passwordConfirm">Пароль повторно</label>
            <form:password path="passwordConfirm" id="passwordConfirm"/>
            <form:errors path="passwordConfirm" cssStyle="color:red"/>
        </div>
    </fieldset>
    <div>
        <label>Пол</label>
        <form:radiobuttons
                path="gender"
                items="${genders}"
                itemValue="name"
                itemLabel="label"/>
        <form:errors path="gender" cssStyle="color:red"/>
    </div>
    <div>
        <label for="birthYear">Год рождения</label>
        <form:input
                path="birthYear"
                id="birthYear"
                type="number"
                min="${minBirthYear}"
                max="${maxBirthYear}"
                step="1"/>
        <form:errors path="birthYear" cssStyle="color:red"/>
    </div>
    <div>
        <label for="address">Адрес</label>
        <form:textarea
                path="address"
                id="address"
                rows="4"
                maxlength="100"/>
        <form:errors path="address" cssStyle="color:red"/>
    </div>
    <div>
        <label for="educationId">Образование</label>
        <form:select path="educationId" id="educationId">
            <form:option value=""/>
            <form:options items="${educations}" itemValue="id" itemLabel="label"/>
        </form:select>
        <form:errors path="educationId" cssStyle="color:red"/>
    </div>
    <div>
        <label for="type">Тип сотрудника</label>
        <form:select path="type" id="type">
            <form:option value=""/>
            <form:options items="${types}" itemValue="name" itemLabel="label"/>
        </form:select>
        <form:errors path="type" cssStyle="color:red"/>
    </div>
    <div>
        <form:checkbox path="chiefEditor" id="chiefEditor"/>
        <label for="chiefEditor">Главный редактор</label>
    </div>
    <div>
        <button type="button"
                onclick="location.href='${pageContext.request.contextPath}/web/employees'">
            Отменить
        </button>
        <button type="submit">Сохранить</button>
    </div>
</form:form>
</body>
</html>