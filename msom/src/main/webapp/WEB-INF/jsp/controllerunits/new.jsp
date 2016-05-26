<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">

</p>
<h3>Add new Controller Unit</h3>
<sf:form method="post" action="new" modelAttribute="controller">
    <div class="form-group">
        <label for="name">Name of Controller Unit</label>
        <sf:input path="name" class="form-control" />
    </div>
    <div class="form-group">
        <label for="model">Model</label>
        <sf:select name="model" path="model.id" cssClass="form-control">
            <sf:options items="${modelsList}" itemLabel="name" itemValue="id" />
        </sf:select> 
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

