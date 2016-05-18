<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<p class="lead">
    <a class="btn btn-default" href="controllerunits/new">Add new Controller Unit</a>
</p>
<h2>All Controller Units</h2>
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
            <td>${controller.name}</td>
            <td>${controller.model.name}</td>
            <td><a href="controllerunits/${controller.id}">Edit</a></td>
            <td>
                <sf:form action="controllerunits/remove/${controller.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>