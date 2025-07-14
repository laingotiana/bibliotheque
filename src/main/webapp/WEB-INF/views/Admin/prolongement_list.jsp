<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Prolongements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Liste des Prolongements</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Adhérent</th>
                    <th>Livre</th>
                    <th>Date début</th>
                    <th>Date fin actuelle</th>
                    <th>Date demandée</th>
                    <th>Statut</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="prolongement" items="${prolongements}">
                    <tr>
                        <td>${prolongement.adherent.nom}</td>
                        <td>${prolongement.pret.exemplaire.livre.titre}</td>
                        <td>${prolongement.pret.dateDebut}</td>
                        <td>${prolongement.pret.dateFin}</td>
                        <td>${prolongement.dateProlongement}</td>
                        <td>
                            <c:choose>
                                <c:when test="${prolongement.status.nomStatus == 'en attent' || prolongement.status.nomStatus == 'En attente'}">
                                    <span class="status-pending">En attente</span>
                                </c:when>
                                <c:when test="${prolongement.status.nomStatus == 'confirmee' || prolongement.status.nomStatus == 'Confirmée' || prolongement.status.nomStatus == 'Confirme'}">
                                    <span class="status-confirmed">Confirmée</span>
                                </c:when>
                                <c:when test="${prolongement.status.nomStatus == 'annulee' || prolongement.status.nomStatus == 'Annulée'}">
                                    <span style="color: #dc3545; font-weight: bold;">Annulée</span>
                                </c:when>
                                <c:otherwise>
                                    <span>${prolongement.status.nomStatus}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${prolongement.status.nomStatus == 'en attent' || prolongement.status.nomStatus == 'En attente'}">
                                <form action="accepter_prolongement" method="post" style="display:inline;">
                                    <input type="hidden" name="idProlongement" value="${prolongement.idProlongement}" />
                                    <button type="submit" class="btn btn-success btn-sm">Accepter</button>
                                </form>
                                <form action="refuser_prolongement" method="post" style="display:inline;">
                                    <input type="hidden" name="idProlongement" value="${prolongement.idProlongement}" />
                                    <button type="submit" class="btn btn-danger btn-sm">Refuser</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="/admin_home" class="btn btn-secondary">Retour</a>
    </div>
</body>
</html>
