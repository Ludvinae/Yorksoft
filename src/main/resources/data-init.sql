INSERT INTO software_type (designation)
VALUES ('Tableur'),
       ('Location'),
       ('Gestion de paie');

INSERT INTO software (name, description, id_software_type)
VALUES ('Pexel', 'Le meilleur tableur du marché !', 1),
       ('Wise', 'Manager de paie accessible et performant', 3);

INSERT INTO version_type (designation)
VALUES ('Release'),
       ('Release Candidate'),
       ('Beta'),
       ('Snapshot');

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
       ('03 55 65 00 01', 'patrickthierry', 'Patrick', 'Thierry', 'kjiZ!51sd3Z' );

INSERT INTO manager (id_app_user)
VALUES (4);

INSERT INTO technician (id_app_user, rank)
VALUES (5, 1), (6, 3);

INSERT INTO director (id_app_user)
VALUES (7);

INSERT INTO client (id_app_user, importance)
VALUES (1, 1), (2, 1), (3, 5);

INSERT INTO status (display_order, designation)
VALUES (1, 'Nouveau'),
       (2, 'Attribué'),
       (3, 'En cours'),
       (4, 'En attente'),
       (5, 'Résolu'),
       (6, 'Clos');


INSERT INTO urgency (priority_factor, designation, description)
VALUES (1, 'Basse', 'Inconvenience d''utilisation'),
       (2, 'Moyenne', 'Capacité d''utilisation restreinte'),
       (3, 'Elevée', 'Arrêt de production'),
       (4, 'Critique', 'Faille de sécurité');

INSERT INTO impact (priority_factor, designation, description)
VALUES (1, 'Bas', 'Une seule personne concernée'),
       (2, 'Moyen', 'Une équipe'),
       (3, 'Elevé', 'Un département'),
       (4, 'Critique', 'Toute l''entreprise');

INSERT INTO theme (priority_factor, designation, description)
VALUES (1, 'Erreur de manipulation', ''),
       (3, 'Erreur système', ''),
       (4, 'Problème réseau', ''),
       (2, 'Temps de réponse', ''),
       (3, 'Dysfonctionnement', ''),
       (2, 'Bug', ''),
       (1, 'Erreur de paramétrage', ''),
       (1, 'Autres cas', '');

INSERT INTO ticket (open_date, call_duration, final_priority, initial_priority, close_date, modification_date,
                    description, id_client, id_urgency, id_impact, id_version)
VALUES (NOW(), null, 1, 2, null, null, 'Ca marche pas', 1, 1, 1, 2),
       (NOW(), 1035, null, 80, null, null, 'Indisponibilité du service de sauvegarde en ligne', 3, 2, 2, 3);

INSERT INTO history (id_status, id_ticket, id_app_user)
VALUES (1, 1, 1),
       (1, 2, 3),
       (2, 1, 1),
       (4, 1, 3);

INSERT INTO comment (date_sent, last_modification, content, id_ticket, id_app_user)
VALUES (NOW(), null, 'Bonjour, pourriez vous préciser la nature de votre problème ?', 1, 5);

INSERT INTO assignment (assigment_date, end_date, id_ticket, id_manager, id_technician)
VALUES (NOW(), null, 1, 4, 5);


INSERT INTO knowledge (subject, id_theme)
VALUES ('Service Cloud indisponible', 4);

INSERT INTO article (creation_date, modification_date, content, id_knowledge, id_technician)
VALUES ('2024-04-06', null, 'Explication sur la résolution des problemes de sauvegarde sur le Cloud', 1, 6);

INSERT INTO license (id_software, user_count, expiration_date, license_number)
VALUES ( 1, 1, '2027-12-02', 'azerty123456'),
       ( 2, 1, '2028-03-03', 'plopplop8511'),
       ( 2, 100, '2030-10-10', 'plopplop1234'),
       ( 2, 1, '2027-12-02', 'ploplop2345');

INSERT INTO license_clients (id_license, id_client)
VALUES (1, 1), (2, 2), (3, 3), (4, 1);

INSERT INTO classification (id_theme, id_ticket, affectation_date)
VALUES (6, 1, now()),
       (3, 2, now()),
       (8, 1, now());


INSERT INTO knowledge_versions (id_version, id_knowledge)
VALUES (3, 1);






