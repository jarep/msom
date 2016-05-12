<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">

</p>
<h3>Add new Controller Unit</h2>
    <sf:form method="post" action="new" modelAttribute="controller">
    <div class="form-group">
        <label for="name">Name of Controller Unit</label>
        <sf:input path="name" class="form-control" />
    </div>

    <sf:select path="model.id">
        <sf:options items="${modelsList}" itemLabel="name" itemValue="id" />
    </sf:select>    
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

