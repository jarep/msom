<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<h2>All Processing Systems</h2>
<table class="table">
    <tr>
        <th>Name</th>
        <th>View / Edit</th>
        <th>Simulate</th>
    </tr>
    <tr>
        <c:forEach items="#{processingSystems}" var="processingSystem">
        <tr>
            <td>${processingSystem.name}</td>
            <td><a href="/msom/processingsystemmockup/view/${processingSystem.id}">View / Edit</a></td>
            <td><a href="/msom/processingsystemmockup/simulate/${processingSystem.id}">Simulate</a></td>
        </tr>
    </c:forEach>
</tr>

</table>

<div>
    ${message}
</div>