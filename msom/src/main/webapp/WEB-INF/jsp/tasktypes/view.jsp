<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">Typ zadania pozwala określić złożoność zadania. 
    Każde zadanie ma przypisany typ. Poszczególne moduły przetwarzające potrafią 
    obsługiwać zadania konkretnego typu.
</p>
<h3>Edycja typu zadań</h2>
    <sf:form method="post" modelAttribute="taskType" id="taskTypeForm" action="update">
        <sf:hidden path="id" />
    <div class="form-group">
        <label for="name">Name of type</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <div class="form-group">
        <label for="difficulty">Difficulty (Integer)</label>
        <sf:input path="difficulty" class="form-control" />
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>