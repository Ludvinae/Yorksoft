INSERT INTO software_type (designation)
VALUES ('Tableur'),
       ('Location'),
       ('Gestion de paie'),
       ('Géolocalisation');

INSERT INTO software (name, description, id_software_type)
VALUES ('Pexel', 'Le meilleur tableur du marché !', 1),
       ('Wise', 'Manager de paie accessible et performant', 3),
       ('FastApp', 'Application mobile de géolocalisation', 4),
       ('EasyRent', 'Système complet de location de matériel', 2);

INSERT INTO version_type (designation, urgency_malus, code)
VALUES ('Release', 0, 'r'),
       ('Release Candidate', 0, 'rc'),
       ('Beta', 1, 'b'),
       ('Snapshot', 1, 's'),
       ('Alpha', 2, 'a');

INSERT INTO version (version_number, publication_date, id_version_type, id_software)
VALUES ('1.0.0', '2011-11-11 14:30:00', 1, 1),
       ('2.0.0', '2025-02-19 11:10:00', 2, 1),
       ('0.6.3', '2026-01-01 22:00:00', 3, 2),
       ('1.0.0', '2026-04-17 11:15:12', 1, 3),
       ('1.0.0', '2026-05-21 08:00:00', 1, 4),
       ('0.1.1', '2025-10-15 16:25:58', 2, 3),
       ('0.5.6', '2026-01-29 15:12:00', 4, 3),
       ('1.0.0', '2026-05-01 15:00:00', 2, 4);

