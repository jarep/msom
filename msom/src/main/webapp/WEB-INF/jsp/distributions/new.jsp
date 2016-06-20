<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- opis -->
</p>
<div>
<h3>Add new distribution</h2>
    <sf:form method="post" action="new" modelAttribute="distribution" id="distributionForm">
    <div class="form-group">
        <label for="type">Type of distribution</label>
        <sf:select path="type" class="form-control" >
            <sf:options items="${distributionTypes}" />
        </sf:select>
    </div>
    <div class="form-group">
        <label for="parameterA">Parameter A (Double)</label>
        <sf:input path="parameterA" class="form-control" type="number"/>
    </div>
    <div class="form-group">
        <label for="parameterB">Parameter B (Double)</label>
        <sf:input path="parameterB" class="form-control" type="number"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
</div>
<br/>
<div class="alert alert-danger" role="alert">
    <em>Only POISSON distribution is supported.</em>
</div>