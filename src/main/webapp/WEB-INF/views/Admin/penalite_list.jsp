<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Pénalités</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Liste des pénalités</h2>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>#</th>
            <th>Nom Adhérent</th>
            <th>Prénom Adhérent</th>
            <th>Début pénalité</th>
            <th>Fin pénalité</th>
            <th>Durée (jours)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="penalite" items="${penalites}">
            <tr>
                <td>${penalite.idPenalite}</td>
                <td>${penalite.adherant.nom}</td>
                <td>${penalite.adherant.prenom}</td>
                <td>${penalite.debutPenalite}</td>
                <td>${penalite.finPenalite}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty penalite.debutPenalite && not empty penalite.finPenalite}">
                            <c:set var="diffMillis" value="${penalite.finPenalite.time - penalite.debutPenalite.time}" />
                            <c:set var="days" value="${diffMillis / (1000*60*60*24)}" />
                            ${days}
                        </c:when>
                        <c:otherwise>
                            N/A
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/admin_home" class="btn btn-secondary">Retour</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
