<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<h2>All Processing Systems</h2>
<table class="table">
    <tr>
        <th>Name</th>
        <th>View</th>
        <th>Edit</th>
        <th>Simulate</th>
    </tr>
    <tr>
        <c:forEach items="#{processingSystems}" var="processingSystem">
        <tr>
            <td>${processingSystem.name}</td>
            <td><a href="/msom/processingsystem/view/${processingSystem.id}">View</a></td>
            <td><a href="#">Edit</a></td>
            <td><a href="#">Simulate</a></td>
        </tr>
    </c:forEach>
</tr>

</table>
