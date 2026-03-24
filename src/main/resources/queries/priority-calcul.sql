SELECT t.description, t.initial_priority, t.final_priority,
       i.priority_factor, u.priority_factor, c.importance, th.priority_factor,
       (i.priority_factor * u.priority_factor * c.importance * th.priority_factor) as sum
FROM ticket AS t
LEFT JOIN public.impact i on t.id_impact = i.id_impact
LEFT JOIN public.urgency u on t.id_urgency = u.id_urgency
LEFT JOIN public.client c on t.id_client = c.id_client
JOIN classification cl on t.id_ticket = cl.id_ticket
JOIN theme th on cl.id_theme = th.id_theme;