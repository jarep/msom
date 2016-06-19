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
    <p class="btn btn-info" onclick="refreshView()">REFRESH VIEW</p> 
</div>
<input type="hidden" id="isLocked" name="isLocked" value="${isLocked}"/>
<div class="simulation-container">
<tiles:insertDefinition name="processingsystem/refresh" />  
</div>
<div id="footer">
    <h4>Explanation of shortcuts</h4>
    <p><strong>twt</strong> - Total Waiting Time</p>
    <p><strong>tpt</strong> - Total Processing Time</p>
    <p><strong>ex.</strong> - Execution counter - how many times this task was processed</p>
</div>
