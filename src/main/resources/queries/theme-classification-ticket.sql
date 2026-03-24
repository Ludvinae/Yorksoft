SELECT t.id_ticket, t.description, c.affectation_date, th.designation, th.priority_factor FROM theme as th
JOIN classification c ON th.id_theme = c.id_theme
JOIN ticket t ON c.id_ticket = t.id_ticket;