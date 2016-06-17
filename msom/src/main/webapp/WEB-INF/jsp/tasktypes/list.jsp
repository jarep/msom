<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">Type of the task is an assessment of its complexity.
    Each task needs to have a type assigned. 
    Specific processing modules can handle a specific type of task.
</p>

<p class="lead">
    <a class="btn btn-default" href="tasktypes/new">Add new Task Type</a>
</p>
<h2>Available Task Types</h2>
<c:choose>
<c:when test="${empty msg}">
</c:when>
<c:when test="${fn:startsWith(msg, 'Error')}">
<div class="alert alert-danger" role="alert">
      <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
      <span>${msg}</span>
</div>
</c:when>
<c:otherwise>
<div class="alert alert-success" role="alert">
      <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
      <span>${msg}</span>
</div>
</c:otherwise>
</c:choose>
<table class="table">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Difficulty</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{taskTypesList}" var="type">
        <tr>
            <td>${type.id}</td>
            <td>${type.name}</td>
            <td>${type.difficulty}</td>
            <td><a href="tasktypes/${type.id}">Edit</a></td>
            <td>
                <sf:form action="tasktypes/remove/${type.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="alert alert-warning" role="alert">
    <em>Warning - you are allowed to delete only types which are not linked to any tasks (for seed data it would be "Type Y")</em>
</div>

