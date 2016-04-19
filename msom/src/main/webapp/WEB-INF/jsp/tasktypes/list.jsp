<%-- 
    Document   : list
    Created on : 2016-04-19, 19:03:36
    Author     : jaroslaw
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/msom/resources/css/bootstrap.min.css" type="text/css">

        <title>Task types list</title>
    </head>
    <body>
        <h1>Available task types.</h1>
        <table class="table">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Difficulty</th>
            </tr>
            <c:forEach items="#{taskTypesList}" var="type">
                <tr>
                    <td>${type.id}</td>
                    <td>${type.name}</td>
                    <td>${type.difficulty}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
