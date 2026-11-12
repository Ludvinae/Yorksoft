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
VALUES ( '0684545611', 'barbara.dupont99@gmail.com', 'Barbara', 'Dupont', '{seed-client}'),
       ( '0744123313', 'bdupont@hotmail.com', 'Barbara', 'Dupont', '{seed-client}'),
       ( '', 'kevin@creditmutuel.fr', 'Kevin', 'Martin', '{seed-client}'),
       ('0355654525', 'jeanvaljean@yorksoft.fr', 'Jean', 'Valjean', '{seed-manager}'),
       ('0355657899', 'sandraschmidt@yorksoft.fr', 'Sandra', 'Schmidt', '{seed-technician}'),
       ('0355657711', 'thomasmuller@yorksoft.fr', 'Thomas', 'Muller', '{seed-technician}'),
       ('0355650001', 'patrickthierry@yorksoft.fr', 'Patrick', 'Thierry', '{seed-director}' ),
       ('', 'jeanclaude@creditmutuel.fr', 'Jean-Claude', 'Convenant', '{seed-client}'),
       ('', 'admin@yorksoft.fr', 'Super', 'Admin', '{seed-admin}'),
       ('0612457891', 'julien.morel@gmail.com', 'Julien', 'Morel', '{seed-client}'),
       ('0723881456', 'camille.leroy@gmail.com', 'Camille', 'Leroy', '{seed-client}'),
       ('0654219833', 'nicolas.bernard@gmail.com', 'Nicolas', 'Bernard', '{seed-client}'),
       ('0766124587', 'sophie.martin@gmail.com', 'Sophie', 'Martin', '{seed-client}'),
       ('0671392214', 'alexandre.robin@gmail.com', 'Alexandre', 'Robin', '{seed-client}'),
       ('0741589620', 'marie.garnier@gmail.com', 'Marie', 'Garnier', '{seed-client}'),
       ('0699114563', 'thomas.chevalier@gmail.com', 'Thomas', 'Chevalier', '{seed-client}'),
       ('0782341975', 'lea.fontaine@gmail.com', 'Léa', 'Fontaine', '{seed-client}'),
       ('0618735290', 'kevin.lambert@gmail.com', 'Kévin', 'Lambert', '{seed-client}'),
       ('0755441238', 'claire.perrin@gmail.com', 'Claire', 'Perrin', '{seed-client}'),
       ('0627886341', 'antoine.marchand@gmail.com', 'Antoine', 'Marchand', '{seed-client}'),
       ('0714297753', 'emma.rey@gmail.com', 'Emma', 'Rey', '{seed-client}'),
       ('0663912884', 'lucas.faure@gmail.com', 'Lucas', 'Faure', '{seed-client}'),
       ('0355653155', 'chloe.noel@yorksoft.fr', 'Chloé', 'Noël', '{seed-technician}'),
       ('0355659808', 'maxime.picard@yorksoft.fr', 'Maxime', 'Picard', '{seed-technician}'),
       ('0355655118', 'sarah.vionelli@yorksoft.fr', 'Sarah', 'Vionelli', '{seed-technician}'),
       ('0355651788', 'alice.pfund@yorksoft.fr', 'Alice', 'Pfund', '{seed-technician}'),
       ('', 'guest-technician@jury.fr', 'Test', 'Technicien', '{seed-technician}'),
       ('', 'guest-manager@jury.fr', 'Test', 'Manager', '{seed-manager}');

INSERT INTO admin (id_app_user)
VALUES (9);

INSERT INTO manager (id_app_user)
VALUES (4), (28);

INSERT INTO technician (id_app_user, rank)
VALUES (5, 1), (6, 3), (23, 1), (24, 2),
       (25, 1), (26, 2), (27, 1);

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

INSERT INTO knowledge(subject, description, resolution, id_theme)
VALUES(
          'Profil Outlook corrompu après changement de mot de passe',
          'Après un changement de mot de passe, Outlook conserve parfois des jetons d''authentification invalides.',
          'Supprimer puis recréer le profil Outlook.', 1),
      ('Boîte mail saturée',
       'L''utilisateur ne peut plus envoyer ou recevoir de messages lorsque son quota est atteint.',
       'Archiver ou supprimer des messages afin de libérer de l''espace.',
       1),
      ('Mot de passe expiré',
       'Le compte utilisateur a dépassé sa durée de validité.',
       'Réinitialiser le mot de passe et forcer son renouvellement.',
       2),
      ('Compte verrouillé après tentatives multiples',
       'Le compte a été verrouillé suite à plusieurs échecs d''authentification.',
       'Déverrouiller le compte depuis l''annuaire.',
       2),
      ('Impression bloquée dans la file d''attente',
       'Le spooler d''impression conserve des travaux corrompus.',
       'Vider la file d''attente puis redémarrer le service Spooler.',
       3),
      ('Erreur PDF sur version 4.2',
       'L''export PDF échoue à cause d''une bibliothèque défectueuse.',
       'Installer le correctif PDF fourni avec la version 4.2.1.',
       4),
      ('Connexion VPN impossible après mise à jour',
       'Le certificat local est devenu invalide après une mise à jour du client VPN.',
       'Réinstaller le certificat et redémarrer le poste.',
       5),
      ('Licence expirée',
       'La licence du logiciel est arrivée à expiration.',
       'Importer une nouvelle licence valide.',
       6),
      ('Service applicatif arrêté',
       'Le service principal du logiciel n''est plus démarré.',
       'Redémarrer le service et vérifier les journaux.',
       7),
      ('Erreur de connexion à la base de données',
       'Le logiciel ne peut plus joindre le serveur de base de données.',
       'Vérifier la connectivité réseau et les paramètres de connexion.',
       7);

