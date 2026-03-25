SELECT v.id_version, v.version_number, vt.designation
FROM software as s
         JOIN version v ON s.id_software = v.id_software
         JOIN version_type vt ON v.id_version_type = vt.id_version_type
WHERE s.id_software = 1;