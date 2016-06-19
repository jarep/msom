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
        <sf:input path="parameterA" class="form-control" type="number" step="0.01"/>
    </div>
    <div class="form-group">
        <label for="parameterB">Parameter B (Double)</label>
        <sf:input path="parameterB" class="form-control" type="number" step="0.01"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
</div>
<br/>
<div class="alert alert-warning" role="alert">
    <em>Parameter A - is the average number of events per interval.</em>
    <em>Parameter B - number of occurrences.</em>
</div>
<div class="alert alert-info" role="alert">
    <em>Only integer parameters greater than "1" allowed for distributions! Else task will be not created</em>
</div>
<div class="alert alert-danger" role="alert">
    <em>Now only POISSON distribution is supported.</em>
</div>
