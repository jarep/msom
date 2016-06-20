
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Model</h3>
<sf:form method="post" modelAttribute="model" id="modelForm" action="update">
    <sf:hidden path="id" />
    <div class="form-group">
        <label for="name">Name</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <div class="form-group" >
        <label for="firstControllerUnit">First Controller Unit</label>
        <sf:select path="firstControllerUnit.id" class="form-control">
            <sf:options items="${model.controllerUnits}" itemLabel="name" itemValue="id" />
        </sf:select>
    </div>
    <button type="submit" class="btn btn-default">Update</button>
</sf:form>

<h3>Task Probabilities for model</h3>
<a class="btn btn-default" href="/msom/models/addprobability/${model.id}">Create new probability</a><br/><br/>
<div>
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
            <th>Model</th>
            <th>Task</th>
            <th>Distribution Type</th>
            <th>Parameters</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="#{taskProbabilitiesList}" var="p">
            <tr>
                <td>${p.id}</td>
                <td>${p.model.name}</td>
                <td><a href="/msom/tasks/${p.task.id}">${p.task.name}</a></td>
                <td><a href="/msom/distributions/${p.distribution.id}">${p.distribution.type}</a></td>
                <td><a href="/msom/distributions/${p.distribution.id}">${p.distribution.parameterA}, ${p.distribution.parameterB}</a></td>
                <td><a href="/msom/models/editprobability/${p.id}">Edit</a></td>
                <td>
                    <sf:form action="/msom/models/removeprobability/${p.model.id}/${p.id}" method="post">
                        <input type="submit" class="btn-sm btn-danger" value="Delete" />
                    </sf:form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
