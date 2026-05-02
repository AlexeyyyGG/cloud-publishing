<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<%--@elvariable id="publications" type="java.util.List"--%>
<div>
    <c:if test="${pageContext.request.userPrincipal != null}">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}" />
            <button type="submit">Выйти</button>
        </form>
    </c:if>
</div>
<div style="display: inline-block;">
    <div style="text-align: right; margin-bottom: 10px;">
        <a href="${pageContext.request.contextPath}/web/publications/new">
            Добавить
        </a>
    </div>
    <table border="1">
        <tr>
            <th>Название</th>
            <th>Тип</th>
            <th>Тематика</th>
            <th>Рубрики</th>
            <th></th>
        </tr>
        <c:forEach var="pub" items="${publications}">
            <tr>
                <td>${pub.name}</td>
                <td>${fn:toLowerCase(pub.publicationType.label)}</td>
                <td>${pub.theme}</td>
                <td>
                    <c:forEach var="cat" items="${pub.categories}" varStatus="status">
                        ${cat.name}<c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/web/publications/${pub.id}/edit">
                        Редактировать
                    </a>
                    <form action="${pageContext.request.contextPath}/web/publications/${pub.id}"
                          method="post"
                          style="display:inline;">
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
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>