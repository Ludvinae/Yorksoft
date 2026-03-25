SELECT s.name, s.id_software
FROM client AS c
         JOIN license l ON c.id_client = l.id_client
         JOIN software s ON l.id_software = s.id_software
WHERE c.id_client = 1;