<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


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
<tiles:insertDefinition name="processingsystem/refresh" />  
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
    <p><strong>twt</strong> - Total Waiting Time</p>
    <p><strong>tpt</strong> - Total Processing Time</p>
    <p><strong>ex.</strong> - Execution counter - how many times this task was processed</p>
</div>
