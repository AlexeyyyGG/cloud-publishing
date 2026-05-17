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
<%@ include file="/WEB-INF/views/layout/navigation.jsp" %>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<%--@elvariable id="publications" type="java.util.List"--%>
<main>
    <div style="display: inline-block;">
        <div style="text-align: right; margin-bottom: 10px;">
            <a href="${pageContext.request.contextPath}/web/publications/new">
                Добавить
            </a>
        </div>
        <c:choose>
            <c:when test="${empty publications}">
                <p>Журналов и газет пока нет.</p>
            </c:when>
            <c:otherwise>
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
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>