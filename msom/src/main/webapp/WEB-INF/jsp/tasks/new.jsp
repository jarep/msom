<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    Each task has a name and a type.
</p>
<h3>Add new task</h2>
    <sf:form id="taskForm" method="post" action="new" modelAttribute="task">
    <div class="form-group">
        <label for="name">Task name</label>
        <sf:input path="name" class="form-control" />
    </div>
    <div class="form-group">
        <label for="tasktype">Task Type</label>
        <sf:select path="taskType.id" class="form-control">
            <sf:options items="${taskTypesList}" itemLabel="name" itemValue="id" />
        </sf:select>    
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>