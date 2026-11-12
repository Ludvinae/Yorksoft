# Server setup — showcase deployment

How to set up the VPS and the domain for the public showcase deployment of Yorksoft, and what is left before going live.

The showcase runs on fictional seeded data. Visitors only use the temporary trial technician accounts (rank 0) created from the login page; the seeded role accounts are never published.

## Overview

| Piece | Where |
|---|---|
| Domain | `benoitchamplon.fr`, registered at OVH (DNS zone managed there) |
| App URL | `https://yorksoft.benoitchamplon.fr` |
| Root domain | `benoitchamplon.fr` / `www`: static portfolio on GitHub Pages (repo `Ludvinae/Portfolio`), not on this server |
| Email | OVH mailbox (15 GB, included with the domain), e.g. `contact@benoitchamplon.fr` |
| Server | netcup VPS Lite (2 vCPU / 4 GB RAM, IPv4 + IPv6), Debian 13 (trixie). IP and SSH user: `secret/server.md` (not committed) |
| Stack | `backend/compose.yml`, copied to `/opt/suivi_mns/` by the CI `deploy` job |

Request path:

```
Internet ──► Caddy (80/443, Let's Encrypt certificates)
               └─► front: nginx (Angular app, proxies /api/)
                     └─► app: Spring Boot (8080, not published)
                           └─► postgres (5432, not published)
```

Only Caddy publishes ports. The Caddyfile is inlined in `backend/compose.yml` (`configs.caddyfile`), so the deploy job only has to copy that one file. Hosting another site on this VPS later means adding a block there (not needed for the portfolio, which goes on the OVH web hosting).

## 1. DNS (OVH)

Do this first: propagation can take from a few minutes to a few hours.

In the OVH control panel → *Domain names* → `benoitchamplon.fr` → *DNS zone*:

- Add an **A** record: subdomain `yorksoft`, target = the VPS IPv4.
- Leave out the **AAAA** (IPv6) record for now. Add it once the site works and you have checked it answers over IPv6 (`curl -6 -I https://yorksoft.benoitchamplon.fr`).

Check propagation:

```bash
nslookup yorksoft.benoitchamplon.fr 8.8.8.8
```

Leave the **MX** records and the SPF TXT alone (the mailbox depends on them), and don't touch the root domain and `www` records: they point to GitHub Pages (4 A + 4 AAAA, `www` CNAME to `ludvinae.github.io`, plus the `_github-pages-challenge-ludvinae` TXT that verifies the domain). Only add the `yorksoft` record. If the zone has a wildcard `*` record, the explicit `yorksoft` record still takes precedence.

Email: create the mailbox included with the domain (OVH → *Emails*), e.g. `contact@benoitchamplon.fr`. It can send and receive (OVH webmail, or IMAP/SMTP in any mail client). The legal notice page uses this address as the contact for GDPR requests. No mail server is installed on the VPS.

## 2. VPS — first login and hardening

netcup sends the initial root credentials by email (also available in the Server Control Panel). From your machine:

```bash
ssh root@<VPS_IP>
```

### 2.1 Update the system and create your user

```bash
apt update && apt full-upgrade -y
apt install -y sudo           # missing from the netcup Debian 13 image
adduser <user>                 # lowercase name; pick a strong password: sudo asks for it
usermod -aG sudo <user>
```

### 2.2 SSH key login

From your machine (not the VPS):

```bash
ssh-copy-id <user>@<VPS_IP>    # or append your public key to ~/.ssh/authorized_keys on the VPS
ssh <user>@<VPS_IP>            # must work without a password before going further
```

### 2.3 Disable password and root login

On the VPS:

```bash
sudo tee /etc/ssh/sshd_config.d/99-hardening.conf <<'EOF'
PasswordAuthentication no
KbdInteractiveAuthentication no
PermitRootLogin no
EOF
sudo sshd -t && sudo systemctl restart ssh
```

Open a **new** terminal and check `ssh <user>@<VPS_IP>` still works before closing the current session.

### 2.4 Firewall

```bash
sudo apt install -y ufw
sudo ufw default deny incoming
sudo ufw default allow outgoing
sudo ufw allow OpenSSH
sudo ufw allow 80/tcp           # HTTP: redirect to HTTPS + Let's Encrypt challenge
sudo ufw allow 443/tcp          # HTTPS
sudo ufw allow 443/udp          # HTTP/3
sudo ufw enable
```

Note: ports published by Docker bypass ufw rules (Docker writes its own iptables rules). That's why only Caddy publishes ports in `compose.yml`: never add a `ports:` entry to `postgres` or `app`.

### 2.5 Automatic security updates

