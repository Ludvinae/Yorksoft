SELECT t.description, th.designation
FROM theme as th
         JOIN ticket_themes tt ON th.id_theme = tt.id_theme
         JOIN ticket t ON tt.id_ticket = t.id_ticket
WHERE t.id_ticket = 1;