-- Authors of the seeded knowledge articles (technicians 5 & 6, manager 4)
UPDATE knowledge SET id_author = 5 WHERE id_knowledge IN (1, 3, 5, 7, 9);
UPDATE knowledge SET id_author = 6 WHERE id_knowledge IN (2, 4, 6, 8);
UPDATE knowledge SET id_author = 4 WHERE id_knowledge = 10;

INSERT INTO ticket (title, open_date, call_duration, current_priority, initial_priority, close_date, modification_date,
                    description, id_client, id_urgency, id_impact, id_version, current_status, id_current_theme,
                    id_current_manager, id_current_technician, overdue, id_knowledge)
VALUES  ('Ca marche pas', '2026-04-11 09:12:00', 468, 0, 0, null, '2026-04-11 10:13:00',
        'Ca marche pas', 1, 1, 1, (5), 'WAITING_CLIENT', 7,
        4, 5, false, null),

       ('Indisponibilité du service de sauvegarde en ligne', NOW(), 1035, 70,
        70, null, NOW(),
        'Impossible d''acceder au dossiers sur le cloud dans le gestionnaire de projets.', 3, 2,
        2, 5, 'OPEN', 3, null, null,
        true, null),

        ('Pas de menu', '2026-03-11 14:51:08', 952, 37,
         37, '2026-03-11 16:16:42', '2026-03-11 16:16:42',
         'Impossible d''accéder au items du menu', 1, 2,
         1, 2, 'CLOSED', 1, 4, 6,
         true, null),

       ('Pas de menu apparent', '2026-05-05 08:23:08', 1352, 85,
        85, null, '2026-05-05 10:16:42',
        'Impossible d''acceder au items du menu', 3, 2,
        3, 4, 'ASSIGNED', 1, 4, 5,
        true, null),

        ('Erreur de connexion au logiciel', '2026-05-20 08:15:00', 671, 0, 0,
        NULL,'2026-05-20 08:15:00','Le client ne peut plus se connecter à l''application depuis ce matin.',
        10, 1,1,1, 'OPEN', 3,
         NULL, NULL, true, null),

        ('Application extrêmement lente','2026-05-20 09:42:00',1028,61,61,
        NULL,'2026-05-20 09:42:00','Des ralentissements importants sont constatés sur plusieurs postes.',
        11, 2,2,1,'OPEN',4,
         NULL,NULL,true, null),

        ('Crash lors de la génération PDF','2026-05-20 10:10:00',1438, 85, 85,
        NULL,'2026-05-20 10:10:00','Le logiciel plante systématiquement lors de l''export PDF.',
        12,2, 3,2,'OPEN',5,
         NULL, NULL,true, null),

        ('Impossible d''imprimer les rapports', '2026-05-20 11:25:00',782, 23,23,
        NULL,'2026-05-20 11:25:00', 'Les impressions restent bloquées dans la file d''attente.',
        13, 1, 2, 4, 'OPEN', 1,NULL,
        NULL, true, null),

        ('Erreur serveur 500', '2026-05-20 13:05:00', 4120,100,100,
        NULL, '2026-05-20 13:05:00','Une erreur 500 apparaît lors de l''accès au tableau de bord.',
        14,3, 4, 1, 'OPEN', 2,NULL,
        NULL,true, null),

        ('Synchronisation des données impossible', '2026-05-20 14:33:00',2280, 76,
        76,NULL,'2026-05-20 14:33:00','Les données ne remontent plus depuis l''API distante.',
        15, 3, 3, 5, 'OPEN',3, NULL,
        NULL, true, null),

        ('Mot de passe refusé','2026-05-21 08:20:00',467,0,0,NULL,
         '2026-05-21 08:20:00', 'Le client indique que son mot de passe n''est plus reconnu.',
         16, 1, 1, 5, 'OPEN',6,NULL,
         NULL,true, 3),

        ('Bug affichage mobile', '2026-05-21 09:50:00',1089, 46,46,
        NULL,'2026-05-21 09:50:00', 'Le menu principal disparaît sur smartphone.',
        17, 1, 2, 2,'OPEN', 5, NULL,
        NULL, true, null),

        ('Données incohérentes dans les statistiques', '2026-05-21 10:48:00', 1560,46,
        46, NULL, '2026-05-21 10:48:00', 'Les indicateurs affichent des valeurs incorrectes.',
        18, 2,3, 3, 'OPEN', 2,NULL,
        NULL, true, null),

        ('Perte de connexion base de données', '2026-05-21 11:37:00', 3120, 85,
        85,NULL,'2026-05-21 11:37:00','La connexion PostgreSQL tombe régulièrement.',
        19,3,4,3,'OPEN',3,NULL,
        NULL, true, null),

        ('Erreur lors de la création d''un ticket','2026-05-21 13:14:00',1260,38,
        38, NULL,'2026-05-21 13:14:00','Le formulaire retourne une erreur inattendue.',
         20, 2, 2, 2, 'OPEN', 2,NULL,
         NULL,false, null),

        ('Notifications email non reçues','2026-05-21 14:55:00',1020,61,
        61,NULL,'2026-05-21 14:55:00','Les utilisateurs ne reçoivent plus les notifications.',
        21, 2,2,1,'OPEN',2,NULL,
        NULL,true, null),

        ('Interface bloquée après connexion','2026-05-21 15:32:00',2460,76,
        76, NULL,'2026-05-21 15:32:00','L''application reste figée après authentification.',
        22,3,3,2,'OPEN',4, NULL,
        NULL,true, null);


