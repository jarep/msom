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
                    <h1 class="page-header">MSOM</h1>
                    <h2>Modelowanie Systemów Obsługi Masowej</h2>
                    <p class="lead">
                        System jest w początkowej fazie - aktualnie działa tylko 
                        zarządzanie typami zadań -> Link "Task Types" w menu po prawej
                        oraz lista zadań -> Link "Tasks"
                    </p>
                    <hr />
                    <h3>Wyczyszczenie bazy danych i utworzenie przykładowych danych</h3>         
                    <a class="btn btn-danger" href="fakedatagenerator">Generuj przykładowe dane</a>
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
