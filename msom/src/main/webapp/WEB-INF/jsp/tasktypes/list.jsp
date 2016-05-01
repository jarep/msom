<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">Typ zadania pozwala określić złożoność zadania. 
    Każde zadanie ma przypisany typ. Poszczególne moduły przetwarzające potrafią 
    obsługiwać zadania konkretnego typu.
</p>

<p class="lead">
    <a class="btn btn-default" href="tasktypes/new">Dodaj nowy typ</a>
</p>
<h2>Dostępne typy</h2>
<table class="table">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Difficulty</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="#{taskTypesList}" var="type">
        <tr>
            <td>${type.id}</td>
            <td>${type.name}</td>
            <td>${type.difficulty}</td>
            <td><a href="tasktypes/${type.id}">Edit</a></td>
            <td>
                <sf:form action="tasktypes/remove/${type.id}" method="post">
                    <input type="submit" class="btn-sm btn-danger" value="Delete" />
                </sf:form>
            </td>
        </tr>
    </c:forEach>
</table>
<p>
    <em>Uwaga - można usunąc tylko te typy, które nie mają 
        powiązanych zadań (dla przykładowych danych "Type D")</em>
</p>
