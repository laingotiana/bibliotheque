-- Désactiver temporairement les vérifications des clés étrangères pour éviter les erreurs
SET FOREIGN_KEY_CHECKS = 0;

-- Vider les tables (dans l'ordre inverse des dépendances)
TRUNCATE TABLE prolongement;
TRUNCATE TABLE reservation;
TRUNCATE TABLE pret;
TRUNCATE TABLE penalite;
TRUNCATE TABLE abonnement;
TRUNCATE TABLE categorie_livre;
TRUNCATE TABLE exemplaire;
TRUNCATE TABLE livre;
TRUNCATE TABLE adherant;
TRUNCATE TABLE admin;
TRUNCATE TABLE type_pret;
TRUNCATE TABLE status;
TRUNCATE TABLE profil;
TRUNCATE TABLE auteur;
TRUNCATE TABLE categorie;
TRUNCATE TABLE jour_ferie;

-- Réactiver les vérifications des clés étrangères
SET FOREIGN_KEY_CHECKS = 1;



-- Insertion dans la table categorie
INSERT INTO categorie (id_categorie, nom_categorie) VALUES
(1, 'Roman'),
(2, 'Science-Fiction'),
(3, 'Histoire'),
(4, 'Biographie');
-- Littérature classique


-- Insertion dans la table auteur
INSERT INTO auteur (id_auteur, prenom_auteur_, nom_auteur) VALUES
(1, 'Victor', 'Hugo'),
(2, 'Isaac', 'Asimov'),
(3, 'J.K.', 'Rowling'),
(4, 'Michelle', 'Obama');

-- Insertion dans la table profil
INSERT INTO profil (id_profil, type_adherant, quota_pret, quota_reservation) VALUES
(1, 'Etudiant', 3, 2),
(2, 'Professeur', 5, 3),
(3, 'Anonyme', 1, 1),
(4, 'Professionnel', 10, 5);

-- Insertion dans la table status
INSERT INTO status (id_status, nom_status) VALUES
(1, 'En attente'),
(2, 'Confirme'),
(3, 'Annulee'),
(4, 'Termine');

-- Insertion dans la table type_pret (avec données supplémentaires)
INSERT INTO type_pret (id_type, type) VALUES
(1, 'Sur place'),
(2, 'Long terme'),
(3, 'Court terme'), -- Nouvelle entrée
(4, 'Réservé');     -- Nouvelle entrée

-- Insertion dans la table admin
INSERT INTO admin (id, nom, mdp) VALUES
(1, 'admin1', '123');

-- Insertion dans la table livre
INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, id_auteur) VALUES
(1, 'Les Miserables', '978-0140444308', 'Français', 1862, 'Epopee sur la justice', 1232, 1),
(2, 'Fondation', '978-0553293357', 'Anglais', 1951, 'Saga de science-fiction', 320, 2),
(3, 'Harry Potter a l ecole des sorciers', '978-2070643028', 'Français', 1997, 'Aventure d un jeune sorcier', 300, 3),
(4, 'Devenir', '978-1524763138', 'Français', 2018, 'Memoires de Michelle Obama', 448, 4);

-- Insertion dans la table exemplaire
INSERT INTO exemplaire (id_exemplaire, disponible, id_livre) VALUES
(1, 10, 1),
(2, 20, 1),
(3, 21, 2),
(4, 21, 3),
(5, 60, 4);

-- Insertion dans la table adherant
INSERT INTO adherant (id_adherent, nom, prenom, password, id_profil) VALUES
(1, 'Dupont', 'Jean', 'jean123', 1),
(2, 'Martin', 'Sophie', 'sophie456', 2),
(3, 'Durand', 'Pierre', 'pierre789', 3);

-- Insertion dans la table pret
INSERT INTO pret (id_pret, date_debut, date_fin, id_type, id_adherent, id_exemplaire,rendu) VALUES
(1, '2025-06-01', '2025-06-15', 1, 1, 2,'true');
(2, '2025-06-05', '2025-07-05', 2, 2, 5),
(3, '2025-06-10', '2025-06-24', 1, 3, 4);

-- Insertion dans la table reservation
INSERT INTO reservation (id_reservation, date_reservation, date_debut_pret, date_fin_pret, id_status, id_adherent) VALUES
(1, '2025-06-01', '2025-06-15', '2025-06-29', 1, 1),
(2, '2025-06-02', '2025-06-20', '2025-07-04', 2, 2),
(3, '2025-06-03', NULL, NULL, 3, 3);

