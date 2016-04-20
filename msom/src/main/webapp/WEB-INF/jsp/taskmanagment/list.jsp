<%-- 
    Document   : list
    Created on : 2016-04-20, 13:11:33
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
        <link rel="stylesheet" href="/msom/resources/css/style.css" type="text/css">
        <title>Task list</title>
    </head>
    <body>
        <header id="tmp-header">

        </header>
        <div class="container">

            <div class="row">
                <div class="col-md-9">
                    <h1 class="page-header">Zarządzanie zadaniami.</h1>
                    <p class="lead">
                        Zadanie aktualnie posiada tylko nazwę i typ zadania.
                    </p>
                    <h2>Zadania w systemie</h2>
                    <table class="table">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Type - difficulty</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach items="#{tasksList}" var="task">
                            <tr>
                                <td>${task.id}</td>
                                <td>${task.name}</td>
                                <td>${task.taskType.name}</td>
                                <td>${task.taskType.difficulty}</td>
                                <td>
                                        <sf:form action="taskmanagment/remove/${task.id}" method="post">
                                            <input type="submit" class="btn-sm btn-danger" value="Delete" />
                                        </sf:form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
     
                </div>
                <div class="col-md-3">
                    <nav id="tmp-nav">
                        <ul class="nav">
                            <li><a href="/msom/index.html">Home</a></li>
                            <li>
                                <a href="/msom/taskmanagment">Task Management</a>
                                <ul class="nav">
                                    <li><a href="/msom/tasktypes">Task Types</a></li>
                                    <li><a href="/msom/tasks">Tasks</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Modules Managemenet</a></li>
                            <li><a href="#">Probability Management</a></li>
                        </ul>
                    </nav>
                </div>
            </div>

        </div>
    </body>
</html>
