<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
window.setTimeout(function(){ document.location.reload(true); }, 10000);
</script>

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
            <h4>${taskDispatcher.name}</h4>
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
                                        ${task}<br/>
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
                                <span> Queue Value: <em>${processingUnit.queueValue}<em> </span> <br/>
                                <span> Avg. waiting time: <em>...</em> </span> 
                            </p>
                            <p class="details-toogle"> - Show/hide details - </p>
                            <ul class="tasks-list"> 
                                <c:forEach items="${processingUnit.getWaitingTasks()}" var="waitingTask">
                                    <li>${waitingTask}</li>
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
<div id="monitoring">
    <h4>Types statistics</h4>
    <div class="type">
        <h4>Type A</h4>
        <p>Generated tasks: <strong>75</strong></p>
        <p>Finished tasks: <strong>37</strong></p>
        <p>Avg. waiting time: <strong>485.2</strong></p>
        <p>Avg. processing time: <strong>3.5</strong></p>
    </div>

    <div class="type">
        <h4>Type B</h4>
        <p>Generated tasks: <strong>456</strong></p>
        <p>Finished tasks: <strong>357</strong></p>
        <p>Avg. waiting time: <strong>127</strong></p>
        <p>Avg. processing time: <strong>0.5</strong></p>
    </div>

    <div class="type">
        <h4>Type C</h4>
        <p>Generated tasks: <strong>3</strong></p>
        <p>Finished tasks: <strong>1</strong></p>
        <p>Avg. waiting time: <strong>245</strong></p>
        <p>Avg. processing time: <strong>55.2</strong></p>
    </div>
</div>
<div id="footer">
    <h4>Explanation of shortcuts</h4>
    <p>TWT - Total Waiting Time</p>
    <p>TPT - Total Processing Time</p>
    <p>PC - Processing Count</p>
</div>
