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
        <link rel="stylesheet" href="/msom/resources/css/style.css" type="text/css">
        <title>Task types list</title>
    </head>
    <body>
        <header id="tmp-header">

        </header>
        <div class="container">

            <div class="row">
                <div class="col-md-9">
                    <h1 class="page-header">Zarządzanie typami zadań.</h1>
                    <p class="lead">Typ zadania pozwala określić złożoność zadania. 
                        Każde zadanie ma przypisany typ. Poszczególne moduły przetwarzające potrafią 
                        obsługiwać zadania konkretnego typu.
                    </p>
                    <p >
                        <a class="btn btn-default" href="#">Dodaj nowy typ</a>
                    </p>
                    <h2>Dostępne typy</h1>
                    <table class="table">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Difficulty</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach items="#{taskTypesList}" var="type">
                            <tr>
                                <td>${type.id}</td>
                                <td>${type.name}</td>
                                <td>${type.difficulty}</td>
                                <td><a href="#">Edit</a></td>
                                <td><a href="#">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="col-md-3">
                    <nav id="tmp-nav">
                        <ul class="nav">
                            <li><a href="index.html">Home</a></li>
                            <li>
                                <a href="#">Task Management</a>
                                <ul class="nav">
                                    <li><a href="#">Task Types</a></li>
                                    <li><a href="#">Tasks</a></li>
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
