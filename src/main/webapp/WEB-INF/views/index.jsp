<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bibliotheque - Accueil</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(120deg, #f8fafc 0%, #e0e7ff 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .main-wrapper {
            display: flex;
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
            overflow: hidden;
            max-width: 900px;
            width: 95vw;
            min-height: 480px;
        }
        .left-img {
            flex: 1.2;
            background: url('https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=600&q=80') center center/cover no-repeat;
            min-width: 280px;
            min-height: 100%;
        }
        .container {
            flex: 1.5;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            padding: 60px 40px;
            background: rgba(255,255,255,0.97);
        }
        h1 {
            font-size: 2.7rem;
            margin-bottom: 30px;
            color: #f0ab08;
            font-weight: 700;
            letter-spacing: 1px;
        }
        .nav-links {
            display: flex;
            gap: 25px;
        }
        .nav-links a {
            display: inline-block;
            background: linear-gradient(90deg, #92530f 0%, #937648 100%);
            color: #fff;
            text-decoration: none;
            padding: 14px 32px;
            border-radius: 8px;
            font-size: 1.15rem;
            font-weight: 600;
            box-shadow: 0 4px 12px rgba(102,126,234,0.08);
            transition: background 0.3s, transform 0.2s, box-shadow 0.3s;
            position: relative;
        }
        .nav-links a:hover {
            background: linear-gradient(90deg, #5a6cd1 0%, #667eea 100%);
            transform: translateY(-2px) scale(1.04);
            box-shadow: 0 8px 24px rgba(90,108,209,0.15);
        }
        @media (max-width: 900px) {
            .main-wrapper {
                flex-direction: column;
                min-height: 600px;
            }
            .left-img {
                min-height: 220px;
                width: 100%;
            }
            .container {
                align-items: center;
                padding: 30px 15px;
            }
        }
        @media (max-width: 600px) {
            .main-wrapper {
                flex-direction: column;
                min-height: 400px;
            }
            .left-img {
                min-height: 120px;
            }
            .container {
                align-items: center;
                padding: 18px 5px;
            }
            h1 {
                font-size: 1.5rem;
            }
            .nav-links {
                flex-direction: column;
                gap: 12px;
            }
            .nav-links a {
                padding: 10px 18px;
                font-size: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="main-wrapper">
        <div class="left-img"></div>
        <div class="container">
            <h1>BIENVENUE A LA SITE DE BIBLIOTHEQUE</h1>
            <div class="nav-links">
                <a href="admin">Admin</a>
                <a href="adherent">Adherant</a>
            </div>
        </div>
    </div>
</body>
</html>