INSERT INTO app_user (phone_number, email, first_name, last_name, password)
VALUES ( '06 84 54 56 11', 'barbara.dupont99@gmail.com', 'Barbara', 'Dupont', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ( '07 44 12 33 13', 'bdupont@hotmail.com', 'Barbara', 'Dupont', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ( '', 'kevin@creditmutuel.fr', 'Kevin', 'Martin', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 45 25', 'jeanvaljean@yorksoft.fr', 'Jean', 'Valjean', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 78 99', 'sandraschmidt@yorksoft.fr', 'Sandra', 'Schmidt', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 77 11', 'damienmuller@yorksoft.fr', 'Damien', 'Muller', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 00 01', 'patrickthierry@yorksoft.fr', 'Patrick', 'Thierry', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u' ),
       ('', 'jeanclaude@creditmutuel.fr', 'Jean-Claude', 'Convenant', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('', 'admin@yorksoft.fr', 'Super', 'Admin', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 12 45 78 91', 'julien.morel@gmail.com', 'Julien', 'Morel', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('07 23 88 14 56', 'camille.leroy@gmail.com', 'Camille', 'Leroy', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 54 21 98 33', 'nicolas.bernard@gmail.com', 'Nicolas', 'Bernard', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('07 66 12 45 87', 'sophie.martin@gmail.com', 'Sophie', 'Martin', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 71 39 22 14', 'alexandre.robin@gmail.com', 'Alexandre', 'Robin', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('07 41 58 96 20', 'marie.garnier@gmail.com', 'Marie', 'Garnier', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 99 11 45 63', 'thomas.chevalier@gmail.com', 'Thomas', 'Chevalier', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('07 82 34 19 75', 'lea.fontaine@gmail.com', 'Léa', 'Fontaine', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 18 73 52 90', 'kevin.lambert@gmail.com', 'Kévin', 'Lambert', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('07 55 44 12 38', 'claire.perrin@gmail.com', 'Claire', 'Perrin', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 27 88 63 41', 'antoine.marchand@gmail.com', 'Antoine', 'Marchand', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('07 14 29 77 53', 'emma.rey@gmail.com', 'Emma', 'Rey', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('06 63 91 28 84', 'lucas.faure@gmail.com', 'Lucas', 'Faure', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 31 55', 'chloe.noel@yorksoft.fr', 'Chloé', 'Noël', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 98 08', 'maxime.picard@yorksoft.fr', 'Maxime', 'Picard', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 51 18', 'sarah.vionelli@yorksoft.fr', 'Sarah', 'Vionelli', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u'),
       ('03 55 65 17 88', 'alice.pfund@yorksoft.fr', 'Alice', 'Pfund', '$2a$10$V98VMxgV7myKHPE7eu7n3.GO8EUiqZGK/3ISjSriOFklF2Uvftx8u');

INSERT INTO admin (id_app_user)
VALUES (9);

INSERT INTO manager (id_app_user)
VALUES (4);

INSERT INTO technician (id_app_user, rank)
VALUES (5, 1), (6, 3), (23, 1), (24, 2),
       (25, 1), (26, 2);

INSERT INTO director (id_app_user)
VALUES (7);

INSERT INTO client (id_app_user, importance)
VALUES (1, 0), (2, 0), (3, 2),
       (8, 0), (10, 0), (11, 1),
       (12, 2), (13, 0), (14, 0),
       (15, 0), (16, 0), (17, 1),
       (18, 0), (19, 0), (20, 0),
       (21, 1), (22, 0);

INSERT INTO status (display_order, designation, code)
VALUES (1, 'Nouveau', 'OPEN'),
       (2, 'Attribué', 'ASSIGNED'),
       (3, 'En cours', 'IN_PROGRESS'),
       (4, 'En attente client', 'WAITING_CLIENT'),
       (6, 'Résolu', 'SOLVED'),
       (7, 'Clos', 'CLOSED'),
       (5, 'En attente tiers', 'WAITING_THIRD_PARTY'),
       (8, 'Rejeté', 'REJECTED');


INSERT INTO urgency (priority_factor, designation, description)
VALUES (0, 'Basse', 'Solution de contournement disponible'),
       (1, 'Haute', 'Pas de solution de contournement'),
       (2, 'Critique', 'Faille de sécurité');

INSERT INTO impact (priority_factor, designation, description)
VALUES (0, 'Bas', 'Une seule personne concernée'),
       (1, 'Moyen', 'Une équipe'),
       (2, 'Elevé', 'Un département'),
       (3, 'Critique', 'Toute l''entreprise');

INSERT INTO theme (designation, code, description)
VALUES ('Erreur de manipulation', 'HANDLING_ERROR', ''),
       ( 'Erreur système', 'SYSTEM_ERROR', ''),
       ( 'Problème réseau', 'NETWORK_ISSUE', ''),
       ( 'Temps de réponse', 'RESPONSE_TIME', ''),
       ( 'Bug', 'BUG', ''),
       ( 'Erreur de paramétrage', 'CONFIGURATION_ERROR', ''),
       ('Autres cas', 'OTHER_ISSUE', '');

INSERT INTO ticket (title, open_date, call_duration, current_priority, initial_priority, close_date, modification_date,
                    description, id_client, id_urgency, id_impact, id_version, current_status, current_theme,
                    id_current_manager, id_current_technician, overdue)
VALUES  ('Ca marche pas', '2026-04-11 09:12:00', 468, 0, 0, null, '2026-04-11 10:13:00',
        'Ca marche pas', 1, 1, 1, 2, 'WAITING_CLIENT', 'OTHER_ISSUE',
        4, 5, false),

       ('Indisponibilité du service de sauvegarde en ligne', NOW(), 1035, 70,
        70, null, NOW(),
        'Impossible d''acceder au dossiers sur le cloud dans le gestionnaire de projets.', 3, 2,
        2, 3, 'OPEN', 'NETWORK_ISSUE', null, null, true),

        ('Pas de menu', '2026-03-11 14:51:08', 952, 37,
         37, '2026-03-11 16:16:42', '2026-03-11 16:16:42',
         'Impossible d''acceder au items du menu', 1, 2,
         1, 2, 'CLOSED', 'HANDLING_ERROR', 4, 6, true),

       ('Pas de menu apparant', '2026-05-05 08:23:08', 1352, 85,
        85, null, '2026-05-05 10:16:42',
        'Impossible d''acceder au items du menu', 3, 2,
        3, 2, 'ASSIGNED', 'HANDLING_ERROR', 4, 5, true),

        ('Erreur de connexion au logiciel', '2026-05-20 08:15:00', 671, 0, 0,
        NULL,'2026-05-20 08:15:00','Le client ne peut plus se connecter à l''application depuis ce matin.',
        10, 1,1,1, 'OPEN', 'NETWORK_ISSUE',
         NULL, NULL, true),

        ('Application extrêmement lente','2026-05-20 09:42:00',1028,61,61,
        NULL,'2026-05-20 09:42:00','Des ralentissements importants sont constatés sur plusieurs postes.',
        11, 2,2,1,'OPEN','RESPONSE_TIME',
         NULL,NULL,true),

        ('Crash lors de la génération PDF','2026-05-20 10:10:00',1438, 85, 85,
        NULL,'2026-05-20 10:10:00','Le logiciel plante systématiquement lors de l''export PDF.',
        12,2, 3,2,'OPEN','BUG',
         NULL, NULL,true),

        ('Impossible d''imprimer les rapports', '2026-05-20 11:25:00',782, 23,23,
        NULL,'2026-05-20 11:25:00', 'Les impressions restent bloquées dans la file d''attente.',
        13, 1, 2, 1, 'OPEN', 'HANDLING_ERROR',NULL,
        NULL, true),

        ('Erreur serveur 500', '2026-05-20 13:05:00', 4120,100,100,
        NULL, '2026-05-20 13:05:00','Une erreur 500 apparaît lors de l''accès au tableau de bord.',
        14,3, 4, 1, 'OPEN', 'SYSTEM_ERROR',NULL,
        NULL,true),

        ('Synchronisation des données impossible', '2026-05-20 14:33:00',2280, 76,
        76,NULL,'2026-05-20 14:33:00','Les données ne remontent plus depuis l''API distante.',
        15, 3, 3, 2, 'OPEN','NETWORK_ISSUE', NULL,
        NULL, true),

        ('Mot de passe refusé','2026-05-21 08:20:00',467,0,0,NULL,
         '2026-05-21 08:20:00', 'Le client indique que son mot de passe n''est plus reconnu.',
         16, 1, 1, 1, 'OPEN','CONFIGURATION_ERROR',NULL,
         NULL,true),

        ('Bug affichage mobile', '2026-05-21 09:50:00',1089, 46,46,
        NULL,'2026-05-21 09:50:00', 'Le menu principal disparaît sur smartphone.',
        17, 1, 2, 2,'OPEN', 'BUG', NULL,
        NULL, true),

        ('Données incohérentes dans les statistiques', '2026-05-21 10:48:00', 1560,46,
        46, NULL, '2026-05-21 10:48:00', 'Les indicateurs affichent des valeurs incorrectes.',
        18, 2,3, 3, 'OPEN', 'SYSTEM_ERROR',NULL,
        NULL, true),

        ('Perte de connexion base de données', '2026-05-21 11:37:00', 3120, 85,
        85,NULL,'2026-05-21 11:37:00','La connexion PostgreSQL tombe régulièrement.',
        19,3,4,3,'OPEN','NETWORK_ISSUE',NULL,
        NULL, true),

        ('Erreur lors de la création d''un ticket','2026-05-21 13:14:00',1260,38,
        38, NULL,'2026-05-21 13:14:00','Le formulaire retourne une erreur inattendue.',
         20, 2, 2, 2, 'OPEN', 'SYSTEM_ERROR',NULL,
         NULL,false),

        ('Notifications email non reçues','2026-05-21 14:55:00',1020,61,
        61,NULL,'2026-05-21 14:55:00','Les utilisateurs ne reçoivent plus les notifications.',
        21, 2,2,1,'OPEN','SYSTEM_ERROR',NULL,
        NULL,true),

        ('Interface bloquée après connexion','2026-05-21 15:32:00',2460,76,
        76, NULL,'2026-05-21 15:32:00','L''application reste figée après authentification.',
        22,3,3,2,'OPEN','RESPONSE_TIME', NULL,
        NULL,true);


INSERT INTO history (id_status, id_ticket, id_app_user, start_date, end_date)
VALUES (1, 1, 5, '2026-04-11 09:12:00', '2026-04-11 09:27:00'),
       (1, 2, 3, '2026-04-11 11:31:00', null),
       (2, 1, 4, '2026-04-11 09:27:00', '2026-04-11 10:08:00'),
       (3, 1, 5, '2026-04-11 10:08:00', '2026-04-11 10:13:00'),
       (4, 1, 5, '2026-04-11 10:13:00', null),
       (1, 3, 5, '2026-03-11 14:51:08', '2026-03-11 15:08:08'),
       (1, 3, 4, '2026-03-11 15:08:08', '2026-03-11 15:26:33'),
       (3, 3, 6, '2026-03-11 15:26:33', '2026-03-11 15:45:33'),
       (5, 3, 6, '2026-03-11 15:45:33', '2026-03-11 16:16:42'),
       (6, 3, 1, '2026-03-11 16:16:42', null),
       (1, 4, 5, '2026-05-05 08:23:08', '2026-05-05 10:16:42'),
       (2, 4, 4, '2026-05-05 10:16:42', null),
       (1, 5, 5,  '2026-05-20 08:15:00', NULL),
       (1, 6, 23, '2026-05-20 09:42:00', NULL),
       (1, 7, 24, '2026-05-20 10:10:00', NULL),
       (1, 8, 25, '2026-05-20 11:25:00', NULL),
       (1, 9, 26, '2026-05-20 13:05:00', NULL),
       (1, 10, 5, '2026-05-20 14:33:00', NULL),
       (1, 11, 23, '2026-05-21 08:20:00', NULL),
       (1, 12, 24, '2026-05-21 09:50:00', NULL),
       (1, 13, 25, '2026-05-21 10:48:00', NULL),
       (1, 14, 26, '2026-05-21 11:37:00', NULL),
       (1, 15, 5,  '2026-05-21 13:14:00', NULL),
       (1, 16, 23, '2026-05-21 14:55:00', NULL),
       (1, 17, 24, '2026-05-21 15:32:00', NULL);

INSERT INTO comment (date_sent, last_modification, content, id_ticket, id_app_user)
VALUES (NOW(), null, 'Bonjour, pourriez vous préciser la nature de votre problème ?', 1, 5);

INSERT INTO assignment (assignment_date, end_date, id_ticket, id_manager, id_technician)
VALUES ('2026-04-11 09:27:00', null, 1, 4, 5),
       ('2026-03-03 13:24', '2026-03-03 16:21',  2, 4, 5);


INSERT INTO knowledge (subject, id_theme)
VALUES ('Service Cloud indisponible', 4);

INSERT INTO article (creation_date, modification_date, content, id_knowledge, id_technician)
VALUES ('2024-04-06', null, 'Explication sur la résolution des problemes de sauvegarde sur le Cloud', 1, 6);

INSERT INTO license (id_software, expiration_date, license_number, id_app_user)
VALUES ( 1, '2027-12-02', 'azerty123456', 3),
       ( 2, '2028-03-03', 'plopplop8511', 1),
       ( 2,  '2030-10-10', 'plopplop1234', 2),
       ( 2,  '2027-12-02', 'ploplop2345', 8),
       (1, '2028-12-31', 'A7F9K2M4P8Q1X5Z3T6YB', 10),
       (2, '2027-09-15', 'L8D3W9R2N6C4V7B1H5JK', 11),
       (3, '2029-03-22', 'Q1Z5X8C2V6B9N4M7P3RT', 12),
       (4, '2026-11-30', 'T7Y2U8I4O1P6A3S9D5FG', 13),
       (1, '2025-02-14', 'M4N8B2V7C1X5W9L3K6HJ', 14),
       (2, '2028-07-01', 'R3T7Y1U5I9O2P6A4S8DF', 15),
       (3, '2027-05-18', 'H5J9K2L6M1N4B8V3C7XZ', 16),
       (4, '2030-01-10', 'P8Q2W6E9R4T1Y5U3I7OP', 17),
       (1, '2024-10-01', 'Z6X1C5V9B3N7M2L8K4JH', 18),
       (2, '2028-04-27', 'F2G6H9J3K7L1M5N8B4VC', 19),
       (3, '2029-08-19', 'D4S8F1G5H9J2K6L3M7NB', 20),
       (4, '2027-12-05', 'C7V2B6N9M4Q1W5E8R3TY', 21),
       (1, '2031-06-30', 'X5Z9A2S6D1F4G8H3J7KL', 22);


INSERT INTO classification (id_theme, id_ticket, affectation_date)
VALUES (5, 1, '2026-04-11 09:12:00'),
       (3, 2, '2026-04-11 11:31:00'),
       (7, 1, '2026-04-11 10:13:00'),
       (1, 3, '2026-03-11 14:51:08'),
       (1, 4, '2026-05-05 08:23:08'),
       (3, 5, '2026-05-20 08:15:00'),
       (4, 6, '2026-05-20 09:42:00'),
       (5, 7, '2026-05-20 10:10:00'),
       (1, 8, '2026-05-20 11:25:00'),
       (2, 9, '2026-05-20 13:05:00'),
       (3, 10, '2026-05-20 14:33:00'),
       (6, 11, '2026-05-21 08:20:00'),
       (5, 12, '2026-05-21 09:50:00'),
       (2, 13, '2026-05-21 10:48:00'),
       (3, 14, '2026-05-21 11:37:00'),
       (2, 15, '2026-05-21 13:14:00'),
       (2, 16, '2026-05-21 14:55:00'),
       (4, 17, '2026-05-21 15:32:00');


INSERT INTO knowledge_versions (id_version, id_knowledge)
VALUES (3, 1);