INSERT INTO history (id_status, id_ticket, id_app_user, start_date, end_date)
VALUES (1, 1, 5, '2026-04-11 09:12:00', '2026-04-11 09:27:00'),
       (1, 2, 3, '2026-04-11 11:31:00', null),
       (2, 1, 4, '2026-04-11 09:27:00', '2026-04-11 10:08:00'),
       (3, 1, 5, '2026-04-11 10:08:00', '2026-04-11 10:13:00'),
       (4, 1, 5, '2026-04-11 10:13:00', null),
       (1, 3, 5, '2026-03-11 14:51:08', '2026-03-11 15:08:08'),
       (2, 3, 4, '2026-03-11 15:08:08', '2026-03-11 15:26:33'),
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
       ('2026-03-11 15:08:08', null, 3, 4, 6),
       ('2026-05-05 10:16:42', null,  4, 4, 5);




INSERT INTO knowledge_versions(id_knowledge, id_version)
VALUES  (1, 1), (1, 2), (2, 1),
        (2, 2), (3, 1), (3, 2),
        (4, 1), (5, 3), (6, 4),
        (7, 5), (8, 6), (9, 4),
        (10, 4);

INSERT INTO procedure (creation_date, modification_date, title, content, id_knowledge, id_technician)
VALUES ('2024-04-06 09:08:07', null, 'Sauvegarde',
        'Explication sur la résolution des problèmes de sauvegarde sur le Cloud', 1, 6);

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

INSERT INTO activity_type (designation, name, icon_address)
VALUES ('TICKET', 'Ticket', 'ph-ticket'),
       ('COMMENT', 'Commentaire', 'ph-chat'),
       ('PROCEDURE', 'Procédure', 'ph-article'),
       ('ASSIGNMENT', 'Attribution', 'ph-hand-deposit'),
       ('USER', 'Utilisateur', 'ph-user'),
       ('OTHER', 'Autre', 'ph-bookmark');

INSERT INTO activity (id_app_user, timestamp, description, id_activity_type)
VALUES (5, NOW(), 'A écrit un commentaire sur le ticket #1', 2),
       (6, '2024-04-06 09:08:07', 'A publié un procedure sur la connaissance #1', 3),
       (24, '2026-05-21 15:32:00', 'A ouvert le ticket #17', 1);

-- The hand-written tickets above are dated around 2026-05-21: shift them so that day becomes yesterday,
-- otherwise the demo ages and they drop out of the dashboards' periods. Rows already dated NOW() are left alone.
WITH s AS (SELECT date_trunc('day', localtimestamp) - TIMESTAMP '2026-05-22 00:00:00' AS shift)
UPDATE ticket SET open_date = open_date + s.shift, close_date = close_date + s.shift,
                  modification_date = modification_date + s.shift
FROM s WHERE open_date < TIMESTAMP '2026-06-01 00:00:00';

WITH s AS (SELECT date_trunc('day', localtimestamp) - TIMESTAMP '2026-05-22 00:00:00' AS shift)
UPDATE history SET start_date = start_date + s.shift, end_date = end_date + s.shift
FROM s WHERE id_ticket <> 2;

