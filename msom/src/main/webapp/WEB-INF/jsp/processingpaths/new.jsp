<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">

</p>
<h3>Add new Processing Path</h3>
<div>        
    <sf:form method="post" modelAttribute="path">

        <table class="table">
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Task Type</th>
                <th>Processing</th>
            </tr>
            <tr>
                <td>
                    <sf:hidden path="controllerUnit.id" />
                    ${path.controllerUnit.name}
                </td>
                <td>
                    <sf:select name="nextControllerUnit" path="nextControllerUnit.id" cssClass="form-control">
                        <sf:options items="${controllers}" itemLabel="name" itemValue="id" />
                    </sf:select> 
                </td>
                <td>
                    <sf:select name="taskType" path="taskType.id" cssClass="form-control">
                        <sf:options items="${unspecifiedTypes}" itemLabel="name" itemValue="id" />
                    </sf:select> 
                </td>
                <td>
                    <sf:checkbox path="processing" />
                </td>
            </tr>
        </table>
        <div>
            <a class="btn btn-danger" href="/msom/controllerunits/${path.controllerUnit.id}">Return to " <strong>${path.controllerUnit.name} </strong>"</a>
            <button type="submit" class="btn btn-success">Submit</button>
        </div>
    </sf:form>
</div>


