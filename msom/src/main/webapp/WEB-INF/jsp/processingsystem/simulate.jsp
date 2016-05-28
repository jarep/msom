<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Processing System <strong>${processingSystem.name}</strong></h3>
<span>Temporary buttons:</span>
<a href="/msom/processingsystem/reset/${processingSystem.id}">Reset</a> |
<a href="/msom/processingsystem/simulate/${processingSystem.id}">Next simulation step</a>
<br/>
<span>Proposed buttons:</span>
<a href="#">Start simulation</a> |
<a href="#">Stop simulation</a> |
<a href="#">Clean simulation data and unlock processing system</a>


<div class="processing-container">
    <c:forEach items="${processingSystem.getTaskDispatchers()}" var="taskDispatcher">
        <div class="task-dispatcher">
            <h4>${taskDispatcher.name}</h4>
            <div class="processing-units">
                <h5>Processing units:</h5>
                <c:forEach items="${taskDispatcher.getProcessingUnits()}" var="processingUnit">
                    <div class="single-unit">
                        <h6><strong>${processingUnit.name}</strong> [cores: ${processingUnit.cores}] [eff: ${processingUnit.efficiency}]</h6> 
                        <p class="unit-details">
                            Available types: 
                            <c:forEach items="${processingUnit.getAvailableTypes()}" var="availableType">
                                <span>${availableType.name}, </span>
                            </c:forEach>
                        </p>
                        <div class="tasks-box">
                            <p class="simulation-parameters">
                                <span> Tasks in progress <em>4</em> </span> <br/>
                                <span> Processed  <em>51.50 %</em> </span> <br/>
                                <span> Avg. processing time: <em>48</em> </span> 
                            </p>
                            <p class="details-toogle"> - Show/hide details - </p>
                            <ul class="tasks-list"> 
                                <li>ID 47 - Task1 [A] 55.01% {TWT: 254; TPT: 0; PC: 0}</li>
                                <li>ID 48 - Task2 [B] 05.54% {TWT: 124; TPT: 7; PC: 1}</li>
                                <li>ID 50 - Task1 [A] 78.12% {TWT: 455; TPT: 0; PC: 0}</li>
                                <li>ID 57 - Task5 [A] 38.14% {TWT: 156; TPT: 12; PC: 2}</li>
                            </ul>
                        </div>
                        <div class="tasks-box">
                            <p class="simulation-parameters">
                                <span> Tasks in queue: <em>${processingUnit.queueLength}</em> </span> <br/>
                                <span> Queue Value: <em>${processingUnit.queueValue}</em> </span> <br/>
                                <span> Avg. waiting time: <em>48</em> </span> 
                            </p>
                            <p class="details-toogle"> - Show/hide details - </p>
                            <ul class="tasks-list"> 
                                <li>ID 65 - Task1 [A] {TWT: 2155; TPT: 0; PC: 0}</li>
                                <li>ID 66 - Task2 [B] {TWT: 123; TPT: 0; PC: 0}</li>
                                <li>ID 67 - Task1 [A] {TWT: 456; TPT: 125; PC: 3}</li>
                                <li>ID 69 - Task5 [A] {TWT: 68; TPT: 0; PC: 0}</li>
                                <li>ID 72 - Task1 [A] {TWT: 1544; TPT: 356; PC: 2}</li>
                                <li>ID 73 - Task5 [A] {TWT: 14; TPT: 0; PC: 0}</li>
                                <li> - - - - - - ................. - - - - - - </li>
                            </ul> 
                        </div>

                    </div>
                </c:forEach>
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