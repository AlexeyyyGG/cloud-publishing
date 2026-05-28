<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
<%@ include file="/WEB-INF/views/layout/navigation.jsp" %>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<%--@elvariable id="employees" type="java.util.List"--%>
<main>
    <div style="display: inline-block;">
        <div style="text-align: right; margin-bottom: 10px;">
            <c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
                <a href="${pageContext.request.contextPath}/web/employees/new">
                    Новый сотрудник
                </a>
            </c:if>
        </div>
        <table border="1">
            <tr>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Пол</th>
                <th>Год рождения</th>
                <th>Тип сотрудника</th>
                <c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
                    <th></th>
                </c:if>
            </tr>
            <c:forEach var="emp" items="${employees}">
                <tr>
                    <td>${emp.firstName}</td>
                    <td>${emp.lastName}</td>
                    <td>${emp.gender.shortLabel}</td>
                    <td>${emp.birthYear}</td>
                    <td>${emp.type.label}</td>
                    <c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
                        <td>
                            <a href="${pageContext.request.contextPath}/web/employees/${emp.id}/edit">
                                Редактировать
                            </a>
                            <form action="${pageContext.request.contextPath}/web/employees/${emp.id}" method="post" style="display:inline;">
                                <input type="hidden" name="_method" value="delete"/>
                                <input type="hidden"
                                       name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                                <button type="submit"
                                        onclick="return confirm('Вы уверены?');">
                                    Удалить
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>