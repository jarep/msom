<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<p class="lead">
</p>
<h3>Processing System <strong>${processingSystem.name}</strong></h3>

<div class="btn-group">
    <a class="btn btn-danger" href="/msom/processingsystem/reset/${processingSystem.id}">RESET</a>
    <a class="btn btn-success" href="/msom/processingsystem/start/${processingSystem.id}">START</a>
    <a class="btn btn-warning" href="/msom/processingsystem/stop/${processingSystem.id}">STOP</a> 
</div>
<div style="margin: 10px 0; padding: 25px;" class="bg-info">
    <samp>${message}</samp>
</div>
