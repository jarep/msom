<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">Typ zadania pozwala określić złożoność zadania. 
    Każde zadanie ma przypisany typ. Poszczególne moduły przetwarzające potrafią 
    obsługiwać zadania konkretnego typu.
</p>
<h3>Edycja typu zadań</h2>
    <sf:form id="taskForm" method="post" modelAttribute="task" action="update">
        <sf:hidden path="id" />
    <div class="form-group">
        <label for="name">Nazwa zadania</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <div class="form-group">
        <sf:select path="taskType.id" >
            <sf:options items="${taskTypesList}" itemLabel="name" itemValue="id" />
        </sf:select>
    </div>

    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
