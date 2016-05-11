<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- opis -->
</p>
<h3>Add new distribution</h2>
    <sf:form method="post" action="new" modelAttribute="distribution">
    <div class="form-group">
        <label for="type">Type of distribution</label>
        <sf:select path="type">
            <sf:options items="${distributionTypes}" />
        </sf:select>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>

