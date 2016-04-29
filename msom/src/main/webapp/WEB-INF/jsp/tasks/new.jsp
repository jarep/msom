
<%-- 
    Document   : new
    Created on : 2016-04-20, 12:04:59
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
        <title>Task adding</title>
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
                    <h3>Dodawanie nowego zadania</h2>
                        <sf:form method="post" action="new" modelAttribute="task">
                            <div class="form-group">
                                <label for="name">Name of task</label>
                                <sf:input path="name" class="form-control" />
                            </div>

                            <sf:select path="taskType.id">
                                <sf:options items="${taskTypesList}" itemLabel="name" itemValue="id" />
                            </sf:select>    
                            <button type="submit" class="btn btn-default">Submit</button>
                        </sf:form>
                </div>
                <div class="col-md-3">
                    <nav id="tmp-nav">
                        <ul class="nav">
                            <li><a href="/msom/index.html">Home</a></li>
                            <li>
                                <ul class="nav">
                                    <li><a href="/msom/tasktypes">Task Types</a></li>
                                    <li><a href="/msom/tasks">Tasks</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Modules Management</a></li>
                            <li><a href="#">Probability Management</a></li>
                        </ul>
                    </nav>
                </div>
            </div>

        </div>
    </body>
</html>