UPDATE history SET start_date = t.open_date
FROM ticket t WHERE t.id_ticket = 2 AND history.id_ticket = 2;

WITH s AS (SELECT date_trunc('day', localtimestamp) - TIMESTAMP '2026-05-22 00:00:00' AS shift)
UPDATE assignment SET assignment_date = assignment_date + s.shift, end_date = end_date + s.shift FROM s;

WITH s AS (SELECT date_trunc('day', localtimestamp) - TIMESTAMP '2026-05-22 00:00:00' AS shift)
UPDATE classification SET affectation_date = affectation_date + s.shift FROM s WHERE id_ticket <> 2;

UPDATE classification SET affectation_date = t.open_date
FROM ticket t WHERE t.id_ticket = 2 AND classification.id_ticket = 2;

WITH s AS (SELECT date_trunc('day', localtimestamp) - TIMESTAMP '2026-05-22 00:00:00' AS shift)
UPDATE activity SET timestamp = timestamp + s.shift
FROM s WHERE timestamp BETWEEN TIMESTAMP '2026-01-01 00:00:00' AND TIMESTAMP '2026-06-01 00:00:00';

-- SLA fields of the hand-written tickets, as TicketMetricsService would store them: active time spent in
-- OPEN/ASSIGNED/IN_PROGRESS, deadline from the last refresh (now for an open ticket, the solve for a closed one,
-- since the refresh is skipped once the close date is set).
UPDATE ticket t
SET active_time_in_seconds = m.active,
    sla_deadline = m.ref_time + make_interval(secs => m.sla - m.active),
    overdue = m.active > m.sla
FROM (SELECT tk.id_ticket,
             CAST(SUM(CASE WHEN s.code IN ('OPEN', 'ASSIGNED', 'IN_PROGRESS')
                           THEN EXTRACT(EPOCH FROM COALESCE(h.end_date, localtimestamp) - h.start_date)
                           ELSE 0 END) AS bigint) AS active,
             CASE WHEN tk.close_date IS NULL THEN localtimestamp
                  ELSE MAX(h.start_date) FILTER (WHERE s.code = 'SOLVED') END AS ref_time,
             CASE WHEN tk.current_priority <= 25 THEN 72 * 3600
                  WHEN tk.current_priority <= 50 THEN 24 * 3600
                  WHEN tk.current_priority <= 75 THEN 8 * 3600
                  ELSE 2 * 3600 END AS sla
      FROM ticket tk
      JOIN history h ON h.id_ticket = tk.id_ticket
      JOIN status s ON s.id_status = h.id_status
      GROUP BY tk.id_ticket, tk.close_date, tk.current_priority) m
WHERE t.id_ticket = m.id_ticket;

-- Generates p_count past tickets spread over the last p_days days (more of them recently, on working days),
-- each assigned to one of p_technicians and taken through the workflow as far as its timeline reaches now:
-- OPEN -> ASSIGNED -> IN_PROGRESS [-> WAITING_* -> IN_PROGRESS] -> SOLVED -> CLOSED. Writes the ticket, its
-- history, assignment, classification, comments, and the technician's activity, the way the workflow services
-- would. The active time (OPEN, ASSIGNED, IN_PROGRESS) meets the SLA for about 82% of the tickets, and about
-- 1 in 3 goes through a long wait (3 to 25 days, not counted in the SLA) so that each technician keeps some
-- active tickets. p_opener is the user who opens every ticket (a trial account, so the purge deletes them),
-- null lets the technician or the client open it.
-- Deterministic: the same p_salt gives the same tickets (relative to now). Returns the number of tickets.
-- Also called by DemoTicketGenerator for each new trial account: keep its signature in sync.
-- Kept as one statement (no semicolon inside the body) because Spring splits the seed script on semicolons.
CREATE OR REPLACE FUNCTION demo_generate_tickets(p_technicians integer[], p_count integer, p_days integer,
                                                 p_salt text, p_opener integer)
