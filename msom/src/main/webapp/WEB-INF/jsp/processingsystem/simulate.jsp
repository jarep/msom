<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Processing System <strong>${processingSystem.name}</strong></h3>

<div class="btn-group">
    <a class="btn btn-danger" href="/msom/processingsystem/reset/${processingSystem.id}">RESET</a>
    <a class="btn btn-success" href="/msom/processingsystem/start/${processingSystem.id}">START</a>
    <a class="btn btn-warning" href="/msom/processingsystem/stop/${processingSystem.id}">STOP</a> 
    <a class="btn btn-info" href="/msom/processingsystem/simulate/${processingSystem.id}">REFRESH VIEW</a> 
</div>
<div style="margin: 10px 0; padding: 25px;" class="bg-info">
    <samp>${message}</samp>
</div>

<div class="processing-container">
    <c:forEach items="${processingSystem.getTaskDispatchers()}" var="taskDispatcher">
        <div class="task-dispatcher">
            <h4>${taskDispatcher.name} <c:if test="${taskDispatcher.isFirst()}"><em>- first controller -</em></c:if></h4>
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
                                <span> Processed  <em>...</em> </span> <br/>
                                <span> Avg. processing time: <em>...</em> </span> 
                            </p>
                            <p class="details-toogle"> - Show/hide details - </p>
                            <ul class="tasks-list"> 
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
                                <span> Avg. waiting time: <em>...</em> </span> 
                            </p>
                            <p class="details-toogle"> - Show/hide details - </p>
                            <ul class="tasks-list"> 
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
            <div>
                <h6>Coming out paths</h6>
                <ul class="paths">
                    <c:forEach items="${taskDispatcher.getComingOutPaths()}" var="cPath">
                        <li>${cPath.type.getName()} (processing: ${cPath.processing}) -> ${cPath.nextTaskDispatcher.getName()}</li>
                        </c:forEach>
                </ul>
            </div>
        </div>
    </c:forEach>
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
<div id="footer">
    <h4>Explanation of shortcuts</h4>
    <p><strong>twt</strong> - Total Waiting Time</p>
    <p><strong>tpt</strong> - Total Processing Time</p>
    <p><strong>ex.</strong> - Execution counter - how many times this task was processed</p>
</div>
