<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion Adherent</title>
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
            max-width: 800px;
            width: 95vw;
            min-height: 400px;
        }
        .left-img {
            flex: 1.2;
            background:  url('https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=600&q=80') center center/cover no-repeat;
            min-width: 220px;
            min-height: 100%;
        }
        .container {
            flex: 1.5;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            padding: 50px 40px;
            background: rgba(255,255,255,0.97);
            min-width: 260px;
        }
        h2 {
            font-size: 2.2rem;
            margin-bottom: 28px;
            color: #e4ab0f;
            font-weight: 700;
            letter-spacing: 1px;
        }
        .form-group {
            margin-bottom: 22px;
            width: 100%;
        }
        label {
            display: block;
            font-size: 1.05rem;
            margin-bottom: 8px;
            color: #3b3663;
            font-weight: 500;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #cfd8dc;
            border-radius: 7px;
            font-size: 1.08rem;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 7px rgba(102, 126, 234, 0.18);
        }
        button {
            background: linear-gradient(90deg, #92530f 0%, #937648 100%);
            color: #fff;
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
            background: linear-gradient(90deg, #92530f 0%, #937648 100%);
            transform: translateY(-2px) scale(1.03);
            box-shadow: 0 8px 24px rgba(90,108,209,0.13);
        }
        @media (max-width: 900px) {
            .main-wrapper {
                flex-direction: column;
                min-height: 500px;
            }
            .left-img {
                min-height: 180px;
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
                min-height: 350px;
            }
            .left-img {
                min-height: 90px;
            }
            .container {
                align-items: center;
                padding: 15px 5px;
            }
            h2 {
                font-size: 1.2rem;
            }
            .form-group label {
                font-size: 0.95rem;
            }
            input[type="text"],
            input[type="password"],
            button {
                font-size: 0.95rem;
                padding: 8px;
            }
        }
    </style>
</head>
<body>
    <div class="main-wrapper">
        <div class="left-img"></div>
        <div class="container">
            <h2>Connexion Adherant</h2>
            <form action="adherent_login" method="post" style="width:100%;">
                <div class="form-group">
                    <label for="nom">Nom :</label>
                    <input type="text" id="nom" name="nom" required>
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe :</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Se connecter</button>
            </form>
        </div>
    </div>
</body>
</html>