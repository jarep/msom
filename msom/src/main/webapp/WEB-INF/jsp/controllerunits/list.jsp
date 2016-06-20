<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<p class="lead">
    <a class="btn btn-default" href="controllerunits/new">Add new Controller Unit</a>
</p>
<h2>Available Controller Units</h2>
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
        <th>Model</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{controllersList}" var="controller">
        <tr>
            <td>${controller.id}</td>
            <td><a href="controllerunits/${controller.id}">${controller.name}</a></td>
            <td><a href="/msom/models/${controller.model.id}">${controller.model.name}</a></td>
            <td><a href="controllerunits/${controller.id}">Edit</a></td>
            <td>
                <sf:form action="controllerunits/remove/${controller.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="alert alert-warning" role="alert">
    <em>You can manage processing paths on editing site for particular controller.</em>
</div>
