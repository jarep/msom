<%-- 
    Document   : classic
    Created on : Apr 30, 2016, 10:08:21 AM
    Author     : olaf
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<tiles:importAttribute name="javascripts"/>
        
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/msom/resources/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="/msom/resources/css/style.css" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.15.0/jquery.validate.min.js"></script>
        <title>MSOM</title>
    </head>
    <body>
        <tiles:insertAttribute name="header"></tiles:insertAttribute>
            <div class="container">
                <div class="row">
                    <div class="col-md-9">
                        <h1 class="page-header"><tiles:getAsString name="title" /></h1>
                    <tiles:insertAttribute name="body" />
                </div>
                <div class="col-md-3">
                    <tiles:insertAttribute name="menu"></tiles:insertAttribute>
                </div>
            </div>
        </div>
    <c:forEach var="script" items="${javascripts}">
        <script src="<c:url value="${script}"/>"></script>
    </c:forEach>
    </body>
</html>
