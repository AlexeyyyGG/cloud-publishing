<%@ page contentType="text/html; charset=UTF-8" %>
<meta charset="UTF-8"/>
<h2>Обновить сотрудника</h2>
<form action="${pageContext.request.contextPath}/web/employees/${employee.id}" method="post">
    <input type="text" name="firstName" placeholder="Имя" required value="${employee.firstName}"/>
    <input type="text" name="lastName" placeholder="Фамилия" required value="${employee.lastName}"/>
    <input type="text" name="middleName" placeholder="Отчество" value="${employee.middleName}"/>
    <input type="email" name="email" placeholder="Email" required value="${employee.email}"/>
    <input type="password" name="password" placeholder="Новый пароль"/>
    <input type="password" name="passwordConfirm" placeholder="Подтверждение пароля"/>
    <select name="gender">
        <option value="MALE" ${employee.gender == 'MALE' ? 'selected' : ''}>Мужской</option>
        <option value="FEMALE" ${employee.gender == 'FEMALE' ? 'selected' : ''}>Женский</option>
    </select>
    <input type="number" name="birthYear" placeholder="Год рождения" value="${employee.birthYear}"/>
    <input type="text" name="address" placeholder="Адрес" value="${employee.address}"/>
    <input type="text" name="education" placeholder="Образование" value="${employee.education}"/>
    <input type="text" name="type" placeholder="Тип" value="${employee.type}"/>
    <label>
        <input type="checkbox" name="isChiefEditor"
               value="true" ${employee.isChiefEditor ? 'checked' : ''}/> Главный редактор
    </label>
    <button type="submit">Обновить</button>
</form>