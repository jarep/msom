<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div style="margin: 10px 0; padding: 25px;" class="bg-info">
    <samp>${message}</samp>
</div>
<div id="area">
    <div id="jsPlumbContainer" class="processing-container">
        <c:forEach items="${processingSystem.getTaskDispatchers()}" var="taskDispatcher">
            <div id="TD-${taskDispatcher.id}" class="task-dispatcher">
                <h4>${taskDispatcher.name} <c:if test="${taskDispatcher.first}"><em>- first controller -</em></c:if></h4>
                    <div class="processing-units">
                        <h5>Processing units:</h5>
                    <c:forEach items="${taskDispatcher.getProcessingUnits()}" var="processingUnit">
                        <div class="single-unit">
                            <h6><strong>${processingUnit.name}</strong> [cores: ${processingUnit.numberOfCores}] [eff: ${processingUnit.efficiency}]</h6> 
                            <p class="unit-details">
                                Available types: 
                                <c:forEach items="${processingUnit.getAvailableTypes()}" var="availableType">
                                    <span>${availableType.name}, </span>
                                </c:forEach>
                            </p>
                            <div class="tasks-box">
                                <p class="simulation-parameters">
                                    <span> Tasks in progress <em>${processingUnit.numberOfProcessingTasks}</em> </span> <br/>
                                </p>
                                <p  style="cursor:pointer;" class="details-toogle" onclick="toggleDetails(this)"> - hide details - </p>
                                <ul class="tasks-list percentages ${taskDispatcher.name} ${processingUnit.name}"> 
                                    <c:forEach items="${processingUnit.getProcessingTasks()}" var="task">
                                        <li class="progress" style="height:35px; margin-bottom: 5px;">
                                            ${task.shortName} 
                                            (ex.: ${task.executionCounter}, 
                                            twt: ${task.waitingTime}, 
                                            tpt: ${task.processingTime}) <br/>
                                            <span class="progress-bar" role="progressbar" aria-valuenow="70"
                                                  aria-valuemin="0" aria-valuemax="100" style="width:${task.getPercentageOfCurrentExecution()}%">
                                                <strong>${task.getPercentageOfCurrentExecution()}%</strong>
                                            </span>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </div>
                            <div class="tasks-box">
                                <p class="simulation-parameters">
                                    <span> Tasks in queue: <em>${processingUnit.queueLength}</em> </span> <br/>
                                    <span> Queue Value: <em>${processingUnit.queueValue}</em> </span> <br/>
                                </p>
                                <p  style="cursor:pointer;" class="details-toogle" onclick="toggleDetails(this)"> - hide details - </p>
                                <ul class="tasks-list ${taskDispatcher.name} ${processingUnit.name}"> 
                                    <c:forEach items="${processingUnit.getWaitingTasks()}" var="waitingTask">
                                        <li>${waitingTask.shortName} 
                                            (ex.: ${waitingTask.executionCounter}, 
                                            twt: ${waitingTask.waitingTime}, 
                                            tpt: ${waitingTask.processingTime}) </li>
                                        </c:forEach>
                                </ul> 
                            </div>
                        </div>
                    </c:forEach>
                </div>
                     <div class="paths">
                    <h5>Processing paths (if bold tasks should be processed)</h5>
                    <ul>
                        <c:forEach items="${taskDispatcher.getComingOutPaths()}" var="cPath">
                            <li id="${cPath.getId()}-${cPath.nextTaskDispatcher.getId()}" <c:if test="${cPath.nextTaskDispatcher.getId() != cPath.previousTaskDispatcher.getId()}">class="comingOutPath"</c:if>>
                                <c:choose>
                                    <c:when test="${cPath.isProcessing()}">
                                        <strong>${cPath.type.getName()}</strong>
                                    </c:when>    
                                    <c:otherwise>
                                        ${cPath.type.getName()}
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<hr/>
<div id="monitoring">
    <h4>Types statistics</h4>
    <table class="table table-condensed table-striped">
        <tr>
            <th>Type</th>
            <th>Generated tasks</th>
            <th>Finished tasks</th>
            <th>Avg. waiting time</th>
            <th>Avg. processing time</th>
        </tr>
        <c:forEach items="${processingSystem.getAllTypes()}" var="type">
            <tr>
                <td>${type.name}</td>
                <td>${type.getNumberOfGeneratedTasks()}</td>
                <td>${type.getNumberOfFinishedTasks()}</td>
                <td>${type.getAvgWaitingTime()}</td>
                <td>${type.getAvgProcessingTime()}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<hr/>