```bash
sudo apt install -y unattended-upgrades
sudo dpkg-reconfigure -plow unattended-upgrades
```

## 3. Docker

```bash
curl -fsSL https://get.docker.com | sudo sh
sudo usermod -aG docker <user>  # lets the CI run docker compose without sudo
```

Log out and back in, then check: `docker compose version`.

Cap container log size (Docker rotates by size only, with no time limit, so no container logs visitor IPs there: nginx has `access_log off`):

```bash
sudo tee /etc/docker/daemon.json <<'EOF'
{
  "log-driver": "json-file",
  "log-opts": { "max-size": "10m", "max-file": "3" }
}
EOF
sudo systemctl restart docker
```

### 3.1 Access logs (visitor IPs, 14 days)

Caddy writes one JSON line per request (visitor IP, date, URL, status, user agent; `Authorization` and cookies are redacted) to `/var/log/caddy/access.log`, bind-mounted from the host. Rotation is left to logrotate: rotated daily, deleted after 14 days. That is the retention announced on the legal notice page (`/mentions-legales`): change both together.

```bash
sudo tee /etc/logrotate.d/caddy <<'EOF'
/var/log/caddy/*.log {
    daily
    rotate 14
    maxage 14
    missingok
    notifempty
    compress
    delaycompress
    copytruncate
}
EOF
sudo logrotate --debug /etc/logrotate.d/caddy   # dry run: no error expected
```

`copytruncate` because Caddy keeps the file open and doesn't reopen it on a signal. To investigate an attack: `sudo zgrep <ip> /var/log/caddy/access.log*`.

## 4. Deployment directory and production `.env`

```bash
sudo mkdir -p /opt/suivi_mns
sudo chown <user>:<user> /opt/suivi_mns
```

Create `/opt/suivi_mns/.env` (it is read by Compose for `${DB_*}` and passed to the backend container). Showcase values:

```dotenv
# Database (DB_HOST is the compose service name)
DB_HOST=postgres
DB_PORT=5432
DB_NAME=yorksoft
DB_USER=yorksoft
DB_PASSWORD=<strong random password>

# openssl rand -base64 48
JWT_SECRET=<long random secret>

# Showcase: the demo dataset is recreated on every restart/deploy.
# data-init.sql isn't idempotent, so don't switch to DDL_AUTO=update with SQL_FILE=init.
DDL_AUTO=create
SQL_FILE=init
SQL_INIT_MODE=always

# Seeded role accounts: the seed is replayed on every start, so keep these set.
# Never publish them; visitors only use trial accounts.
APP_SEED_DEMO_ADMIN_PASSWORD=<strong password>
APP_SEED_DEMO_TECHNICIAN_PASSWORD=<strong password>
APP_SEED_DEMO_DIRECTOR_PASSWORD=<strong password>
APP_SEED_DEMO_MANAGER_PASSWORD=<strong password>
APP_SEED_DEMO_CLIENT_PASSWORD=<strong password>

# Public trial accounts
APP_DEMO_ENABLED=true
APP_DEMO_TTL_HOURS=24
APP_DEMO_MAX_ACCOUNTS_PER_IP=10
APP_DEMO_MAX_TICKETS_PER_HOUR=20
APP_DEMO_MAX_COMMENTS_PER_HOUR=20
```

```bash
chmod 600 /opt/suivi_mns/.env
```

Generate secrets with `openssl rand -base64 48` (JWT) and `openssl rand -base64 24` (passwords).

## 5. CI access

### 5.1 Dedicated SSH key for GitHub Actions

On your machine:

```bash
ssh-keygen -t ed25519 -N "" -C "yorksoft-ci" -f yorksoft-ci
```

Append `yorksoft-ci.pub` to `~/.ssh/authorized_keys` of `<user>` on the VPS.

### 5.2 GitHub secrets

Repository → *Settings* → *Secrets and variables* → *Actions*:

| Secret | Value |
|---|---|
| `SERVER_HOST` | VPS IPv4 (GitHub-hosted runners have no IPv6) |
| `SERVER_USER` | `<user>` |
| `SERVER_SSH_KEY` | content of the **private** key `yorksoft-ci` |
| `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN` | unchanged |

Delete the local `yorksoft-ci` private key once it is stored in GitHub.

The `deploy` job runs on `ubuntu-latest`, copies `backend/compose.yml` to `/opt/suivi_mns/compose.yml`, then runs `docker compose pull && docker compose up -d --remove-orphans` and prunes old images.

## 6. Netcup data processing agreement (GDPR)

Signed on 2026-11-09 with the answers below.

