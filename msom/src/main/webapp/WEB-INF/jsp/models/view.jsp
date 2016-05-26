
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Model</h3>
<sf:form method="post" modelAttribute="model" action="update">
    <sf:hidden path="id" />
    <div class="form-group">
        <label for="name">Name</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <button type="submit" class="btn btn-default">Update</button>
</sf:form>
