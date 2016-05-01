<%-- 
    Document   : classic
    Created on : Apr 30, 2016, 10:08:21 AM
    Author     : olaf
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/msom/resources/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="/msom/resources/css/style.css" type="text/css">
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
    </body>
</html>
