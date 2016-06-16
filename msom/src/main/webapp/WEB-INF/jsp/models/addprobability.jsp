<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">

</p>
<h3>Add new Task Probability</h3>
<div>        
    <sf:form method="post" modelAttribute="taskProbability" id="processingPathForm">

        <table class="table">
            <tr>
                <th>Model</th>
                <th>Task</th>
                <th>Distribution</th>
            </tr>
            <tr>
                <td>
                    <sf:hidden path="model.id" />
                    ${model.name}
                </td>
                <td>
                    <sf:select path="task.id" cssClass="form-control">
                        <sf:options items="${tasks}" itemLabel="name" itemValue="id" />
                    </sf:select> 
                </td>
                <td>
                    <sf:select path="distribution.id" cssClass="form-control">
                        <sf:options items="${distributions}" itemValue="id" />
                    </sf:select> 
                </td>
            </tr>
        </table>
        <div>
            <a class="btn btn-danger" href="/msom/models/${taskProbability.model.id}">Return to " <strong>${taskProbability.model.name} </strong>"</a>
            <button type="submit" class="btn btn-success">Submit</button>
        </div>
    </sf:form>
</div>