SELECT t.description, c.email, s.designation, e.first_name, e.last_name, r.designation, r.rank, h.start_date, h.end_date
FROM ticket AS t
JOIN history h on t.id_ticket = h.id_ticket
JOIN status s ON h.id_status = s.id_status
JOIN client c ON t.id_client = c.id_client
JOIN employee e ON h.id_employee = e.id_employee
JOIN role r ON e.id_role = r.id_role;