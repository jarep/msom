<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Processing System <strong>${processingSystem.name}</strong></h3>

<p>Is locked? ...</p>
<a class="btn-success btn-sm" href="#">Save changes</a>
<a class="btn-danger btn-sm" href="#">Cancel</a>

<div class="processing-container">
    <c:forEach items="${processingSystem.getTaskDispatchers()}" var="taskDispatcher">
        <div class="task-dispatcher">
            <h4>${taskDispatcher.name}</h4>
            <div class="processing-units">
                <h5>Processing units:</h5>
                <a class="btn-block btn-success btn-sm" href="#">Add unit</a>
                <c:forEach items="${taskDispatcher.getProcessingUnits()}" var="processingUnit">
                    <div class="single-unit">
                        <h6><strong>${processingUnit.name}</strong> [cores: ${processingUnit.cores}] [eff: ${processingUnit.efficiency}]</h6> 
                        <a class=" btn-danger btn-sm" href="#">Remove unit</a>
                        <a class=" btn-primary btn-sm" href="#">Edit unit</a>
                        <h6>Available types</h6>
                        <ul>
                            <li>Type A < <a href="#">Remove</a> > </li>
                            <li>Type B < <a href="#">Remove</a> > </li>
                            <li> Add new {select} ... </li>
                        </ul>
                    </div>
                </c:forEach>
            </div>
            <div class="processing-units">
                <h5>Processing paths:</h5>
                ...
            </div>
        </div>
    </c:forEach>
</div>