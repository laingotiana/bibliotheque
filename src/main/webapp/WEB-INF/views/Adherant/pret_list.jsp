<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listes de mes prêts</title>
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
        .action-button:disabled {
            background: #bdbdbd;
            color: #fff;
            cursor: not-allowed;
        }
        .status-returned {
            color: #388e3c;
            font-weight: bold;
        }
        .status-pending {
            color: #ff9800;
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
            <h2>Liste de mes prêts</h2>
            <!-- Afficher les messages de confirmation ou d'erreur -->
            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>
            <c:if test="${not empty erreur}">
                <div class="erreur">${erreur}</div>
            </c:if>
            <c:if test="${empty prets}">
                <p style="color: #a67c52; text-align: center;">Aucun prêt trouve.</p>
            </c:if>
            <c:if test="${not empty prets}">
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Livre</th>
                                <th>Exemplaire</th>
                                <th>Date debut</th>
                                <th>Date fin</th>
                                <th>Type de prêt</th>
                                <th>Rendu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pret" items="${prets}">
                                <tr>
                                    <td>${pret.exemplaire.livre.titre}</td>
                                    <td>${pret.exemplaire.idExemplaire}</td>
                                    <td>${pret.dateDebut}</td>
                                    <td>${pret.dateFin}</td>
                                    <td>${pret.typePret.type}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pret.rendu == 1}">
                                                <span class="status-returned">Le livre est rendu</span>
                                            </c:when>
                                            <c:when test="${pret.rendu == 0}">
                                                <span class="status-pending">En cours de lecture</span>
                                                <form action="prolonger" method="post" style="display:inline;">
                                                <input type="hidden" name="pretId" value="${pret.idPret}">
                                                <button type="submit" class="link-pret">Prolonger</button>
                                            </form>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-pending">En cours de lecture</span>
                                            </c:otherwise>
                                        </c:choose>
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