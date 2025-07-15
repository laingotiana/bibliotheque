
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Détail du Livre</title>
    <style>
        body { background: #f5f3f0; font-family: 'Segoe UI', Arial, sans-serif; }
        .container-main { background: #fff; border-radius: 18px; box-shadow: 0 4px 24px rgba(62,39,34,0.08); margin: 40px auto 0 auto; padding: 32px 24px; max-width: 700px; }
        h2 { color: #3e2723; font-weight: 700; text-align: center; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .info { margin-bottom: 20px; }
        .back-link { display: inline-block; margin-bottom: 20px; color: #795548; text-decoration: none; font-weight: bold; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>

<div class="container-main">
    <a href="livre_list" class="back-link">&larr; Retour à la liste</a>
    <h2>Détail du Livre</h2>
    <c:if test="${not empty error}">
        <div class="info" style="color:red;">${error}</div>
    </c:if>
    <c:if test="${not empty livre}">
        <div class="info">
            <strong>Titre:</strong> ${livre.titre}<br>
            <strong>Auteur:</strong> <c:choose>
                <c:when test="${not empty livre.auteur}">
                    ${livre.auteur.prenomAuteur} ${livre.auteur.nomAuteur}
                </c:when>
                <c:otherwise>N/A</c:otherwise>
            </c:choose><br>
            <strong>ISBN:</strong> ${livre.isbn}<br>
            <strong>Langue:</strong> ${livre.langue}<br>
            <strong>Année:</strong> ${livre.anneePublication}<br>
            <strong>Pages:</strong> ${livre.nbPage}<br>
            <strong>Synopsis:</strong> ${livre.synopsis}
        </div>
        <h3>Exemplaires</h3>
        <c:choose>
            <c:when test="${not empty exemplaires}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Exemplaire</th>
                            <th>Disponibilité</th>
                            <th>Restant disponible</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ex" items="${exemplaires}">
                            <tr>
                                <td>${ex.idExemplaire}</td>
                                <td>${ex.disponible}</td>
                                <td>
                                    <c:out value="${restantDispoMap[ex.idExemplaire]}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div style="color: #b71c1c;">Aucun exemplaire trouvé pour ce livre.</div>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
</body>
</html>