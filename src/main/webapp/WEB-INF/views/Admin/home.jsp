<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Admin</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(120deg, #fffbe9 0%, #f5f3f0 100%);
            min-height: 100vh;
        }
        .navbar-custom {
            background: #3e2723;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
        .navbar-custom .navbar-brand {
            color: #fffbe9;
            font-weight: bold;
            font-size: 1.7rem;
            letter-spacing: 1px;
        }
        .navbar-custom .nav-link {
            color: #fffbe9;
            font-weight: 500;
            margin-right: 10px;
            border-radius: 5px;
            transition: background 0.2s, color 0.2s;
        }
        .navbar-custom .nav-link.active, .navbar-custom .nav-link:hover {
            background: #795548;
            color: #fff;
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
        .date-picker-container label {
            font-size: 0.95rem;
            color: #3e2723;
            margin-right: 8px;
        }
        .date-picker-container input[type="date"] {
            border-radius: 5px;
            border: 1px solid #bdbdbd;
            padding: 3px 8px;
        }
        /* LOGO ADMIN NAVBAR */
        .logo-admin {
            width: 70px;
            height: 70px;
            margin-right: 12px;
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
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container-fluid">
            <img src="https://cdn-icons-png.flaticon.com/512/29/29302.png" alt="Logo Livre" class="logo-admin"  />
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon" style="filter: invert(1);"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#home">Accueil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="render_gestion">Gestion Reservation</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#services">Services</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#contact">Contact</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-main">
        <h2>Liste des Prêts</h2>
        <div class="table-responsive">
            <table class="table align-middle">
                <thead>
                    <tr>
                        <th>Date Début</th>
                        <th>Date Fin</th>
                        <th>Statut</th>
                        <th>Type</th>
                        <th>Adhérent</th>
                        <th>Exemplaire</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pret" items="${prets}">
                        <tr>
                            <td><fmt:formatDate value="${pret.dateDebut}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${pret.dateFin}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${pret.rendu == 1}">
                                        <span class="status-returned">Le livre est rendu</span>
                                    </c:when>
                                    <c:when test="${pret.rendu == 0}">
                                        <span class="status-pending">En cours de lecture</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-pending">En cours de lecture</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${pret.typePret.type}</td>
                            <td>${pret.adherant.nom} ${pret.adherant.prenom}</td>
                            <td>${pret.exemplaire.livre.titre} (ID: ${pret.exemplaire.idExemplaire})</td>
                            <td>
                                <c:choose>
                                    <c:when test="${pret.rendu == 1}">
                                        <button class="rendu-button" disabled>
                                            <i class="bi bi-check-circle"></i> Livre rendu
                                        </button>
                                    </c:when>
                                    <c:when test="${pret.rendu == 0}">
                                        <form action="${pageContext.request.contextPath}/rendre_livre" method="get" class="return-form">
                                            <input type="hidden" name="pretId" value="${pret.idPret}">
                                            <div class="date-picker-container mb-2">
                                                <label for="date_rendu_${pret.idPret}">Date de retour :</label>
                                                <c:set var="today" value="<%= new java.util.Date() %>"/>
                                                <input type="date" id="date_rendu_${pret.idPret}" name="date_rendu" 
                                                       value="<fmt:formatDate value='${today}' pattern='yyyy-MM-dd'/>"
                                                >
                                            </div>
                                            <button type="submit" class="rendu-button">
                                                <i class="bi bi-book"></i> Rendre
                                            </button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-pending">
                                            <i class="bi bi-hourglass-split"></i> En cours
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty prets}">
                        <tr>
                            <td colspan="7" class="text-center">Aucun prêt enregistré</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Bootstrap JS Bundle (with Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</body>
</html>