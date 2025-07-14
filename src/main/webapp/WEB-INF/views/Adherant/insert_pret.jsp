<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demande de pret</title>
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
            justify-content: center;
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
        .form-group {
            margin-bottom: 22px;
            width: 100%;
        }
        label {
            display: block;
            font-size: 1.05rem;
            margin-bottom: 8px;
            color: #a67c52;
            font-weight: 500;
        }
        select, input[type="date"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0c9a6;
            border-radius: 7px;
            font-size: 1.08rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        select:focus, input[type="date"]:focus {
            outline: none;
            border-color: #a67c52;
            box-shadow: 0 0 7px rgba(166, 124, 82, 0.18);
        }
        button {
            background: #a67c52;
            color: #fffbe9;
            border: none;
            padding: 13px 0;
            border-radius: 7px;
            font-size: 1.15rem;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s, box-shadow 0.3s;
            position: relative;
            overflow: hidden;
            width: 100%;
            margin-top: 10px;
        }
        button:hover {
            background: #7c5c3b;
            color: #fff;
            transform: scale(1.03);
            box-shadow: 0 8px 24px rgba(166,124,82,0.13);
        }
        .message {
            margin-top: 16px;
            padding: 16px;
            border-radius: 6px;
            text-align: center;
            font-size: 0.95rem;
        }
        .success {
            background-color: #d1fae5;
            color: #065f46;
        }
        .error {
            background-color: #fee2e2;
            color: #991b1b;
        }
        @media (max-width: 991px) {
            .content-area {
                padding: 18px 5px;
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
        }
    </style>
</head>
<body>
    <div class="main-wrapper">
        <div class="left-img"></div>
        <div class="content-area">
            <img src="https://cdn-icons-png.flaticon.com/512/29/29302.png" alt="Logo Livre" class="logo" />
            <h2>Demander un prêt de livre</h2>
            <form action="insert_pret" method="post" style="width:100%;max-width:400px;">
                <div class="form-group">
                    <label for="id_exemplaire">Livre (exemplaire) :</label>
                    <select id="id_exemplaire" name="id_exemplaire" required>
                        <c:forEach var="exemplaire" items="${exemplaires}">
                            <option value="${exemplaire.idExemplaire}">
                                ${exemplaire.livre.titre} (Exemplaire ${exemplaire.idExemplaire})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="id_type">Type de prêt :</label>
                    <select id="id_type" name="id_type" required>
                        <c:forEach var="typePret" items="${types}">
                            <option value="${typePret.idType}">
                                ${typePret.type}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="date_debut">Date debut :</label>
                    <input type="date" id="date_debut" name="date_debut" required>
                </div>
                <div class="form-group">
                    <label for="date_fin">Date fin :</label>
                    <input type="date" id="date_fin" name="date_fin" required>
                </div>
                <div>
                    <button type="submit">Demander le prêt</button>
                </div>
            </form>
            <c:if test="${not empty message}">
                <div class="message success">${message}</div>
            </c:if>
            <c:if test="${not empty erreur}">
                <div class="message error">${erreur}</div>
            </c:if>
        </div>
    </div>
    <!-- Bootstrap JS Bundle (with Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>