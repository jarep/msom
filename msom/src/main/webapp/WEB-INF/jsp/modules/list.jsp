<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- [opis] -->
</p>
<p class="lead">
    <a class="btn btn-default" href="modules/new">Add new module</a>
</p>

<h2>Available modules</h2>
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
            <c:forEach items="#{module.taskTypes}" var="tasktype">    
                ${tasktype.name} 
            </c:forEach>
            </td>
            <td>${module.controllerUnit.name}</td>
            <td><a href="modules/${module.id}">Edit</a></td>
            <td>
                <sf:form action="modules/remove/${module.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
