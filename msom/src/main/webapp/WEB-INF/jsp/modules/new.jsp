<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    [opis]
</p>
<h3>Dodawanie modułu</h2>
    <sf:form method="post" action="new" modelAttribute="module">
    <div class="form-group">
        <label for="name">Name of module</label>
        <sf:input path="name" class="form-control"/>
    </div>
    <div class="form-group">
        <label for="cores">Cores (Integer)</label>
        <sf:input path="cores" class="form-control" type="number" min="1"/>
    </div>
    <div class="form-group">
        <label for="efficiency">Efficiency (Integer)</label>
        <sf:input path="efficiency" class="form-control" type="number" min="1"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

