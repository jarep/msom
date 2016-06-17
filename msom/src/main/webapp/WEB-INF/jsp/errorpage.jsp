<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<div class="alert alert-danger" role="alert">
      <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
      <span>Error: Page not found<c:if test="${not empty errorMsg}"> (${errorMsg})</c:if>!</span>
</div>
