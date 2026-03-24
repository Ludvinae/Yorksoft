SELECT t.description, sta.designation, th.designation,
       t.initial_priority, i.designation, u.designation, t.final_priority,
       cc.designation, t.call_duration, c.first_name, c.last_name, c.importance,
       s.name, st.designation, v.version_number, vt.designation

FROM ticket AS t
JOIN public.client c on c.id_client = t.id_client
LEFT JOIN public.impact i on i.id_impact = t.id_impact
LEFT JOIN public.urgency u on t.id_urgency = u.id_urgency
JOIN public.communication_canal cc on cc.id_canal = t.id_communication_canal
JOIN public.version v on v.id_version = t.id_version
JOIN version_type vt on v.id_version_type = vt.id_version_type
JOIN software s on v.id_software = s.id_software
JOIN software_type st on s.id_software_type = st.id_software_type
JOIN history h ON t.id_ticket = h.id_ticket
JOIN status sta ON h.id_status = sta.id_status
JOIN classification cl ON t.id_ticket = cl.id_ticket
JOIN theme th ON cl.id_theme = th.id_theme;
