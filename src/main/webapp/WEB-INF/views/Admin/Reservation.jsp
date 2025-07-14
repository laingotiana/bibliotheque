<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste de mes Reservations</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(120deg, #fffbe9 0%, #f5f3f0 100%);
            min-height: 100vh;
        }
        .container-main {
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 4px 24px rgba(62,39,34,0.08);
            margin: 40px auto 0 auto;
            padding: 32px 24px;
            max-width: 1100px;
        }
        h2 {
            color: #3e2723;
            font-weight: 700;
            margin-bottom: 30px;
            text-align: center;
        }
        .table {
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(62,39,34,0.06);
        }
        .table thead th {
            background: #3e2723;
            color: #fffbe9;
            font-weight: 600;
            border: none;
        }
        .table tbody tr:nth-child(even) {
            background: #f5f3f0;
        }
        .table tbody tr:hover {
            background: #ffe0b2;
        }
        .rendu-button {
            background: #795548;
            color: #fffbe9;
            border: none;
            padding: 7px 18px;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 500;
            transition: background 0.2s, transform 0.2s;
        }
        .rendu-button:hover {
            background: #3e2723;
            color: #fff;
            transform: scale(1.04);
        }
        .rendu-button:disabled {
            background: #bdbdbd;
            color: #fff;
            cursor: not-allowed;
        }
        .status-pending {
            color: #ff9800;
            font-weight: bold;
        }
        .status-returned {
            color: #388e3c;
            font-weight: bold;
        }
        .status-confirmed {
            color: #17a2b8;
            font-weight: bold;
        }
        .message {
            background-color: #d4edda;
            color: #155724;
            text-align: center;
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
        }
        .erreur {
            background-color: #f8d7da;
            color: #721c24;
            text-align: center;
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #795548;
            text-decoration: none;
            font-size: 1rem;
            transition: color 0.3s ease;
        }
        .back-link:hover {
            color: #3e2723;
            text-decoration: underline;
        }
        @media (max-width: 991px) {
            .container-main {
                padding: 18px 5px;
            }
            .table {
                font-size: 0.97rem;
            }
        }
        @media (max-width: 600px) {
            .container-main {
                margin: 10px 0 0 0;
                padding: 8px 2px;
            }
            h2 {
                font-size: 1.2rem;
            }
            .table {
                font-size: 0.93rem;
            }
        }
    </style>
    <script>
        function confirmerReservation(reservationId) {
            if (confirm('Êtes-vous sûr de vouloir confirmer cette réservation ?')) {
                // Ici vous pouvez ajouter une requête AJAX ou un formulaire pour confirmer
                alert('Fonctionnalité de confirmation à implémenter pour la réservation ' + reservationId);
            }
        }
        
        function annulerReservation(reservationId) {
            if (confirm('Êtes-vous sûr de vouloir annuler cette réservation ?')) {
                // Ici vous pouvez ajouter une requête AJAX ou un formulaire pour annuler
                alert('Fonctionnalité d\'annulation à implémenter pour la réservation ' + reservationId);
            }
        }
    </script>
</head>
<body>
    <div class="container-main">
        <h2>Liste des reservations</h2>
        <!-- Afficher les messages de confirmation ou d'erreur -->
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="erreur">${erreur}</div>
        </c:if>
        <c:if test="${empty reservations}">
            <p style="color: #3e2723; text-align: center;">Aucune reservation trouvee.</p>
        </c:if>
        <c:if test="${not empty reservations}">
            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Date reservation</th>
                            <th>Date debut prêt</th>
                            <th>Date fin prêt</th>
                            <th>Livre</th>
                            <th>Statut</th>
                            <th>Adherant</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reservation" items="${reservations}">
                            <tr>
                                <td>${reservation.dateReservation}</td>
                                <td>${reservation.dateDebutPret}</td>
                                <td>${reservation.dateFinPret}</td>
                                <td>${reservation.exemplaire.livre.titre}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${reservation.status.getNomStatus() == 'en attent' || reservation.status.getNomStatus() == 'En attente'}">
                                            <span class="status-pending">En attente</span>
                                            <form action="${pageContext.request.contextPath}/accepter_reservation" method="get" class="return-form d-inline-block ms-2">
                                                <input type="hidden" name="id_reservation" value="${reservation.getIdReservation()}">
                                                <input type="submit" class="rendu-button" value="Accepter">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                            </form>
                                        </c:when>
                                        <c:when test="${reservation.status.getNomStatus() == 'confirmee' || reservation.status.getNomStatus() == 'Confirmée' || reservation.status.getNomStatus() == 'Confirme'}">
                                            <span class="status-confirmed">Confirmée</span>
                                        </c:when>
                                        <c:when test="${reservation.status.getNomStatus() == 'annulee' || reservation.status.getNomStatus() == 'Annulée'}">
                                            <span style="color: #dc3545; font-weight: bold;">Annulée</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span>${reservation.status.getNomStatus()}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${reservation.adherant.nom}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <br>
        <a href="/" class="back-link">Retour a l'accueil</a>
    </div>
    <!-- Bootstrap JS Bundle (with Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>