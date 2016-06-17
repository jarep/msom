<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>

<h2>Available Processing Systems</h2>
<table class="table">
    <tr>
        <th>Name</th>
        <th>Simulate</th>
    </tr>
    <tr>
        <c:forEach items="#{processingSystems}" var="processingSystem">
        <tr>
            <td>${processingSystem.name}</td>
            <td><a href="/msom/processingsystem/simulate/${processingSystem.id}">Simulate</a></td>
        </tr>
    </c:forEach>
</tr>

</table>
