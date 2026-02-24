<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title>Добавить сотрудника</title>
</head>
<body>
<h2>Добавить сотрудника</h2>
<form action="${pageContext.request.contextPath}/web/employees/add" method="post">
    <div>
        <input type="text" name="firstName" placeholder="Имя" required/>
    </div>
    <div>
        <input type="text" name="lastName" placeholder="Фамилия" required/>
    </div>
    <div>
        <input type="text" name="middleName" placeholder="Отчество"/>
    </div>
    <div>
        <input type="email" name="email" placeholder="Email" required/>
    </div>
    <div>
        <input type="password" name="password" placeholder="Пароль" required/>
    </div>
    <div>
        <input type="text" name="gender" placeholder="Пол"/>
    </div>
    <div>
        <input type="number" name="birthYear" placeholder="Год рождения"/>
    </div>
    <div>
        <input type="text" name="address" placeholder="Адрес"/>
    </div>
    <div>
        <input type="text" name="education" placeholder="Образование"/>
    </div>
    <div>
        <input type="text" name="type" placeholder="Тип"/>
    </div>
    <div>
        <input type="hidden" name="isChiefEditor" value="false"/>
        <label>
            <input type="checkbox" name="isChiefEditor" value="true"/> Главный редактор
        </label>
    </div>
    <div>
        <button type="submit">Добавить</button>
    </div>
</form>
</body>
</html>