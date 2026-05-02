<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
<%--@elvariable id="publicationRequest" type="dto"--%>
<%--@elvariable id="publicationType" type="java.util.List"--%>
<%--@elvariable id="categories" type="java.util.List"--%>
<%--@elvariable id="journalists" type="java.util.List"--%>
<%--@elvariable id="editors" type="java.util.List"--%>
<%--@elvariable id="publicationId" type="java.lang.Integer"--%>
<form:form method="post"
           action="${pageContext.request.contextPath}/web/publications/${publicationId}"
           novalidate="true"
           modelAttribute="publicationRequest">
    <div>
        <label for="name">Название</label>
        <form:input path="name" id="name"/>
        <form:errors path="name" cssStyle="color:red"/>
    </div>
    <div>
        <label>Тип</label>
        <form:radiobuttons
                path="publicationType"
                items="${publicationType}"
                itemValue="name"
                itemLabel="label"/>
        <form:errors path="publicationType" cssStyle="color:red"/>
    </div>
    <div>
        <label for="theme">Тематика</label>
        <form:input path="theme" id="theme"/>
        <form:errors path="theme" cssStyle="color:red"/>
    </div>
    <div>
        <label>Рубрики</label>
        <form:select path="categories" multiple="true">
            <form:options items="${categories}" itemValue="id" itemLabel="name"/>
        </form:select>
        <form:errors path="categories" cssStyle="color:red"/>
    </div>
    <div>
        <label>Журналисты</label>
        <form:select path="journalists" multiple="true">
            <form:options items="${journalists}" itemValue="id" itemLabel="shortName"/>
        </form:select>
        <form:errors path="journalists" cssStyle="color:red"/>
    </div>
    <div>
        <label>Редакторы</label>
        <form:select path="editors" multiple="true">
            <form:options items="${editors}" itemValue="id" itemLabel="shortName"/>
        </form:select>
        <form:errors path="editors" cssStyle="color:red"/>
    </div>
    <div>
        <button type="button"
                onclick="location.href='${pageContext.request.contextPath}/web/publications'">
            Отменить
        </button>
        <button type="submit">Сохранить</button>
    </div>
</form:form>
</body>
</html>