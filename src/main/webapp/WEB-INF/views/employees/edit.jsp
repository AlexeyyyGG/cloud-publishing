<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Обновить сотрудника</h2>

<%--@elvariable id="employeeUpdateRequest" type="dto"--%>
<form:form method="post"
           action="${pageContext.request.contextPath}/web/employees/${employeeId}"
           modelAttribute="employeeUpdateRequest">
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
        <form:password path="passwordConfirm" placeholder="Подтверждение пароля"/>
        <form:errors path="passwordConfirm" cssStyle="color:red"/>
    </div>
    <div>
        <form:input path="gender" placeholder="Пол"/>
        <form:errors path="gender" cssStyle="color:red"/>
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
        <form:input path="type" placeholder="Тип"/>
        <form:errors path="type" cssStyle="color:red"/>
    </div>
    <div>
        <form:checkbox path="chiefEditor"/> Главный редактор
    </div>
    <div>
        <button type="submit">Обновить</button>
    </div>
</form:form>