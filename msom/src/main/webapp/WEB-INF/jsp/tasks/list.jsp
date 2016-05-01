<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    Zadanie aktualnie posiada tylko nazwę i typ zadania.
</p>
<p class="lead">
    <a class="btn btn-default" href="tasks/new">Dodaj nowe zadanie</a>
</p>

<h2>Zadania w systemie</h2>
<table class="table">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Type</th>
        <th>Type - difficulty</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{tasksList}" var="task">
        <tr>
            <td>${task.id}</td>
            <td>${task.name}</td>
            <td>${task.taskType.name}</td>
            <td>${task.taskType.difficulty}</td>
            <td><a href="tasks/${task.id}">Edit</a></td>
            <td>
        <sf:form action="tasks/remove/${task.id}" method="post">
            <input type="submit" class="btn-sm btn-danger" value="Delete" />
        </sf:form>
    </td>
</tr>
</c:forEach>
</table>
