<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<div>
    <a href="${pageContext.request.contextPath}/web/employees">
        Сотрудники
    </a>
    <c:if test="${pageContext.request.isUserInRole('ROLE_CHIEF_EDITOR')}">
        |
        <a href="${pageContext.request.contextPath}/web/publications">
            Журналы / Газеты
        </a>
    </c:if>
    |
    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <button type="submit">Выйти</button>
    </form>
</div>
<hr/>