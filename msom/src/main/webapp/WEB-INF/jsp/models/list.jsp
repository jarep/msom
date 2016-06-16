<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<p class="lead">
    <a class="btn btn-default" href="models/new">Add new Model</a>
</p>
<h2>Available Models</h2>
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
        <th>First Controller Unit</th>
        <th>Controller Units</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{modelsList}" var="m">
        <tr>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td>${m.firstControllerUnit.name} 
                <c:if test="${m.firstControllerUnit.id>0}">
                    <sf:form action="models/remove-first-task-dispatcher/${m.id}" method="post" cssStyle="display: inline;">
                        <input type="submit" class="btn-sm btn-danger" value="x" id="remove-btn" />
                    </sf:form>
                </c:if>
            <td>
            <c:forEach items="#{m.controllerUnits}" var="controllerUnit" varStatus="loop">    
                ${controllerUnit.name}<c:if test="${!loop.last}"><br/></c:if>
            </c:forEach>
            </td>
            <td><a href="models/${m.id}">Edit</a></td>
            <td>
                <sf:form action="models/remove/${m.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="alert alert-warning" role="alert">
    <em>You can manage distribution for particular tasks on on model editing site.</em>
</div>
