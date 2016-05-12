
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Controller Unit</h2>
    <sf:form method="post" modelAttribute="controller" action="update">
        <sf:hidden path="id" />
        <sf:hidden path="model.id" />
    <div class="form-group">
        <label for="name">Name</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
<div>
    <h4>Paths from this controller</h4>
    <table class="table">
        <tr>
            <th>Id</th>
            <th>From</th>
            <th>To</th>
            <th>Task Type</th>
        </tr>
        <c:forEach items="#{pathsFromControllerList}" var="path">
            <tr>
                <td>${path.id}</td>
                <td>${path.controllerUnit.name}</td>
                <td>${path.nextControllerUnit.name}</td>
                <td>${path.taskType.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    <h4>Paths to this controller</h4>
    <table class="table">
        <tr>
            <th>Id</th>
            <th>From</th>
            <th>To</th>
            <th>Task Type</th>
        </tr>
        <c:forEach items="#{pathsToControllerList}" var="path">
            <tr>
                <td>${path.id}</td>
                <td>${path.controllerUnit.name}</td>
                <td>${path.nextControllerUnit.name}</td>
                <td>${path.taskType.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
