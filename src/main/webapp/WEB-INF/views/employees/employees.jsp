<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
    <a href="${pageContext.request.contextPath}/web/employees/new">
        Добавить сотрудника
    </a>
</c:if>

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
            <td>${emp.gender.label}</td>
            <td>${emp.birthYear}</td>
            <td>${emp.type.label}</td>
            <td>
                <c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
                    <a href="${pageContext.request.contextPath}/web/employees/${emp.id}/edit">
                        Редактировать
                    </a>

                    <form action="${pageContext.request.contextPath}/web/employees/${emp.id}" method="post" style="display:inline;">
                        <input type="hidden" name="_method" value="delete" />
                        <button type="submit"
                                onclick="return confirm('Вы уверены?');">
                            Удалить
                        </button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>