<%@ page contentType="text/html; charset=UTF-8" %>
<meta charset="UTF-8"/>
<h2>Обновить сотрудника</h2>
<form action="${pageContext.request.contextPath}/web/employees/${employee.id}" method="post">
    <input type="hidden" name="_method" value="put" />
    <div>
        <input type="text" name="firstName" value="${employee.firstName}" placeholder="Имя" required />
    </div>
    <div>
        <input type="text" name="lastName" value="${employee.lastName}" placeholder="Фамилия" required />
    </div>
    <div>
        <input type="text" name="middleName" value="${employee.middleName}" placeholder="Отчество" />
    </div>
    <div>
        <input type="email" name="email" value="${employee.email}" placeholder="Email" required />
    </div>
    <div>
        <input type="password" name="password" placeholder="Пароль (оставьте пустым, чтобы не менять)" />
    </div>
    <div>
        <input type="password" name="passwordConfirm" placeholder="Подтверждение пароля" />
    </div>
    <div>
        <input type="text" name="gender" value="${employee.gender}" placeholder="Пол" />
    </div>
    <div>
        <input type="number" name="birthYear" value="${employee.birthYear}" placeholder="Год рождения" />
    </div>
    <div>
        <input type="text" name="address" value="${employee.address}" placeholder="Адрес" />
    </div>
    <div>
        <input type="text" name="education" value="${employee.education}" placeholder="Образование" />
    </div>
    <div>
        <input type="text" name="type" value="${employee.type}" placeholder="Тип" />
    </div>
    <div>
        <input type="hidden" name="isChiefEditor" value="false" />
        <label>
            <input type="checkbox" name="isChiefEditor" value="true" ${employee.isChiefEditor ? 'checked' : ''} />
            Главный редактор
        </label>
    </div>
    <div>
        <button type="submit">Обновить</button>
    </div>
</form>