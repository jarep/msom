<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- [opis] -->
</p>
<h3>Add new module</h2>
    <sf:form method="post" action="new" id="moduleForm" modelAttribute="module">
    <div class="form-group">
        <label for="name">Name of module</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <div class="form-group">
        <label for="cores">Cores (Number)</label>
        <sf:input path="cores" class="form-control" type="number"/>
    </div>
    <div class="form-group">
        <label for="efficiency">Efficiency (Number)</label>
        <sf:input path="efficiency" class="form-control" type="number"/>
    </div>
    <div class="form-group">
        <label for="controllerUnit">Controller Unit</label>
        <sf:select path="controllerUnit.id" class="form-control">
            <sf:options items="${controllerUnitsList}" itemLabel="name" itemValue="id" />
        </sf:select>  
    </div>
    <div class="form-group">
        <label for="taskTypes">Task types</label>
        <sf:checkboxes items="${taskTypesList}" path="taskTypes"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
    
