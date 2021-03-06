<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- [opis] -->
</p>
<p class="lead">
    <a class="btn btn-default" href="modules/new">Add new Module</a>
</p>

<h2>Available Modules</h2>
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
        <th>Cores</th>
        <th>Efficiency</th>
        <th>Task types</th>
        <th>Controller unit</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{moduleList}" var="module">
        <tr>
            <td>${module.id}</td>
            <td>${module.name}</td>
            <td>${module.cores}</td>
            <td>${module.efficiency}</td>
            <td>
                <c:forEach items="#{module.taskTypes}" var="tasktype" varStatus="loop">    
                    ${tasktype.name}<c:if test="${!loop.last}"><br/></c:if>
                </c:forEach>
            </td>
            <td>
                <a href="/msom/controllerunits/${module.controllerUnit.getId()}">${module.controllerUnit.name}</a>
            </td>
            <td><a href="modules/${module.id}">Edit</a></td>
            <td>
                <sf:form action="modules/remove/${module.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
