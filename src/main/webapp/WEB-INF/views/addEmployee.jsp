<%@ page contentType="text/html; charset=UTF-8" %>
<meta charset="UTF-8"/>
<h2>Добавить сотрудника</h2>
<form action="${pageContext.request.contextPath}/web/employees/add" method="post">
    <input type="hidden" name="isChiefEditor" value="false"/>
    <input type="text" name="firstName" placeholder="Имя" required/>
    <input type="text" name="lastName" placeholder="Фамилия" required/>
    <input type="text" name="middleName" placeholder="Отчество"/>
    <input type="email" name="email" placeholder="Email" required/>
    <input type="password" name="password" placeholder="Пароль" required/>
    <input type="text" name="gender" placeholder="Пол"/>
    <input type="number" name="birthYear" placeholder="Год рождения"/>
    <input type="text" name="address" placeholder="Адрес"/>
    <input type="text" name="education" placeholder="Образование"/>
    <input type="text" name="type" placeholder="Тип"/>
    <label>
        <input type="checkbox" name="isChiefEditor" value="true"/> Главный редактор
    </label>
    <button type="submit">Добавить</button>
</form>