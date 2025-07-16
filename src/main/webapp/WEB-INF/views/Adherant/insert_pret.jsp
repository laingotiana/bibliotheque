<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Insérer un Prêt</title>
    <link rel="stylesheet" href="/assets/css/hello.css">
    <style>
        body { background: #f5f3f0; font-family: 'Segoe UI', Arial, sans-serif; }
        .container-main { background: #fff; border-radius: 18px; box-shadow: 0 4px 24px rgba(62,39,34,0.08); margin: 40px auto 0 auto; padding: 32px 24px; max-width: 900px; }
        h1 { color: #3e2723; font-weight: 700; text-align: center; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; font-weight: 600; color: #3e2723; margin-bottom: 8px; }
        .form-group select, .form-group input { width: 100%; padding: 10px; border: 1px solid #d7ccc8; border-radius: 6px; font-size: 16px; }
        .form-group select:focus, .form-group input:focus { outline: none; border-color: #795548; }
        .btn-submit { background: #3e2723; color: #fffbe9; padding: 12px 24px; border: none; border-radius: 6px; cursor: pointer; font-size: 16px; }
        .btn-submit:hover { background: #795548; }
        .message { color: green; text-align: center; margin-bottom: 20px; }
        .erreur { color: red; text-align: center; margin-bottom: 20px; }
    </style>
</head>
<body>
<div class="container-main">
    <h1>Insérer un Prêt</h1>
    <c:if test="${not empty erreur}">
        <p class="erreur">${erreur}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>
    <form action="insert_pret" method="post">
        <div class="form-group">
            <label for="id_exemplaire">Exemplaire :</label>
            <select name="id_exemplaire" id="id_exemplaire" required>
                <c:forEach var="exemplaire" items="${exemplaires}">
                    <option value="${exemplaire.idExemplaire}">
                        ${exemplaire.livre.titre} (ID: ${exemplaire.idExemplaire}, Disponible: ${exemplaire.disponible})
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="id_type">Type de Prêt :</label>
            <select name="id_type" id="id_type" required>
                <c:forEach var="type" items="${types}">
                    <option value="${type.idType}">${type.type}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="date_debut">Date de Début :</label>
            <input type="date" name="date_debut" id="date_debut" required>
        </div>
        <button type="submit" class="btn-submit">Insérer Prêt</button>
    </form>
</div>
</body>
</html>