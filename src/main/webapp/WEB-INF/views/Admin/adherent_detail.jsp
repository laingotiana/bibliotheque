<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Détail de l'Adhérent</title>
    <style>
        body { background: #f5f3f0; font-family: 'Segoe UI', Arial, sans-serif; }
        .container-main { background: #fff; border-radius: 18px; box-shadow: 0 4px 24px rgba(62,39,34,0.08); margin: 40px auto 0 auto; padding: 32px 24px; max-width: 700px; }
        h2 { color: #3e2723; font-weight: 700; text-align: center; }
        h3 { color: #3e2723; font-weight: 600; margin-top: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .info { margin-bottom: 20px; }
        .back-link { display: inline-block; margin-bottom: 20px; color: #795548; text-decoration: none; font-weight: bold; }
        .back-link:hover { text-decoration: underline; }
        .error { color: #b71c1c; text-align: center; }
    </style>
</head>
<body>
<div class="container-main">
    <a href="adherent_list" class="back-link">← Retour à la liste</a>
    <h2>Détail de l'Adhérent</h2>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty adherant}">
        <div class="info">
            <strong>Nom:</strong> ${adherant.nom}<br>
            <strong>Prénom:</strong> ${adherant.prenom}<br>
            <strong>Profil:</strong> ${adherant.profil.typeAdherant} <br>
            <strong>Quota Total Prêts:</strong> ${quotaPretTotal}<br>
            <strong>Quota Restant Prêts:</strong> ${quotaPretRestant}<br>
            <strong>Quota Total Réservations:</strong> ${quotaReservationTotal}<br>
            <strong>Quota Restant Réservations:</strong> ${quotaReservationRestant}<br>
        </div>
        <h3>Abonnements</h3>
        <c:choose>
            <c:when test="${not empty abonnements}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Date Début</th>
                            <th>Date Fin</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="abonnement" items="${abonnements}">
                            <tr>
                                <td><fmt:formatDate value="${abonnement.dateDebut}" pattern="yyyy-MM-dd" /></td>
                                <td><fmt:formatDate value="${abonnement.dateFin}" pattern="yyyy-MM-dd" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div style="color: #b71c1c;">Aucun abonnement trouvé pour cet adhérent.</div>
            </c:otherwise>
        </c:choose>
        <h3>Pénalités</h3>
        <c:choose>
            <c:when test="${not empty penalites}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Date Début</th>
                            <th>Date Fin</th>
                            <th>Raison</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="penalite" items="${penalites}">
                            <tr>
                                <td><fmt:formatDate value="${penalite.debutPenalite}" pattern="yyyy-MM-dd" /></td>
                                <td><fmt:formatDate value="${penalite.finPenalite}" pattern="yyyy-MM-dd" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div style="color: #b71c1c;">Aucune pénalité trouvée pour cet adhérent.</div>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
</body>
</html>