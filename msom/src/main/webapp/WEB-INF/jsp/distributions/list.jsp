<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- opis -->
</p>
<p class="lead">
    <a class="btn btn-default" href="distributions/new">Add new distribution</a>
</p>

<h2>Available distributions</h2>
<table class="table">
    <tr>
        <th>Id</th>
        <th>Type</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{distributionList}" var="distribution">
        <tr>
            <td>${distribution.id}</td>
            <td>${distribution.type}</td>
            <td><a href="distributions/${distribution.id}">Edit</a></td>
            <td>
        <sf:form action="distributions/remove/${distribution.id}" method="post">
            <input type="submit" class="btn-sm btn-danger" value="Delete" />
        </sf:form>
    </td>
</tr>
</c:forEach>
</table>