-- Insertion dans la table prolongement
INSERT INTO prolongement (id_prolongement, date_prolongement, id_status, id_adherent) VALUES
(1, '2025-06-15', 2, 1),
(2, '2025-06-20', 1, 2);

-- Insertion dans la table penalite
INSERT INTO penalite (id_penalite, debut_penalite, fin_penalite, id_adherent) VALUES
(1, '2025-06-16', '2025-06-30', 1),
(2, '2025-06-20', '2025-07-04', 3);

-- Insertion dans la table categorie_livre
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 4);


INSERT INTO abonnement (id_abonnement, dateDebut, dateFin, id_adherent) VALUES
(1, '2025-01-01', '2025-07-20', 1),
(2, '2025-03-15', '2026-07-14', 2),
(3, '2025-06-01', '2025-11-30', 3);











INSERT INTO categorie (id_categorie, nom_categorie) VALUES
(1, 'Littérature classique'),
(2, 'Philosophie'),
(3, 'Jeunesse / Fantastique');



INSERT INTO auteur (id_auteur, prenom_auteur_, nom_auteur) VALUES
(1, 'Victor', 'Hugo'),
(2, 'Albert', 'Camus'),
(3, 'J.K.', 'Rowling');


INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, id_auteur) VALUES
(1, 'Les Miserables', '9782070409189', 'Français', 1862, 'Epopee sur la justice', 1232, 1),
(2, 'L Étranger', '9782070360022', 'Français', 1951, 'Saga de science-fiction', 320, 2),
(3, 'Harry Potter a l ecole des sorciers', '9782070643026', 'Français', 1997, 'Aventure d un jeune sorcier', 300, 3);

INSERT INTO categorie_livre (id_livre, id_categorie) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO exemplaire (id_exemplaire, disponible, id_livre) VALUES
(1, 1, 1),
(2, 1, 1),
(3, 1, 1),
(4, 1, 2),
(5, 1, 2),
(6, 1, 3);

INSERT INTO profil (id_profil, type_adherant, quota_pret, quota_reservation,jour_pret,jour_penalite,quota_prolongement) VALUES
(1, 'Etudiant', 2, 1,7,10,3),
(2, 'Enseignant', 3, 2,9,9,5),
(3, 'Professionnel', 4, 3,12,8,7);

INSERT INTO adherant (id_adherent, nom, prenom, password, id_profil) VALUES
(1, 'ETU001', 'Amine Bensaid', 'amine', 1),
(2, 'ETU002', 'Sarah El Khattabi', 'sarah', 1),
(3, 'ETU003', 'Youssef Moujahid', 'youssef', 1),
(4, 'ENS001', 'Nadia Benali', 'nadia', 2),
(5, 'ENS002', 'Karim Haddadi', 'karim', 2),
(6, 'ENS003', 'Salima Touhami', 'salima', 2),
(7, 'PROF001', 'Rachid El Mansouri', 'rachid', 3),
(8, 'PROF001', 'Amina Zerouali', 'amina', 3);

INSERT INTO abonnement (id_abonnement, dateDebut, dateFin, id_adherent) VALUES
(1, '2025-02-01', '2026-07-24', 1),
(2, '2025-02-01', '2025-07-01', 2),
(3, '2025-04-01', '2025-12-10', 3),
(4, '2025-07-01', '2026-07-01', 4),
(5, '2025-08-01', '2026-05-01', 5),
(6, '2025-07-01', '2026-06-01', 6),
(7, '2025-06-01', '2025-12-01', 7),
(8, '2024-10-01', '2025-06-01', 8);

-- Insertion dans la table type_pret (avec données supplémentaires)
INSERT INTO type_pret (id_type, type) VALUES
(1, 'Sur place'),
(2, 'Long terme'),
(3, 'Court terme'), -- Nouvelle entrée
(4, 'Réservé');

-- INSERT INTO penalite (id_penalite, debut_penalite, fin_penalite, id_adherent) VALUES
-- (1, '2025-06-16', '2025-06-30', 1),
-- (2, '2025-06-20', '2025-07-04', 3);

INSERT INTO status (id_status, nom_status) VALUES
(1, 'En attente'),
(2, 'Confirme'),
(3, 'Annulee'),
(4, 'Termine');

INSERT INTO admin (id, nom, mdp) VALUES
(1, 'admin1', '123');

INSERT INTO jour_ferie (date) VALUES
('2025-07-26'), -- Nouvel An
('2025-07-19');