netcup Customer Control Panel → *Master Data* → *Order Processing*:

- Data subjects: website visitors
- Categories: **Traffic data**, **Login and authentication**
- Additional data: `Free text entered by visitors in demo tickets and comments (deleted after 24 h)`
- **No special categories of personal data (Art. 9 GDPR)**

Keep the legal notice page consistent with these answers.

## 7. First deployment

Prerequisites: DNS record resolves to the VPS, ports 80/443 open, `.env` in place, GitHub secrets updated.

1. Push to `main` (a change under `backend/` or `frontend/` triggers the build and deploy jobs).
2. Follow the run in the *Actions* tab.
3. On the VPS:

   ```bash
   cd /opt/suivi_mns
   docker compose ps                 # all services up, app "healthy" after ~20 s
   docker compose logs -f caddy      # certificate obtained for yorksoft.benoitchamplon.fr
   ```

4. From your machine: `curl -I https://yorksoft.benoitchamplon.fr` → `200`, and `http://` redirects to `https://`.
5. In a browser: create a trial account from the login page, open a ticket, check the dashboard.

### Troubleshooting

- **Certificate not issued**: the DNS record doesn't point to the VPS yet, or port 80 is closed. Caddy retries on its own; check `docker compose logs caddy`. Don't restart in a loop (Let's Encrypt rate limits); the `caddy_data` volume keeps issued certificates across restarts.
- **502 on `/api/`**: the backend is still starting or crashed — `docker compose logs app` (missing `.env` variable, DB not ready).
- **Every visitor hits the trial account limit at once**: the client IP isn't forwarded. The chain relies on Caddy setting `X-Forwarded-For`, nginx appending to it, and `server.forward-headers-strategy=native` in `application.properties`.
- **Old site still answering / no deploy**: check the `SERVER_*` secrets and that `<user>` is in the `docker` group.
- **Can't log in with a seeded account** (`admin@yorksoft.fr`, …): on the server the passwords are the `APP_SEED_DEMO_*_PASSWORD` values of `/opt/suivi_mns/.env`, not the dev ones (`AdminPass1`…, which only exist in `application-dev.properties`). Read them from your machine:

  ```bash
  ssh <user>@<VPS_IP> "grep APP_SEED_DEMO_ /opt/suivi_mns/.env"
  ```

  To check the backend got the same value as the file (prints the length + 1, e.g. `33` for a 32-char password): `ssh <user>@<VPS_IP> "cd /opt/suivi_mns && docker compose exec -T app printenv APP_SEED_DEMO_ADMIN_PASSWORD | wc -c"`. The startup log line `Seeded password for N demo user(s)` confirms the seed ran; `still have the '{seed-…}' placeholder` means the variable is empty.
- **Caddyfile change not applied**: Compose doesn't recreate a container when only an inline `configs` content changes. The CI `deploy` job forces it (`docker compose up -d --force-recreate --no-deps caddy`); by hand, run the same command.

## 8. Remaining before going live

Tracked as Trello cards with the **Prod** label on the "Yorksoft" board.

Done in the repo:

- `server_name yorksoft.benoitchamplon.fr` in `frontend/nginx-custom.conf`
- HTTPS through Caddy, front no longer publishes port 80
- Client IP forwarding (`server.forward-headers-strategy=native`)
- CI `deploy` job on `ubuntu-latest`
- CORS restricted to `https://yorksoft.benoitchamplon.fr` (`app.cors.allowed-origins`, overridable with `APP_CORS_ALLOWED_ORIGINS`; the `dev` profile allows `http://localhost:4200`)
- Legal notice page (`/mentions-legales`, linked from the login page and the menus); visitor IPs logged by Caddy only, 14 days (section 3.1); avatars generated locally instead of loaded from ui-avatars.com

Still to do:

- **First deployment**: section 7 (DNS, VPS hardening, Docker, `.env` and GitHub secrets done on 2026-11-10).
- **Enrich the demo dataset** (backend): `data-init.sql` has 17 tickets and a single closed one, so the dashboard charts are nearly empty. Seed dates are hardcoded (2024…): use dates relative to `now()` so the demo doesn't age (tickets all overdue, empty "last months" charts).
- **Timezone offset on SLA/timestamps** (backend): see `docs/ARCHITECTURE.md` "Known issues". Quick workaround noted on the card: `TZ=Europe/Paris` on the `app` service; the real fix is UTC end to end.
Nice to have, not blocking:

- Production config cleanup: `spring.jpa.show-sql=true` floods the logs; `frontend/compose.yml` is stale; decide whether Swagger UI / `/api-docs` stays public.- Uneven KPI card borders on the dashboards.
