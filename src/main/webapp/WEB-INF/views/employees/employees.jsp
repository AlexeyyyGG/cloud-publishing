<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
    <a href="${pageContext.request.contextPath}/web/employees/new">
        Добавить сотрудника
    </a>
</c:if>

<table border="1">
    <tr>
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Middle name</th>
        <th>Email</th>
        <th>Gender</th>
        <th>Birth year</th>
        <th>Address</th>
        <th>Education</th>
        <th>Type</th>
        <th>Is chief editor</th>
        <c:if test="${pageContext.request.isUserInRole('CHIEF_EDITOR')}">
            <th>Actions</th>
        </c:if>
    </tr>

    <c:forEach var="emp" items="${employees}">
        <tr>
            <td>${emp.id}</td>
            <td>${emp.firstName}</td>
            <td>${emp.lastName}</td>
            <td>${emp.middleName}</td>
            <td>${emp.email}</td>
            <td>${emp.gender}</td>
            <td>${emp.birthYear}</td>
            <td>${emp.address}</td>
            <td>${emp.education}</td>
            <td>${emp.type}</td>
            <td>${emp.chiefEditor}</td>
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