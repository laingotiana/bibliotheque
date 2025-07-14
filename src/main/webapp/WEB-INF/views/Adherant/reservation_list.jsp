<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste de mes réservations - Bibliotheque</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(120deg, #fffbe9 0%, #f5f3f0 100%);
            min-height: 100vh;
        }
        .main-wrapper {
            display: flex;
            min-height: 100vh;
            align-items: stretch;
        }
        .left-img {
            flex: 0 0 320px;
            background: url('https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=600&q=80') center center/cover no-repeat;
            min-width: 220px;
            min-height: 100%;
        }
        .content-area {
            flex: 1 1 auto;
            padding: 48px 32px;
            background: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
        }
        .logo {
            width: 70px;
            height: 70px;
            margin-bottom: 18px;
        }
        h2 {
            color: #a67c52;
            font-weight: 700;
            margin-bottom: 30px;
            text-align: center;
        }
        .table {
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(166,124,82,0.06);
        }
        .table thead th {
            background: #a67c52;
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
        .action-button {
            background: #a67c52;
            color: #fffbe9;
            border: none;
            padding: 7px 18px;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 500;
            transition: background 0.2s, transform 0.2s;
        }
        .action-button:hover {
            background: #7c5c3b;
            color: #fff;
            transform: scale(1.04);
        }
        .action-button.danger {
            background: #dc3545;
        }
        .action-button.danger:hover {
            background: #c82333;
        }
        .status-en-attente {
            color: #ff9800;
            font-weight: bold;
        }
        .status-confirmee {
            color: #388e3c;
            font-weight: bold;
        }
        .status-annulee {
            color: #dc3545;
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
            color: #a67c52;
            text-decoration: none;
            font-size: 1rem;
            transition: color 0.3s ease;
        }
        .back-link:hover {
            color: #7c5c3b;
            text-decoration: underline;
        }
        @media (max-width: 991px) {
            .content-area {
                padding: 18px 5px;
            }
            .table {
                font-size: 0.97rem;
            }
        }
        @media (max-width: 600px) {
            .main-wrapper {
                flex-direction: column;
            }
            .left-img {
                min-height: 120px;
                width: 100%;
            }
            .content-area {
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
</head>
<body>
    <div class="main-wrapper">
        <div class="left-img"></div>
        <div class="content-area">
            <img src="https://cdn-icons-png.flaticon.com/512/29/29302.png" alt="Logo Livre" class="logo" />
            <h2>Liste de mes reservations</h2>
            <!-- Afficher les messages de confirmation ou d'erreur -->
            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>
            <c:if test="${not empty erreur}">
                <div class="erreur">${erreur}</div>
            </c:if>
            <c:if test="${empty reservations}">
                <p style="color: #a67c52; text-align: center;">Aucune reservation trouvee.</p>
            </c:if>
            <c:if test="${not empty reservations}">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Livre</th>
                                <th>Exemplaire</th>
                                <th>Date de reservation</th>
                                <th>Date debut prêt</th>
                                <th>Date fin prêt</th>
                                <th>Statut</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reservation" items="${reservations}">
                                <tr>
                                    <td>${reservation.exemplaire.livre.titre}</td>
                                    <td>${reservation.exemplaire.idExemplaire}</td>
                                    <td>${reservation.dateReservation}</td>
                                    <td>${reservation.dateDebutPret}</td>
                                    <td>${reservation.dateFinPret}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.status.nomStatus == 'en attent' || reservation.status.nomStatus == 'En attente'}">
                                                <span class="status-en-attente">En attente</span>
                                            </c:when>
                                            <c:when test="${reservation.status.nomStatus == 'confirmee' || reservation.status.nomStatus == 'Confirmée'}">
                                                <span class="status-confirmee">Confirmée</span>
                                            </c:when>
                                            <c:when test="${reservation.status.nomStatus == 'annulee' || reservation.status.nomStatus == 'Annulée'}">
                                                <span class="status-annulee">Annulée</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${reservation.status.nomStatus}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${reservation.status.nomStatus == 'en attent' || reservation.status.nomStatus == 'En attente'}">
                                            <form action="annuler_reservation" method="post" style="display: inline;">
                                                <input type="hidden" name="reservationId" value="${reservation.idReservation}">
                                                <button type="submit" class="action-button danger" onclick="return confirm('Êtes-vous sûr de vouloir annuler cette réservation ?')">Annuler</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <br>
            <a href="/" class="back-link">Retour à l'accueil</a>
        </div>
    </div>
    <!-- Bootstrap JS Bundle (with Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>