RETURNS integer
LANGUAGE sql
AS $$
WITH ref AS (
    SELECT localtimestamp AS now_ts,
           (SELECT array_agg(id_app_user ORDER BY id_app_user) FROM client) AS clients,
           (SELECT array_agg(id_app_user ORDER BY id_app_user) FROM manager) AS managers,
           (SELECT array_agg(id_version ORDER BY id_version) FROM version) AS versions,
           (SELECT array_agg(id_urgency ORDER BY priority_factor) FROM urgency) AS urgencies,
           (SELECT array_agg(id_impact ORDER BY priority_factor) FROM impact) AS impacts,
           (SELECT array_agg(id_theme ORDER BY id_theme) FROM theme) AS themes
),
draw AS (
    SELECT n, k, ((hashtext(p_salt || ':' || n || ':' || k) & 2147483647) % 100000) / 100000.0 AS u
    FROM generate_series(1, p_count) AS n,
         unnest(ARRAY['day', 'hour', 'client', 'version', 'urgency', 'impact', 'theme', 'text', 'manager',
                      'technician', 'opener', 'call', 'sla', 'budget', 'wait', 'wait_kind', 'wait_len',
                      'knowledge', 'stall']) AS k
),
u AS (
    SELECT n,
           MAX(u) FILTER (WHERE k = 'day') AS day, MAX(u) FILTER (WHERE k = 'hour') AS hour,
           MAX(u) FILTER (WHERE k = 'client') AS client, MAX(u) FILTER (WHERE k = 'version') AS version,
           MAX(u) FILTER (WHERE k = 'urgency') AS urgency, MAX(u) FILTER (WHERE k = 'impact') AS impact,
           MAX(u) FILTER (WHERE k = 'theme') AS theme, MAX(u) FILTER (WHERE k = 'text') AS text,
           MAX(u) FILTER (WHERE k = 'manager') AS manager, MAX(u) FILTER (WHERE k = 'technician') AS technician,
           MAX(u) FILTER (WHERE k = 'opener') AS opener, MAX(u) FILTER (WHERE k = 'call') AS call,
           MAX(u) FILTER (WHERE k = 'sla') AS sla, MAX(u) FILTER (WHERE k = 'budget') AS budget,
           MAX(u) FILTER (WHERE k = 'wait') AS wait, MAX(u) FILTER (WHERE k = 'wait_kind') AS wait_kind,
           MAX(u) FILTER (WHERE k = 'wait_len') AS wait_len, MAX(u) FILTER (WHERE k = 'knowledge') AS knowledge,
           MAX(u) FILTER (WHERE k = 'stall') AS stall
    FROM draw GROUP BY n
),
picked AS (
    SELECT u.*,
           r.now_ts,
           date_trunc('day', r.now_ts) - floor(p_days * power(u.day, 1.6)) * interval '1 day' AS day0,
           r.clients[1 + floor(u.client * cardinality(r.clients))::int] AS id_client,
           r.versions[1 + floor(u.version * cardinality(r.versions))::int] AS id_version,
           r.urgencies[CASE WHEN u.urgency < 0.5 THEN 1 WHEN u.urgency < 0.85 THEN 2 ELSE 3 END] AS id_urgency,
           r.impacts[CASE WHEN u.impact < 0.45 THEN 1 WHEN u.impact < 0.75 THEN 2
                          WHEN u.impact < 0.93 THEN 3 ELSE 4 END] AS id_impact,
           r.themes[1 + floor(u.theme * cardinality(r.themes))::int] AS id_theme,
           r.managers[CASE WHEN u.manager < 0.8 THEN 1 ELSE cardinality(r.managers) END] AS id_manager,
           p_technicians[1 + floor(u.technician * cardinality(p_technicians))::int] AS id_technician
    FROM u CROSS JOIN ref r
),
scored AS (
    SELECT p.*,
           LEAST(p.day0 - CASE EXTRACT(ISODOW FROM p.day0) WHEN 6 THEN 1 WHEN 7 THEN 2 ELSE 0 END * interval '1 day'
                     + interval '8 hours' + p.hour * interval '9 hours 30 minutes',
                 p.now_ts - interval '5 minutes' - p.hour * interval '3 hours') AS t0,
           COALESCE(p_opener, CASE WHEN p.opener < 0.6 THEN p.id_technician ELSE p.id_client END) AS id_opener,
           floor(LEAST(GREATEST(im.priority_factor + c.importance, 0), 3) / 3.0 * 70
                 + LEAST(GREATEST(ur.priority_factor - vt.urgency_malus, 0), 2) / 2.0 * 30)::int AS priority,
           th.code AS theme_code,
           p.stall < 0.35 OR p.wait < 0.25 AS has_wait,
           CASE WHEN p.stall < 0.35 THEN 259200 + p.wait_len * 1900800
                WHEN p.wait < 0.25 THEN 1800 + p.wait_len * 172800
                ELSE 0 END AS wait_seconds,
           CASE WHEN p.wait_kind < 0.7 THEN 'WAITING_CLIENT' ELSE 'WAITING_THIRD_PARTY' END AS wait_code
    FROM picked p
    JOIN impact im ON im.id_impact = p.id_impact
    JOIN urgency ur ON ur.id_urgency = p.id_urgency
    JOIN client c ON c.id_app_user = p.id_client
    JOIN version v ON v.id_version = p.id_version
    JOIN version_type vt ON vt.id_version_type = v.id_version_type
    JOIN theme th ON th.id_theme = p.id_theme
),
budgeted AS (
    SELECT s.*,
           sla.seconds AS sla_seconds,
           sla.seconds * CASE WHEN s.sla < 0.82 THEN 0.25 + 0.7 * s.budget ELSE 1.08 + 1.2 * s.budget END AS budget_seconds
    FROM scored s
    CROSS JOIN LATERAL (SELECT CASE WHEN s.priority <= 25 THEN 72 * 3600 WHEN s.priority <= 50 THEN 24 * 3600
                                    WHEN s.priority <= 75 THEN 8 * 3600 ELSE 2 * 3600 END AS seconds) sla
),
timed AS (
    SELECT b.*,
           b.t0 + make_interval(secs => b.budget_seconds * 0.12) AS t1,
           b.t0 + make_interval(secs => b.budget_seconds * 0.25) AS t2,
           b.t0 + make_interval(secs => b.budget_seconds * CASE WHEN b.has_wait THEN 0.55 ELSE 1 END) AS t3,
           b.t0 + make_interval(secs => b.budget_seconds * CASE WHEN b.has_wait THEN 0.55 ELSE 1 END
                                        + b.wait_seconds) AS t4,
           b.t0 + make_interval(secs => b.budget_seconds + b.wait_seconds) AS t5,
           b.t0 + make_interval(secs => b.budget_seconds + b.wait_seconds
                                        + GREATEST(b.budget_seconds * 0.25, 300)) AS t6
    FROM budgeted b
),
numbered AS (
    SELECT t.*, nextval(pg_get_serial_sequence('ticket', 'id_ticket'))::integer AS id_ticket
    FROM (SELECT * FROM timed ORDER BY t0) t
),
texts (theme_code, variant, title, description, solution) AS (
    VALUES ('HANDLING_ERROR', 0, 'Fichier supprimé par erreur',
            'Le client a supprimé un fichier de travail partagé et souhaite le récupérer.',
            'Fichier restauré depuis la corbeille du serveur, procédure de récupération expliquée au client.'),
           ('HANDLING_ERROR', 1, 'Saisie validée par erreur',
            'Une saisie a été validée avec de mauvaises valeurs et n''est plus modifiable.',
            'Écriture annulée depuis le menu d''administration puis ressaisie avec le client.'),
           ('HANDLING_ERROR', 2, 'Mauvaise période exportée',
            'Le client a exporté la mauvaise période et a écrasé son fichier précédent.',
            'Export relancé sur la bonne période, ancien fichier récupéré depuis l''historique des versions.'),
           ('SYSTEM_ERROR', 0, 'Erreur au démarrage de l''application',
            'L''application se ferme dès l''ouverture avec un message d''erreur système.',
            'Cache local corrompu supprimé, l''application démarre normalement.'),
           ('SYSTEM_ERROR', 1, 'Plantage à l''enregistrement',
            'Le logiciel se ferme sans message au moment d''enregistrer un document.',
            'Droits d''écriture du dossier de travail rétablis.'),
           ('SYSTEM_ERROR', 2, 'Service arrêté après mise à jour',
            'Le service ne redémarre plus depuis l''installation de la dernière mise à jour.',
            'Dépendance manquante réinstallée, service redémarré.'),
           ('NETWORK_ISSUE', 0, 'Connexion au serveur perdue',
            'Les postes perdent régulièrement la connexion au serveur de l''application.',
            'Délai d''expiration du proxy augmenté, connexion stable depuis.'),
           ('NETWORK_ISSUE', 1, 'Synchronisation hors ligne impossible',
            'Les données saisies hors ligne ne remontent plus vers le serveur.',
            'Pare-feu du client configuré pour autoriser le port de synchronisation.'),
           ('NETWORK_ISSUE', 2, 'Accès distant refusé',
            'Le client ne parvient plus à se connecter depuis l''extérieur de son entreprise.',
            'Adresse IP publique du client ajoutée à la liste autorisée.'),
           ('RESPONSE_TIME', 0, 'Lenteurs à l''ouverture des dossiers',
            'L''ouverture d''un dossier prend plus d''une minute sur tous les postes.',
            'Index de la base reconstruits, temps d''ouverture revenu à la normale.'),
           ('RESPONSE_TIME', 1, 'Recherche très lente',
            'La recherche de fiches met plusieurs minutes à afficher les résultats.',
            'Filtre par défaut corrigé pour ne plus charger tout l''historique.'),
           ('RESPONSE_TIME', 2, 'Rapports mensuels interminables',
            'La génération des rapports mensuels n''aboutit plus avant la fin de la journée.',
            'Génération planifiée la nuit, rapports disponibles le matin.'),
           ('BUG', 0, 'Montants arrondis incorrectement',
            'Les totaux affichés diffèrent de quelques centimes de la somme des lignes.',
            'Correctif d''arrondi installé.'),
           ('BUG', 1, 'Bouton de validation inactif',
            'Le bouton Valider reste grisé alors que le formulaire est complet.',
            'Contournement donné en attendant le correctif : valider avec la touche Entrée.'),
           ('BUG', 2, 'Dates décalées d''un jour dans les exports',
            'Les dates saisies apparaissent avec un jour de décalage dans les exports.',
            'Fuseau horaire du poste corrigé, correctif prévu dans la prochaine version.'),
           ('CONFIGURATION_ERROR', 0, 'Imprimante par défaut introuvable',
            'Les impressions partent vers une imprimante qui n''existe plus.',
            'Imprimante par défaut reconfigurée dans les paramètres du logiciel.'),
           ('CONFIGURATION_ERROR', 1, 'Mauvais taux de TVA appliqué',
            'Les factures utilisent un ancien taux de TVA.',
            'Table des taux mise à jour dans le paramétrage.'),
           ('CONFIGURATION_ERROR', 2, 'Licence non reconnue sur un nouveau poste',
            'Le logiciel indique que la licence n''est pas valide sur un poste récemment installé.',
            'Licence réaffectée au nouveau poste depuis le portail client.'),
           ('OTHER_ISSUE', 0, 'Demande de formation',
            'Le client souhaite une démonstration des nouvelles fonctionnalités.',
            'Session de prise en main réalisée à distance avec le client.'),
           ('OTHER_ISSUE', 1, 'Question sur la facturation',
            'Le client ne comprend pas une ligne de sa dernière facture de maintenance.',
            'Détail de la ligne expliqué, demande transmise au service commercial.'),
           ('OTHER_ISSUE', 2, 'Création de comptes utilisateurs',
            'Le client souhaite ajouter trois nouveaux utilisateurs.',
            'Comptes créés et identifiants transmis au client.')
),
phases AS (
    SELECT n.id_ticket, ph.seq, ph.code, ph.start_date,
           CASE WHEN ph.end_date <= n.now_ts THEN ph.end_date END AS end_date,
           ph.id_actor, ph.reason
    FROM numbered n
    CROSS JOIN LATERAL (
        VALUES (1, 'OPEN', n.t0, n.t1, n.id_opener, CAST(NULL AS text)),
               (2, 'ASSIGNED', n.t1, n.t2, n.id_manager, NULL),
               (3, 'IN_PROGRESS', n.t2, n.t3, n.id_technician, NULL),
               (4, n.wait_code, n.t3, n.t4, n.id_technician,
                CASE WHEN n.wait_code = 'WAITING_CLIENT' THEN 'En attente de précisions du client'
                     ELSE 'En attente d''un correctif de l''éditeur' END),
               (5, 'IN_PROGRESS', n.t4, n.t5, n.id_technician, NULL),
               (6, 'SOLVED', n.t5, n.t6, n.id_technician, NULL),
               (7, 'CLOSED', n.t6, NULL, n.id_technician, 'Solution validée par le client')
    ) AS ph (seq, code, start_date, end_date, id_actor, reason)
    WHERE ph.start_date <= n.now_ts
      AND (n.has_wait OR ph.seq NOT IN (4, 5))
),
ticket_rows AS (
    SELECT n.*,
           x.title, x.description, x.solution,
           cur.code AS current_status, cur.start_date AS modification_date,
           active.seconds AS active_seconds
    FROM numbered n
    JOIN texts x ON x.theme_code = n.theme_code AND x.variant = floor(n.text * 3)::int
    CROSS JOIN LATERAL (SELECT code, start_date FROM phases ph
                        WHERE ph.id_ticket = n.id_ticket ORDER BY seq DESC LIMIT 1) cur
    CROSS JOIN LATERAL (
        SELECT CAST(GREATEST(0, EXTRACT(EPOCH FROM LEAST(n.t1, n.now_ts) - n.t0))
                    + GREATEST(0, EXTRACT(EPOCH FROM LEAST(n.t2, n.now_ts) - n.t1))
                    + GREATEST(0, EXTRACT(EPOCH FROM LEAST(n.t3, n.now_ts) - n.t2))
                    + CASE WHEN n.has_wait THEN GREATEST(0, EXTRACT(EPOCH FROM LEAST(n.t5, n.now_ts) - n.t4)) ELSE 0 END
               AS bigint) AS seconds
    ) active
),
new_tickets AS (
    INSERT INTO ticket (id_ticket, title, open_date, call_duration, current_priority, initial_priority, close_date,
                        modification_date, description, solution, id_client, id_urgency, id_impact, id_version,
                        current_status, id_current_theme, id_current_manager, id_current_technician,
                        active_time_in_seconds, sla_deadline, overdue, id_knowledge)
    SELECT r.id_ticket, r.title, r.t0,
           CASE WHEN r.id_opener <> r.id_client THEN 120 + floor(r.call * 1500)::int END,
           r.priority, r.priority,
           CASE WHEN r.t6 <= r.now_ts THEN r.t6 END,
           r.modification_date, r.description,
           CASE WHEN r.t5 <= r.now_ts THEN r.solution END,
           r.id_client, r.id_urgency, r.id_impact, r.id_version,
           r.current_status, r.id_theme,
           CASE WHEN r.t1 <= r.now_ts THEN r.id_manager END,
           CASE WHEN r.t1 <= r.now_ts THEN r.id_technician END,
           r.active_seconds,
           CASE WHEN r.t6 <= r.now_ts THEN r.t5 ELSE r.now_ts END + make_interval(secs => r.sla_seconds - r.active_seconds),
           r.active_seconds > r.sla_seconds,
           CASE WHEN r.t5 <= r.now_ts AND r.knowledge < 0.3
                THEN (SELECT MIN(k.id_knowledge) FROM knowledge k WHERE k.id_theme = r.id_theme) END
    FROM ticket_rows r
    RETURNING id_ticket
),
new_history AS (
    INSERT INTO history (id_ticket, id_status, id_app_user, start_date, end_date, status_reason)
    SELECT ph.id_ticket, s.id_status, ph.id_actor, ph.start_date, ph.end_date, ph.reason
    FROM phases ph JOIN status s ON s.code = ph.code
),
new_assignments AS (
    INSERT INTO assignment (id_ticket, id_manager, id_technician, assignment_date, end_date)
    SELECT id_ticket, id_manager, id_technician, t1, NULL FROM numbered WHERE t1 <= now_ts
),
new_classifications AS (
    INSERT INTO classification (id_ticket, id_theme, affectation_date)
    SELECT id_ticket, id_theme, t0 FROM numbered
),
comments AS (
    SELECT id_ticket, t3 AS date_sent, id_technician AS id_author,
           CASE WHEN wait_code = 'WAITING_CLIENT'
                THEN 'Bonjour, pourriez-vous nous envoyer une capture d''écran du message affiché ?'
                ELSE 'Nous avons transmis le problème à l''éditeur et revenons vers vous dès réception du correctif.'
           END AS content
    FROM numbered WHERE has_wait AND t3 <= now_ts
    UNION ALL
    SELECT id_ticket, t4 - interval '10 minutes', id_client, 'Voici la capture demandée, merci.'
    FROM numbered WHERE has_wait AND wait_code = 'WAITING_CLIENT' AND t4 <= now_ts
),
new_comments AS (
    INSERT INTO comment (id_ticket, id_app_user, date_sent, content)
    SELECT id_ticket, id_author, date_sent, content FROM comments
),
new_activities AS (
    INSERT INTO activity (id_app_user, timestamp, description, id_activity_type)
    SELECT a.id_app_user, a.timestamp, a.description, at.id_activity_type
    FROM (
        SELECT ph.id_actor AS id_app_user, ph.start_date AS timestamp,
               CASE ph.seq WHEN 1 THEN 'A ouvert le ticket #' || ph.id_ticket
                           WHEN 3 THEN 'A pris en charge le ticket #' || ph.id_ticket
                           WHEN 4 THEN 'A mis le ticket #' || ph.id_ticket || ' en attente d''un '
                                       || CASE WHEN ph.code = 'WAITING_CLIENT' THEN 'client' ELSE 'tiers' END
                           WHEN 5 THEN 'A repris en charge le ticket #' || ph.id_ticket
                           WHEN 6 THEN 'A proposé une solution pour le ticket #' || ph.id_ticket
                           ELSE 'A clos le ticket #' || ph.id_ticket END AS description,
               'TICKET' AS designation
        FROM phases ph
        WHERE ph.seq <> 2 AND ph.id_actor = ANY (p_technicians)
        UNION ALL
        SELECT id_technician, t1, 'A reçu l''attribution du ticket #' || id_ticket, 'ASSIGNMENT'
        FROM numbered WHERE t1 <= now_ts
        UNION ALL
        SELECT id_author, date_sent, 'A commenté le ticket #' || id_ticket, 'COMMENT'
        FROM comments WHERE id_author = ANY (p_technicians)
    ) a
    JOIN activity_type at ON at.designation = a.designation
)
SELECT CAST(COUNT(*) AS integer) FROM new_tickets
$$;

-- Six months of history for the seeded technicians (about 35 tickets each)
SELECT demo_generate_tickets((SELECT array_agg(id_app_user ORDER BY id_app_user) FROM technician),
                             245, 182, 'seed', NULL);

-- The managers' side of those assignments (the function only logs the technicians' activity)
INSERT INTO activity (id_app_user, timestamp, description, id_activity_type)
SELECT a.id_manager, a.assignment_date,
       'A attribué le ticket #' || a.id_ticket || ' à ' || u.first_name || ' ' || u.last_name,
       (SELECT id_activity_type FROM activity_type WHERE designation = 'ASSIGNMENT')
FROM assignment a
JOIN app_user u ON u.id_app_user = a.id_technician
WHERE a.id_ticket > 17;






