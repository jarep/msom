<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- [opis] -->
</p>
<h3>Add new module</h2>
    <sf:form method="post" action="new" id="moduleForm" modelAttribute="module">
    <div class="form-group">
        <label for="name">Name of module</label>
        <sf:input name="name" path="name" class="form-control"/>
    </div>
    <div class="form-group">
        <label for="cores">Cores</label>
        <sf:input name="cores" path="cores" class="form-control" type="number"/>
    </div>
    <div class="form-group">
        <label for="efficiency">Efficiency</label>
        <sf:input name="efficiency"  path="efficiency" class="form-control" type="number"/>
    </div>
    <div class="form-group">
        
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
    
