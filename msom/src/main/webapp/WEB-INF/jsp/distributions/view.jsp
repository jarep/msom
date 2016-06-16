<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- opis -->
</p>
<h3>Edit distribution</h2>
    <sf:form method="post" modelAttribute="distribution" action="update">
        <sf:hidden path="id" />
    <div class="form-group">
        <label for="type">Type of distribution</label>
        <sf:select path="type" class="form-control" >
            <sf:options items="${distributionTypes}" />
        </sf:select>
    </div>
    <div class="form-group">
        <label for="parameterA">Parameter A (Double)</label>
        <sf:input path="parameterA" class="form-control" type="number" step="0.01"/>
    </div>
    <div class="form-group">
        <label for="parameterB">Parameter B (Double)</label>
        <sf:input path="parameterB" class="form-control" type="number" step="0.01"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

<br/>
<div class="alert alert-danger" role="alert">
    <em>Only POISSON distribution is supported.</em>
</div>