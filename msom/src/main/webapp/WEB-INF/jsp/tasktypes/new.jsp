<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<p class="lead">Task types allows to assess its complexity.
    Each task has a type assigned.     
    Specific processing modules can handle a specific type of task.
</p>
<h3>Add new task type</h2>
    <sf:form method="post" modelAttribute="taskType" id="taskTypeForm" action="new">
    <div class="form-group">
        <label for="name">Name of type</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <div class="form-group">
        <label for="difficulty">Difficulty (Number)</label>
        <sf:input path="difficulty" class="form-control" type="number"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>