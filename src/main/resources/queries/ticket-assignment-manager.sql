SELECT * FROM ticket AS t
LEFT JOIN assignment AS a ON t.id_ticket = a.id_ticket
LEFT JOIN employee AS e ON a.id_manager = e.id_employee
