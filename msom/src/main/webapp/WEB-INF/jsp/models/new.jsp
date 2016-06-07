<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">

</p>
<h3>Add new Model</h3>
<sf:form method="post" action="new" id="modelForm" modelAttribute="model">
    <div class="form-group">
        <label for="name">Name of Model</label>
        <sf:input path="name" class="form-control" />
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

