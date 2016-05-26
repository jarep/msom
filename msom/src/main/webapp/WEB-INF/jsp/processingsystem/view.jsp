
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Processing System <strong>${processingSystem.name}</strong></h3>
<a href="/msom/processingsystem/reset/${processingSystem.id}">Reset</a> |
<a href="/msom/processingsystem/view/${processingSystem.id}">Next simulation step</a>

<div class="processing-container">
    <c:forEach items="${processingSystem.getTaskDispatchers()}" var="taskDispatcher">
        <div class="task-dispatcher">
            <h4>${taskDispatcher.name}</h4>
            <div class="processing-units">
                <h5>Processing units:</h5>
                <ul>
                    <c:forEach items="${taskDispatcher.getProcessingUnits()}" var="processingUnit">
                        <li>       
                            <p><strong>${processingUnit.name}</strong> [cores: ${processingUnit.cores}] [eff: ${processingUnit.efficiency}]  </p>
                            <p class="simulation-parameters">
                                Fake simulation:</br>
                                <span> Queue Length: <em>${processingUnit.queueLength}</em> </span> 
                                <span> Queue Value: <em>${processingUnit.queueValue}</em> </span> 
                            </p>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:forEach>
</div>