
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
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
