-- Prod seed: no demo data, just a single admin account so the app isn't locked out
-- of itself on first boot. Password is set via DemoPasswordSeeder (see APP_SEED_DEMO_ADMIN_PASSWORD).
-- Guarded so it's safe to run on every startup (SQL_INIT_MODE=always) without
-- clashing with the unique email constraint once the account already exists.
INSERT INTO app_user (email, first_name, last_name, password)
SELECT 'admin@yorksoft.fr', 'Admin', 'Admin', '{seed-admin}'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'admin@yorksoft.fr');

INSERT INTO admin (id_app_user)
SELECT id_app_user FROM app_user
WHERE email = 'admin@yorksoft.fr'
  AND id_app_user NOT IN (SELECT id_app_user FROM admin);
