INSERT INTO software_type (designation)
VALUES ('Tableur'),
       ('Location'),
       ('Gestion de paie');

INSERT INTO software (name, description, id_software_type)
VALUES ('Pexel', 'Le meilleur tableur du marché !', 1),
       ('Wise', 'Manager de paie accessible et performant', 3);

INSERT INTO version_type (designation, urgency_malus)
VALUES ('Release', 0),
       ('Release Candidate', 0),
       ('Beta', 1),
       ('Snapshot', 1);

INSERT INTO version (version_number, publication_date, id_version_type, id_software)
VALUES ('1.0', '2011-11-11 14:30:00', 1, 1),
       ('2.0', '2025-02-19 11:10:00', 2, 1),
       ('0.6.3', '2026-01-01 22:00:00', 3, 2);

INSERT INTO app_user (phone_number, email, first_name, last_name, password)
VALUES ( '06 84 54 56 11', 'barbara.dupont99@gmail.com', 'Barbara', 'Dupont', 'WeshAlors99'),
       ( '07 44 12 33 13', 'bdupont@hotmail.com', 'Barbara', 'Dupont', 'TangoNocturne'),
       ( '', 'kevin@creditmutuel.fr', 'Kevin', 'Martin', 'kékédu57'),
       ('03 55 65 45 25', 'jeanvaljean@yorksoft.fr', 'Jean', 'Valjean', 'kGz4579c!AF5'),
       ('03 55 65 78 99', 'sandraschmidt@yorksoft.fr', 'Sandra', 'Schmidt', 'sqqZ7A!784mK'),
       ('03 55 65 77 11', 'damienmuller@yorksoft.fr', 'Damien', 'Muller', '78!dsQAE75Vcv'),
       ('03 55 65 00 01', 'patrickthierry', 'Patrick', 'Thierry', 'kjiZ!51sd3Z' ),
       ('', 'jeanclaude@creditmutuel.fr', 'Jean-Claude', 'Convenant', 'fjJH45pM2');

INSERT INTO manager (id_app_user)
VALUES (4);

INSERT INTO technician (id_app_user, rank)
VALUES (5, 1), (6, 3);

INSERT INTO director (id_app_user)
VALUES (7);

INSERT INTO client (id_app_user, importance)
VALUES (1, 0), (2, 0), (3, 2), (8, 0);

INSERT INTO status (display_order, designation, code)
VALUES (1, 'Nouveau', 'OPEN'),
       (2, 'Attribué', 'ASSIGNED'),
       (3, 'En cours', 'IN_PROGRESS'),
       (4, 'En attente', 'WAITING_CLIENT'),
       (5, 'Résolu', 'SOLVED'),
       (6, 'Clos', 'CLOSED');


INSERT INTO urgency (priority_factor, designation, description)
VALUES (1, 'Basse', 'Solution de contournement disponible'),
       (2, 'Haute', 'Pas de solution de contournement');

INSERT INTO impact (priority_factor, designation, description)
VALUES (1, 'Bas', 'Une seule personne concernée'),
       (2, 'Moyen', 'Une équipe'),
       (3, 'Elevé', 'Un département'),
       (4, 'Critique', 'Toute l''entreprise');

INSERT INTO theme (designation, code, description)
VALUES ('Erreur de manipulation', 'HANDLING_ERROR', ''),
       ( 'Erreur système', 'SYSTEM_ERROR', ''),
       ( 'Problème réseau', 'NETWORK_ISSUE', ''),
       ( 'Temps de réponse', 'RESPONSE_TIME', ''),
       ( 'Bug', 'BUG', ''),
       ( 'Erreur de paramétrage', 'CONFIGURATION_ERROR', ''),
       ('Autres cas', 'OTHER_ISSUE', '');

INSERT INTO ticket (title, open_date, call_duration, current_priority, initial_priority, close_date, modification_date,
                    description, id_client, id_urgency, id_impact, id_version, status)
VALUES ('Ca marche pas', NOW(), null, 'VERY_LOW', 'LOW', null, NOW(), 'Ca marche pas', 1, 1, 1, 2, 'WAITING_CLIENT'),
       ('Indisponibilité du service de sauvegarde en ligne', NOW(), 1035, 'MEDIUM', 'MEDIUM', null, NOW(), 'Impossible d''acceder au dossiers sur le cloud dans le gestionnaire de projets.', 3, 2, 2, 3, 'OPEN');

INSERT INTO history (id_status, id_ticket, id_app_user, start_date, end_date)
VALUES (1, 1, 1, '2026-04-11 09:12:00', '2026-04-11 09:27:00'),
       (1, 2, 3, '2026-04-11 11:31:00', null),
       (2, 1, 1, '2026-04-11 09:27:00', '2026-04-11 10:13:00'),
       (4, 1, 3, '2026-04-11 10:13:00', null);

INSERT INTO comment (date_sent, last_modification, content, id_ticket, id_app_user)
VALUES (NOW(), null, 'Bonjour, pourriez vous préciser la nature de votre problème ?', 1, 5);

INSERT INTO assignment (assignment_date, end_date, id_ticket, id_manager, id_technician)
VALUES (NOW(), null, 1, 4, 5),
       ('2026-03-03 13:24', '2026-03-03 16:21',  2, 4, 5);


INSERT INTO knowledge (subject, id_theme)
VALUES ('Service Cloud indisponible', 4);

INSERT INTO article (creation_date, modification_date, content, id_knowledge, id_technician)
VALUES ('2024-04-06', null, 'Explication sur la résolution des problemes de sauvegarde sur le Cloud', 1, 6);

INSERT INTO license (id_software, expiration_date, license_number)
VALUES ( 1, '2027-12-02', 'azerty123456'),
       ( 2, '2028-03-03', 'plopplop8511'),
       ( 2,  '2030-10-10', 'plopplop1234'),
       ( 2,  '2027-12-02', 'ploplop2345');


INSERT INTO classification (id_theme, id_ticket, affectation_date)
VALUES (6, 1, '2026-04-11 09:12:00'),
       (3, 2, now()),
       (8, 1, now());


INSERT INTO knowledge_versions (id_version, id_knowledge)
VALUES (3, 1);






