<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Liste des Adhérents</title>
    <link rel="stylesheet" href="/assets/css/hello.css">
    <style>
        body { background: #f5f3f0; font-family: 'Segoe UI', Arial, sans-serif; }
        .container-main { background: #fff; border-radius: 18px; box-shadow: 0 4px 24px rgba(62,39,34,0.08); margin: 40px auto 0 auto; padding: 32px 24px; max-width: 900px; }
        h1 { color: #3e2723; font-weight: 700; text-align: center; }
        .table { width: 100%; background: #fff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 8px rgba(62,39,34,0.06); margin-bottom: 32px; }
        .table th, .table td { padding: 10px 14px; text-align: left; }
        .table thead th { background: #3e2723; color: #fffbe9; font-weight: 600; border: none; }
        .table tbody tr:nth-child(even) { background: #f5f3f0; }
        .table tbody tr:hover { background: #ffe0b2; }
        .link-adherent { color: #795548; font-weight: bold; text-decoration: none; }
        .link-adherent:hover { text-decoration: underline; color: #3e2723; }
    </style>
</head>
<body>
<div class="container-main">
    <h1>Liste des Adhérents</h1>
    <table class="table">
        <thead>
            <tr>
                <th>Nom Complet</th>
                <th>Détail</th>
                <th>Détail JSON</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="adherant" items="${adherants}">
            <tr>
                <td><c:out value="${adherant.prenom} ${adherant.nom}" /></td>
                <td>
                    <a href="adherent_detail?id=${adherant.idAdherent}" class="link-adherent">Détail</a>
                </td>
                <td>
                    <a href="adherent_detail_json?id=${adherant.idAdherent}" class="link-adherent">Détail JSON</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>