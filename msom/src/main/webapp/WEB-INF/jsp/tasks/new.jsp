<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    Zadanie aktualnie posiada tylko nazwę i typ zadania.
</p>
<h3>Dodawanie nowego zadania</h2>
    <sf:form method="post" action="new" modelAttribute="task">
    <div class="form-group">
        <label for="name">Name of task</label>
        <sf:input path="name" class="form-control" />
    </div>

    <sf:select path="taskType.id">
        <sf:options items="${taskTypesList}" itemLabel="name" itemValue="id" />
    </sf:select>    
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

