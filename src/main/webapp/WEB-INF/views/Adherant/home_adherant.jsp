<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil Adhérent</title>
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
        .menu-list {
            list-style: none;
            padding: 0;
            margin: 0 0 30px 0;
            width: 100%;
            max-width: 400px;
        }
        .menu-list li {
            margin-bottom: 12px;
        }
        .menu-link {
            display: block;
            background: #fffbe9;
            color: #a67c52;
            text-decoration: none;
            padding: 16px 28px;
            border-radius: 8px;
            font-size: 1.15rem;
            font-weight: 600;
            box-shadow: 0 2px 8px rgba(166,124,82,0.06);
            transition: background 0.2s, color 0.2s, transform 0.2s;
        }
        .menu-link:hover, .menu-link.active {
            background: #a67c52;
            color: #fffbe9;
            transform: scale(1.03);
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
            <h2>Bienvenue sur le site Bibliotheque des adherants</h2>
            <ul class="menu-list">
                <li><a href="#" class="menu-link">Voir les livres</a></li>
                <li><a href="liste_pret" class="menu-link">Mes prets</a></li>
                <li><a href="render_insertPret" class="menu-link">Faire un pret</a></li>
                <li><a href="liste_reservations" class="menu-link">Mes reservations</a></li>
                <li><a href="faire_reservation" class="menu-link">Faire une reservation</a></li>
                <li><a href="#" class="menu-link">Mon profil</a></li>
                <li><a href="#" class="menu-link">Déconnexion</a></li>
            </ul>
        </div>
    </div>
    <!-- Bootstrap JS Bundle (with Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>