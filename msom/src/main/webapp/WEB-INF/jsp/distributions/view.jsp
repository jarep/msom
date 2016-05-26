<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
    <!-- opis -->
</p>
<h3>Edit distribution</h2>
<sf:form method="post" modelAttribute="distribution" action="update">
    <sf:hidden path="id" />
    <div class="form-group">
        <label for="type">Type of distribution</label>
        <sf:select path="type">
            <sf:options items="${distributionTypes}" />
        </sf:select>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</sf:form>
