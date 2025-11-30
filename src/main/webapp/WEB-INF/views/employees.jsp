<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Employee" %>
<%
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
%>
<table border="1">
    <tr>
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Middle name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Gender</th>
        <th>Birth year</th>
        <th>Address</th>
        <th>Education</th>
        <th>Type</th>
        <th>Is chief editor</th>
    </tr>
    <% for(Employee emp : employees) { %>
    <tr>
        <td><%= emp.id() %></td>
        <td><%= emp.firstName() %></td>
        <td><%= emp.lastName() %></td>
        <td><%= emp.middleName() %></td>
        <td><%= emp.email() %></td>
        <td><%= emp.password() %></td>
        <td><%= emp.gender() %></td>
        <td><%= emp.birthYear() %></td>
        <td><%= emp.address() %></td>
        <td><%= emp.education() %></td>
        <td><%= emp.type() %></td>
        <td><%= emp.isChiefEditor() %></td>
    </tr>
    <% } %>
</table>