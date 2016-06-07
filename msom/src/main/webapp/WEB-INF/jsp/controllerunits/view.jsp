
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Controller Unit</h3>
<sf:form method="post" id="controllerUnitForm" modelAttribute="controller" action="update">
    <sf:hidden path="id" />
    <div class="form-group">
        <label for="model">Model</label>
        <sf:select name="model" path="model.id" cssClass="form-control">
            <sf:options items="${modelsList}" itemLabel="name" itemValue="id" />
        </sf:select> 
    </div>
    <div class="form-group">
        <label for="name">Name</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <button type="submit" class="btn btn-default">Update</button>
</sf:form>
<h3>Processing Paths</h3>
<a class="btn btn-default" href="/msom/processingpaths/new/${controller.id}">Create new path from " <strong>${controller.name} </strong>"</a>
<div>
    <h4>Paths from this controller</h4>
    <table class="table">
        <tr>
            <th>Id</th>
            <th>From</th>
            <th>To</th>
            <th>Task Type</th>
            <th>Processing</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="#{pathsFromControllerList}" var="path">
            <tr>
                <td>${path.id}</td>
                <td><a href="${path.controllerUnit.id}"> ${path.controllerUnit.name} </a></td>
                <td>
                    <c:choose>
                        <c:when test="${path.controllerUnit.id == path.nextControllerUnit.id}">
                            <em>-- finish task --</em>
                        </c:when>    
                        <c:otherwise>
                            <a href="${path.nextControllerUnit.id}"> ${path.nextControllerUnit.name} </a></td>
                        </c:otherwise>
                    </c:choose>
                <td>${path.taskType.name}</td>
                <td>${path.processing}</td>
                <td>
                    <sf:form action="/msom/processingpaths/remove/${controller.id}/${path.id}" method="post">
                        <input type="submit" class="btn-sm btn-danger" value="Delete" />
                    </sf:form>
                </td>
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
            <th>Processing</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="#{pathsToControllerList}" var="path">
            <tr>
                <td>${path.id}</td>
                <td><a href="${path.controllerUnit.id}"> ${path.controllerUnit.name} </a></td>
                <td>
                    <c:choose>
                        <c:when test="${path.controllerUnit.id == path.nextControllerUnit.id}">
                            <em>-- finish task --</em>
                        </c:when>    
                        <c:otherwise>
                            <a href="${path.nextControllerUnit.id}"> ${path.nextControllerUnit.name} </a></td>
                        </c:otherwise>
                    </c:choose>
            <td>${path.taskType.name}</td>
            <td>${path.processing}</td>
            <td>
                <sf:form action="/msom/processingpaths/remove/${controller.id}/${path.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
            </tr>
        </c:forEach>
    </table>
</div>
