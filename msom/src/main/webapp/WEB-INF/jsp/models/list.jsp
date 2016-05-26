<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<p class="lead">
    <a class="btn btn-default" href="models/new">Add new Model</a>
</p>
<h2>All Models</h2>
<table class="table">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{modelsList}" var="m">
        <tr>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td><a href="models/${m.id}">Edit</a></td>
            <td>
                <sf:form action="models/remove/${m.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
